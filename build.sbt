lazy val `jiggly` = (project in file(".")).
  settings (
    publishArtifact := false
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
