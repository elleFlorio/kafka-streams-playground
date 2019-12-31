package com.elleflorio.kafka.streams.playground.stream

import com.elleflorio.kafka.streams.playground.dao.model.LongExposurePhoto
import com.elleflorio.kafka.streams.playground.dao.model.LongExposurePhotoJsonProtocol._
import com.elleflorio.kafka.streams.playground.dao.model.unsplash.Photo
import com.elleflorio.kafka.streams.playground.dao.model.unsplash.PhotoJsonProtocol._
import com.elleflorio.kafka.streams.playground.utils.parseExposureTime
import org.apache.kafka.common.serialization.Serdes.StringSerde
import org.apache.kafka.streams.kstream.{Consumed, KStream, Produced}
import org.apache.kafka.streams.{StreamsBuilder, Topology}
import spray.json._

object LongExposureTopology {

  def build(sourceTopic: String, sinkTopic: String): Topology = {
    val stringSerde = new StringSerde

    val streamsBuilder = new StreamsBuilder()

    val photoSource: KStream[String, String] =
      streamsBuilder.stream(sourceTopic, Consumed.`with`(stringSerde, stringSerde))

    val covertToPhotoObject: KStream[String, Photo] =
      photoSource.mapValues((_, jsonString) => {
        val photo = jsonString.parseJson.convertTo[Photo]
        println(s"Processing photo ${photo.id}")
        photo
      })

    val filterWithLocation: KStream[String, Photo] = covertToPhotoObject.filter((_, photo) => photo.location.exists(_.position.isDefined))

    val filterWithExposureTime: KStream[String, Photo] = filterWithLocation.filter((_, photo) => photo.exif.exists(_.exposureTime.isDefined))

    val dataExtractor: KStream[String, LongExposurePhoto] =
      filterWithExposureTime.mapValues((_, photo) => LongExposurePhoto(photo.id, parseExposureTime(photo.exif.get.exposureTime.get), photo.createdAt, photo.location.get))

    val longExposureFilter: KStream[String, String] =
      dataExtractor.filter((_, item) => item.exposureTime > 1.0).mapValues((_, longExposurePhoto) => {
        val jsonString = longExposurePhoto.toJson.compactPrint
        println(s"completed processing: $jsonString")
        jsonString
      })

    longExposureFilter.to(sinkTopic, Produced.`with`(stringSerde, stringSerde))

    streamsBuilder.build()
  }

}
