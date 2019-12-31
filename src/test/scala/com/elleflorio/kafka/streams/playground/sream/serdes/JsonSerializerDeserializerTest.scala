package com.elleflorio.kafka.streams.playground.sream.serdes

import java.nio.file.{Files, Paths}

import com.elleflorio.kafka.streams.playground.dao.model.unsplash.Photo
import com.elleflorio.kafka.streams.playground.dao.model.unsplash.PhotoJsonProtocol._
import org.scalatest.{FlatSpecLike, Matchers}
import spray.json._

import scala.jdk.CollectionConverters._

class JsonSerializerDeserializerTest extends Matchers with FlatSpecLike {

  val PHOTO_FILENAME = "/photo_complete.json"

  "Spray" should "correctly serialize/deserialize a photo object" in {

    val source = getClass.getResource(PHOTO_FILENAME)

    val photoFile = Files.readAllLines(Paths.get(source.getPath)).asScala.mkString

    val photo = photoFile.parseJson.convertTo[Photo]

    photo.toString equals photoFile
  }

}
