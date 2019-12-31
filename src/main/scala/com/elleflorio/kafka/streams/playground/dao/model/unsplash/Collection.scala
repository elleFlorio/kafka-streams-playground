package com.elleflorio.kafka.streams.playground.dao.model.unsplash

import java.util.Date

import PhotoJsonProtocol._
import UserJsonProtocol._
import com.elleflorio.kafka.streams.playground.dao.model.util.DateJsonProtocol._
import spray.json.{DefaultJsonProtocol, RootJsonFormat}

case class Collection(id: Long,
                      title:Option[String],
                      description: Option[String],
                      publishedAt: Date,
                      updatedAt: Date,
                      curated: Option[Boolean],
                      featured: Option[Boolean],
                      totalPhotos: Option[Long],
                      `private`: Option[Boolean],
                      shareKey: Option[String],
                      coverPhoto: Option[Photo],
                      user: Option[User],
                      links: Option[Map[String, String]])

object CollectionJsonProtocol extends DefaultJsonProtocol {
  implicit val collectionFormat: RootJsonFormat[Collection] = rootFormat(lazyFormat(jsonFormat(Collection, "id", "title", "description", "published_at", "updated_at", "curated", "featured", "total_photos", "private", "share_key", "cover_photo", "user", "links")))
}
