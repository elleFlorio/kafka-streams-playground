package com.elleflorio.kafka.streams.playground.dao.model

import java.util.Date

import com.elleflorio.kafka.streams.playground.dao.model.unsplash.Location
import com.elleflorio.kafka.streams.playground.dao.model.unsplash.LocationJsonProtocol._
import com.elleflorio.kafka.streams.playground.dao.model.util.DateJsonProtocol._
import spray.json.{DefaultJsonProtocol, RootJsonFormat}

case class LongExposurePhoto(id: String, exposureTime: Float, createdAt: Date, location: Location)

object LongExposurePhotoJsonProtocol extends DefaultJsonProtocol {
  implicit val longExposurePhotoFormat:RootJsonFormat[LongExposurePhoto] = jsonFormat(LongExposurePhoto, "id", "exposure_time", "created_at", "location")
}
