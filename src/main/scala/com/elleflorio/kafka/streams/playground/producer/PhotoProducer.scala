package com.elleflorio.kafka.streams.playground.producer

import java.util.Properties
import java.util.concurrent.Future

import com.elleflorio.kafka.streams.playground.dao.model.unsplash.Photo
import com.elleflorio.kafka.streams.playground.dao.model.unsplash.PhotoJsonProtocol._
import com.elleflorio.kafka.streams.playground.utils._
import org.apache.kafka.clients.producer.{KafkaProducer, ProducerRecord, RecordMetadata}
import spray.json._

case class PhotoProducer(props: Properties, topic: String) {

  createKafkaTopic(props, topic)
  val photoProducer = new KafkaProducer[String, String](props)

  def sendPhoto(photo: Photo): Future[RecordMetadata] = {
    val record = new ProducerRecord[String, String](topic, photo.id, photo.toJson.compactPrint)
    photoProducer.send(record)
  }

  def closePhotoProducer(): Unit = photoProducer.close()
}
