import sbt.{Def, _}
import sbt.Keys._

object ScalaProject extends AutoPlugin {
  override def requires: Plugins = AmmonitePlugin

  object autoImport {
  }

  import autoImport._

  override def projectSettings: Seq[Def.Setting[_]] = Seq(

    // Test dependenciesk
    libraryDependencies += "org.scalactic" %% "scalactic" % "3.0.4",
    libraryDependencies += "org.scalatest" %% "scalatest" % "3.0.4" % "test",

    libraryDependencies += "ch.qos.logback" % "logback-classic" % "1.2.3",
    libraryDependencies += "com.typesafe.scala-logging" %% "scala-logging" % "3.7.2",

    scalacOptions ++= Seq(
      "-deprecation",
      "-encoding", "UTF-8", // yes, this is 2 args
      "-feature",
      "-language:existentials",
      "-language:higherKinds",
      "-language:implicitConversions",
      "-unchecked",
//      "-Xfatal-warnings", // Really? why would you want this on all the time?
      "-Ypartial-unification", // This is for cats fun
      "-Xlint",
      "-Yno-adapted-args",
      "-Ywarn-dead-code", // N.B. doesn't work well with the ??? hole
      "-Ywarn-numeric-widen",
      "-Ywarn-value-discard",
      "-Xfuture"
    )
  )
}
