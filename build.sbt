lazy val `jiggly` = (project in file(".")).
  settings(
    publishArtifact := false
  ).
  aggregate(`shard`)

lazy val `shard` = (project in file("shard")).
  enablePlugins(
    ScalaProject,
    GdxDependencies,
    AkkaDependencies).
  settings {
    import AkkaModules._
    import LibGdxModules._

    (libraryDependencies += "org.hdfgroup" % "hdf-java" % "2.6.1") ::
      Actors ::
      Streams ::
      ReactiveKafka ::
      GdxBullet ::
      Nil
  }
