name := "s3LogsReceiver"

version := "1.0"

scalaVersion := "2.10.5"

val JSON4S_VERSION="3.2.10"
val json4sNative = "org.json4s" %% "json4s-native" % JSON4S_VERSION
val json4sJackson = "org.json4s" %% "json4s-jackson" % JSON4S_VERSION
val json4sExt="org.json4s" %% "json4s-ext" % JSON4S_VERSION
resolvers += "Typesafe Repository" at "http://repo.typesafe.com/typesafe/releases/"
libraryDependencies ++=Seq(
  "com.amazonaws" % "aws-java-sdk" % "1.9.40"
  ,json4sNative,json4sJackson,json4sExt
  ,"org.scalatest" %% "scalatest" % "2.2.2" % "test"
  ,"com.typesafe.akka" % "akka-actor_2.10" % "2.3.13"
  ,"org.mongodb" %% "casbah" % "2.8.2"
  ,"org.apache.kafka" %% "kafka" % "0.8.2.1"
  //,"com.fih.s3log" %% "log-components" % "0.1.0-SNAPSHOT"
  )


    