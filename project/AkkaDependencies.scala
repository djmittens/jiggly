import sbt.Keys._
import sbt._


object AkkaDependencies extends AutoPlugin {

  object autoImport {
    lazy val akkaVersion = Def.settingKey[String]("version of akka a to import")
    lazy val akkaHttpVersion = Def.settingKey[String]("http akka version, different than akka")
    lazy val reactiveKafkaVersion = Def.settingKey[String]("reactive kafka version")

    object AkkaModules {
      lazy val Actors =
        libraryDependencies += "com.typesafe.akka" %% "akka-actor" % akkaVersion.value
      lazy val Streams =
        libraryDependencies += "com.typesafe.akka" %% "akka-stream" % akkaVersion.value
      lazy val Cluster =
        libraryDependencies += "com.typesafe.akka" %% "akka-cluster" % akkaVersion.value
      lazy val Sharding =
        libraryDependencies += "com.typesafe.akka" %% "akka-cluster" % akkaVersion.value
      lazy val Persistence =
        libraryDependencies += "com.typesafe.akka" %% "akka-persistence" % akkaVersion.value
      lazy val ReactiveKafka =
        libraryDependencies += "com.typesafe.akka" %% "akka-stream-kafka" % reactiveKafkaVersion.value
    }
  }

  import autoImport._

  override def projectSettings: Seq[Def.Setting[_]] = Seq(
    akkaVersion := "2.5.6",
    akkaHttpVersion := "10.0.10",
    reactiveKafkaVersion := "0.17"
  )
}
