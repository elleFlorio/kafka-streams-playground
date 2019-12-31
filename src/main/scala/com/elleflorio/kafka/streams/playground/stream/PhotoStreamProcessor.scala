package com.elleflorio.kafka.streams.playground.stream

import java.util.Properties

import com.elleflorio.kafka.streams.playground.utils._
import org.apache.kafka.streams.{KafkaStreams, Topology}

class PhotoStreamProcessor(kafkaProps: Properties, streamProps: Properties, sourceTopic: String, sinkTopic: String) {

  createKafkaTopic(kafkaProps, sinkTopic)
  val topology: Topology = LongExposureTopology.build(sourceTopic, sinkTopic)
  val streams: KafkaStreams = new KafkaStreams(topology, streamProps)

  sys.ShutdownHookThread {
    streams.close(java.time.Duration.ofSeconds(10))
  }

  def start(): Unit = new Thread {
    override def run(): Unit = {
      streams.cleanUp()
      streams.start()
      println("Started long exposure processor")
    }
  }.start()
}
