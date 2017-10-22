import sbt.{Def, _}
import sbt.Keys._

object ScalaProject extends AutoPlugin {
  override def projectSettings: Seq[Def.Setting[_]] = Seq(
    scalaVersion := "2.12.3",
    libraryDependencies += "com.lihaoyi" % "ammonite" % "1.0.3" % "test" cross CrossVersion.full,
    initialCommands in console in Test := "ammonite.Main.main(Array())",

    libraryDependencies += "org.scalactic" %% "scalactic" % "3.0.4",
    libraryDependencies += "org.scalatest" %% "scalatest" % "3.0.4" % "test",
    scalacOptions ++= Seq(
      "-deprecation",
      "-encoding", "UTF-8", // yes, this is 2 args
      "-feature",
      "-language:existentials",
      "-language:higherKinds",
      "-language:implicitConversions",
      "-unchecked",
      "-Xfatal-warnings",
      "-Xlint",
      "-Yno-adapted-args",
      "-Ywarn-dead-code", // N.B. doesn't work well with the ??? hole
      "-Ywarn-numeric-widen",
      "-Ywarn-value-discard",
      "-Xfuture"
    )
  )
}
