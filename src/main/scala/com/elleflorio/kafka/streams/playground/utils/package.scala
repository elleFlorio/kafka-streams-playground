package com.elleflorio.kafka.streams.playground

import java.util.Properties

import org.apache.kafka.clients.admin.{AdminClient, NewTopic}
import scala.jdk.CollectionConverters._

package object utils {

  def createKafkaTopic(props: Properties, topic: String): Unit = {
    val adminClient = AdminClient.create(props)
    val photoTopic = new NewTopic(topic, 1, 1)
    adminClient.createTopics(List(photoTopic).asJava)
  }

  def parseExposureTime(ratio: String): Float = if (ratio.contains("/")) {
    val rat = ratio.split("/")
    rat(0).toFloat / rat(1).toFloat
  }
  else ratio.toFloat
}
