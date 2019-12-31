package com.elleflorio.kafka.streams.playground.dao.model.unsplash

import spray.json.{DefaultJsonProtocol, RootJsonFormat}

case class Tag(title: Option[String])

object TagJsonProtocol extends DefaultJsonProtocol {
  implicit val tagFormat: RootJsonFormat[Tag] = jsonFormat1(Tag)
}
