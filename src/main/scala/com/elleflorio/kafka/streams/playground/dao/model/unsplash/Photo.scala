package com.elleflorio.kafka.streams.playground.dao.model.unsplash

import java.util.Date

import ExifJsonProtocol._
import LocationJsonProtocol._
import TagJsonProtocol._
import UserJsonProtocol._
import com.elleflorio.kafka.streams.playground.dao.model.util.DateJsonProtocol._
import spray.json.{DefaultJsonProtocol, RootJsonFormat}

case class Photo(id: String,
                 createdAt: Date,
                 updatedAt: Date,
                 width: Int,
                 height: Int,
                 color: Option[String],
                 downloads: Option[Long],
                 likes: Option[Long],
                 likedByUser: Option[Boolean],
                 description: Option[String],
                 exif: Option[Exif],
                 location: Option[Location],
                 tags: Option[List[Tag]],
                 currentUserCollections: Option[List[Collection]],
                 urls: Option[Map[String, String]],
                 links: Option[Map[String, String]],
                 user: Option[User])

object PhotoJsonProtocol extends DefaultJsonProtocol {
  implicit val collectionFormat: RootJsonFormat[Collection] = rootFormat(lazyFormat(jsonFormat(Collection, "id", "title", "description", "published_at", "updated_at", "curated", "featured", "total_photos", "private", "share_key", "cover_photo", "user", "links")))

  implicit val photoFormat: RootJsonFormat[Photo] = rootFormat(lazyFormat(jsonFormat(Photo, "id", "created_at", "updated_at", "width", "height", "color", "downloads", "likes", "liked_by_user", "description", "exif", "location", "tags", "current_user_collections", "urls", "links", "user")))
}
