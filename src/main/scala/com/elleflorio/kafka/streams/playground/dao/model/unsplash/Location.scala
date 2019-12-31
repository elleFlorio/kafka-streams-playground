package com.elleflorio.kafka.streams.playground.dao.model.unsplash

import PositionJsonProtocol._
import spray.json.{DefaultJsonProtocol, RootJsonFormat}

case class Location(city: Option[String],
                    country: Option[String],
                    position: Option[Position])

object LocationJsonProtocol extends DefaultJsonProtocol {
  implicit val locationFormat: RootJsonFormat[Location] = jsonFormat3(Location)
}
