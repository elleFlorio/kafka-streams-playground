package com.elleflorio.kafka.streams.playground.dao.model.unsplash

import spray.json.{DefaultJsonProtocol, RootJsonFormat}

case class Badge(title: Option[String], primary: Option[Boolean], slug: Option[String], link: Option[String])

object BadgeJsonProtocol extends DefaultJsonProtocol {
  implicit val badgeFormat: RootJsonFormat[Badge] = jsonFormat4(Badge)
}


