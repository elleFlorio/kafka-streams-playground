package com.elleflorio.kafka.streams.playground.listener

import com.elleflorio.kafka.streams.playground.dao.model.unsplash.Photo
import com.elleflorio.kafka.streams.playground.dao.model.unsplash.PhotoJsonProtocol._
import com.elleflorio.kafka.streams.playground.producer.PhotoProducer
import com.mongodb.client.model.changestream.OperationType
import org.mongodb.scala.bson.collection.mutable.Document
import org.mongodb.scala.model.changestream.ChangeStreamDocument
import org.mongodb.scala.{ChangeStreamObservable, MongoCollection, Observer}
import spray.json._

case class PhotoListener(collection: MongoCollection[Document], producer: PhotoProducer) {

  val cursor: ChangeStreamObservable[Document] = collection.watch()

  cursor.subscribe(new Observer[ChangeStreamDocument[Document]] {
    override def onNext(result: ChangeStreamDocument[Document]): Unit = {
      result.getOperationType match {
        case OperationType.INSERT => {
          val photo = result.getFullDocument.toJson().parseJson.convertTo[Photo]
          producer.sendPhoto(photo).get()
          println(s"Sent photo with Id ${photo.id}")
        }
        case _ => println(s"Operation ${result.getOperationType} not supported")
      }
    }
    override def onError(e: Throwable): Unit = println(s"onError: $e")
    override def onComplete(): Unit = println("onComplete")})
}
