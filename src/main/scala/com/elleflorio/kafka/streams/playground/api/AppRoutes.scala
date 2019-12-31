package com.elleflorio.kafka.streams.playground.api

import akka.actor.ActorSystem
import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import akka.http.scaladsl.server.Directives.{onSuccess, _}
import akka.http.scaladsl.server.Route
import akka.util.Timeout
import com.elleflorio.kafka.streams.playground.dao.model.unsplash.PhotoJsonProtocol._
import com.elleflorio.kafka.streams.playground.dao.PhotoDao
import com.elleflorio.kafka.streams.playground.dao.model.unsplash.Photo

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import scala.concurrent.duration._

trait AppRoutes extends SprayJsonSupport {

  implicit def system: ActorSystem
  implicit def photoDao: PhotoDao
  implicit lazy val timeout = Timeout(5.seconds)

  lazy val healthRoute: Route = pathPrefix("health") {
    concat(
      pathEnd {
        concat(
          get {
            complete(StatusCodes.OK)
          }
        )
      }
    )
  }

  lazy val crudRoute: Route = pathPrefix("photo") {
    concat(
      pathEnd {
        concat(
          post {
            entity(as[Photo]) { photo =>
              val photoCreated: Future[String] =
                photoDao.createPhoto(photo)
              onSuccess(photoCreated) { id =>
              complete((StatusCodes.Created, id))
              }
            }
          }
        )
      }
    )
  }

}
