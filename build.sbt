lazy val `jiggly` = (project in file(".")).
  enablePlugins(ScalaProject).
  settings (
    publishArtifact := false,
    fork in Test := false,
  ).
  aggregate(`shard`)

lazy val `shard` = (project in file("shard")).
  enablePlugins(ScalaProject, AkkaDependencies).
  settings {
    import AkkaModules._
    Seq(
      Actors, Streams, ReactiveKafka
    )
  }





