package com.elleflorio.kafka.streams.playground.dao.model.unsplash

import spray.json.{DefaultJsonProtocol, RootJsonFormat}

case class Exif(make: Option[String],
                model: Option[String],
                exposureTime: Option[String],
                aperture: Option[String],
                focalLength: Option[String],
                iso: Option[Int])

object ExifJsonProtocol extends DefaultJsonProtocol {
  implicit val exifFormat: RootJsonFormat[Exif] = jsonFormat(Exif, "make", "model", "exposure_time", "aperture", "focal_length", "iso")
}
