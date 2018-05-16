import sbt._
import sbt.Keys._

cancelable in ThisBuild := true

inThisBuild(
  (organization := "me.ngrid") ::
    Nil
)

lazy val `jiggly` = (project in file(".")).
  settings(
    publishArtifact := false
  ).
  aggregate(`shard`)

akkaVersion in ThisBuild := "2.5.6"
akkaHttpVersion in ThisBuild := "10.0.10"
reactiveKafkaVersion in ThisBuild := "0.17"
catsVersion in ThisBuild := "1.0.0-RC1"

libGdxVersion in ThisBuild := "1.9.7"

lazy val `shard` = (project in file("shard")).
  enablePlugins(
    AmmonitePlugin,
    ScalaProject,
    CatsDependencies,
    GdxDependencies,
    AkkaDependencies).
  settings(
    {
      import AkkaModules._
      Actors :: Streams :: ReactiveKafka :: Nil
    }, {
      import LibGdxModules._
      GdxBullet :: GdxCore :: GdxHeadlessBackend :: GdxNatives :: GdxBulletNatives :: Nil
    }, {
      import CatsDependencies.autoImport.CatsModules._
      CatsCore :: CatsKernel :: CatsMacros :: Nil
    }
  )

lazy val `colors` = (project in file("colors")).
  enablePlugins(
    ScalaProject,
    AkkaDependencies
  ).settings(
  libraryDependencies += "com.typesafe.slick" %% "slick" % "3.2.3",
  libraryDependencies += "com.typesafe.slick" %% "slick-hikaricp" % "3.2.3",
  libraryDependencies += "org.xerial" % "sqlite-jdbc" % "3.21.0.1",
  // Useful for cod egeneration.
  libraryDependencies += "com.typesafe.slick" %% "slick-codegen" % "3.2.3",
  libraryDependencies += "org.hdfgroup" % "hdf-java" % "2.6.1",
  {
    import AkkaModules._
    Actors :: Streams :: Nil
  },
)
