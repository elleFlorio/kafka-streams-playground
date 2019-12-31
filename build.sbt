name := "kafka-streams-playground"

version := "0.1"

scalaVersion := "2.13.0"

libraryDependencies ++= Seq(
  "org.apache.kafka" % "kafka-streams" % "2.3.0",
  "io.spray" %%  "spray-json" % "1.3.5",
  "org.scalatest" %% "scalatest" % "3.0.8" % "test",
  "org.apache.commons" % "commons-lang3" % "3.9",
  "com.typesafe.akka" %% "akka-stream" % "2.5.25",
  "com.typesafe.akka" %% "akka-http" % "10.1.9",
  "com.typesafe.akka" %% "akka-http-spray-json" % "10.1.9",
  "org.mongodb.scala" %% "mongo-scala-driver" % "2.7.0",
  "org.mongodb" % "mongo-java-driver" % "3.11.0",
)

enablePlugins(JavaAppPackaging)
enablePlugins(DockerPlugin)
enablePlugins(AshScriptPlugin)

mainClass in Compile := Some("com.elleflorio.kafka.streams.playground.Server")
dockerBaseImage := "java:8-jre-alpine"
version in Docker := "latest"
dockerExposedPorts := Seq(8000)
dockerRepository := Some("elleflorio")