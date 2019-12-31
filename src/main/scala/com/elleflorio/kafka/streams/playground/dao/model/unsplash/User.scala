package com.elleflorio.kafka.streams.playground.dao.model.unsplash

import java.util.Date

import BadgeJsonProtocol._
import com.elleflorio.kafka.streams.playground.dao.model.util.DateJsonProtocol._
import spray.json.{DefaultJsonProtocol, RootJsonFormat}

case class User(id: String,
                updatedAt: Date,
                username: String,
                firstName: Option[String],
                lastName: Option[String],
                twitterUserName: Option[String],
                portfolioUrl: Option[String],
                bio: Option[String],
                totalLikes: Option[Long],
                totalPhotos: Option[Long],
                totalCollections: Option[Long],
                followedByUser: Option[Boolean],
                followersCount: Option[Long],
                followingCount: Option[Long],
                downloads: Option[Long],
                uploadsRemaining: Option[Long],
                profileImage: Option[Map[String, String]],
                instagramUsername: Option[String],
                location: Option[String],
                email: Option[String],
                badge: Option[Badge],
                links: Option[Map[String, String]])

object UserJsonProtocol extends DefaultJsonProtocol {
  implicit val userFormat: RootJsonFormat[User] = jsonFormat(User, "id", "updated_at", "username", "first_name", "last_name", "twitter_user_name", "portfolio_url", "bio", "total_likes", "total_photos", "total_collections", "followed_by_user", "followers_count", "following_count", "downloads", "uploads_remaining", "profile_image", "instagram_username", "location", "email", "badge", "links")
}
