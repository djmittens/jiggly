import sbt._
import sbt.Keys._

cancelable in ThisBuild := true

inThisBuild(Seq(
  organization := "me.ngrid"
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
