package com.elleflorio.kafka.streams.playground.dao.model.unsplash

import spray.json.{DefaultJsonProtocol, RootJsonFormat}

case class Position(latitude: Double, longitude: Double)

object PositionJsonProtocol extends DefaultJsonProtocol {
  implicit val positionFormat: RootJsonFormat[Position] = jsonFormat2(Position)
}
