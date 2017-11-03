cancelable in ThisBuild := true

inThisBuild(Seq(
  organization := "me.ngrid",
  scalaOrganization := "org.typelevel",
  scalaVersion := "2.12.4-bin-typelevel-4",
  crossScalaVersions := Seq("2.10.6", "2.11.11", "2.12.4", "2.13.0-M2")
))

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
    ScalaProject,
    CatsDependencies,
    GdxDependencies,
    AkkaDependencies).
  settings(
    {
      import AkkaModules._
      Seq(
        Actors, Streams, ReactiveKafka,
      )
    },
    {
      import LibGdxModules._
      Seq(
        GdxBullet, GdxCore, GdxHeadlessBackend, GdxNatives, GdxBulletNatives
      )
    },
    {
      import CatsModules._
      Seq(CatsCore, CatsKernel, CatsMacros)
    }

  )
