package com.elleflorio.kafka.streams.playground.dao

import com.elleflorio.kafka.streams.playground.dao.model.unsplash.Photo
import com.elleflorio.kafka.streams.playground.dao.model.unsplash.PhotoJsonProtocol._
import org.mongodb.scala.bson.collection.mutable.Document
import org.mongodb.scala.{MongoCollection, MongoDatabase}
import spray.json._

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

class PhotoDao(database: MongoDatabase, photoCollection: String) {

  val collection: MongoCollection[Document] = database.getCollection(photoCollection)

  def createPhoto(photo: Photo): Future[String] = {
    val doc = Document(photo.toJson.toString())
    doc.put("_id", photo.id)
    collection.insertOne(doc).toFuture()
      .map(_ => photo.id)
  }
}
