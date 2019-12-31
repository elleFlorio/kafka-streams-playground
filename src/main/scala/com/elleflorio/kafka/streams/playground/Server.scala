package com.elleflorio.kafka.streams.playground

import java.util.Properties

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import akka.stream.ActorMaterializer
import com.elleflorio.kafka.streams.playground.api.AppRoutes
import com.elleflorio.kafka.streams.playground.dao.PhotoDao
import com.elleflorio.kafka.streams.playground.listener.PhotoListener
import com.elleflorio.kafka.streams.playground.producer.PhotoProducer
import com.elleflorio.kafka.streams.playground.stream.PhotoStreamProcessor
import com.typesafe.config.{Config, ConfigFactory}
import org.apache.kafka.streams.StreamsConfig
import org.mongodb.scala.MongoClient

import scala.concurrent.Await
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration.Duration

object Server extends App with AppRoutes {

  implicit val system: ActorSystem = ActorSystem("kafka-stream-playground")
  implicit val materializer: ActorMaterializer = ActorMaterializer()

  val config: Config = ConfigFactory.load()
  val address = config.getString("http.ip")
  val port = config.getInt("http.port")

  val mongoUri = config.getString("mongo.uri")
  val mongoDb = config.getString("mongo.db")
  val mongoUser = config.getString("mongo.user")
  val mongoPwd = config.getString("mongo.pwd")
  val photoCollection = config.getString("mongo.photo_collection")

  val kafkaHosts = config.getString("kafka.hosts").split(',').toList
  val photoTopic = config.getString("kafka.photo_topic")
  val longExposureTopic = config.getString("kafka.long_exposure_topic")

  val kafkaProps = new Properties()
  kafkaProps.put("bootstrap.servers", kafkaHosts.mkString(","))
  kafkaProps.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer")
  kafkaProps.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer")

  val streamProps = new Properties()
  streamProps.put(StreamsConfig.APPLICATION_ID_CONFIG, "long-exp-proc-app")
  streamProps.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaHosts.mkString(","))

  val photoProducer = PhotoProducer(kafkaProps, photoTopic)
  val photoStreamProcessor = new PhotoStreamProcessor(kafkaProps, streamProps, photoTopic, "long-exposure")
  photoStreamProcessor.start()

  val client = MongoClient(s"mongodb://$mongoUri/$mongoUser")
  val db = client.getDatabase(mongoDb)
  implicit val photoDao: PhotoDao = new PhotoDao(db, photoCollection)
  val photoListener = PhotoListener(photoDao.collection, photoProducer)

  lazy val routes: Route = healthRoute ~ crudRoute

  Http().bindAndHandle(routes, address, port)
  Await.result(system.whenTerminated, Duration.Inf)
}
