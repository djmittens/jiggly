
lazy val `jiggly` = (project in file(".")).
  aggregate(`shard`)

lazy val `shard` = (project in file("shard")).
  enablePlugins(AkkaDependencies).
  settings {
    import AkkaModules._
    Seq(Actors, Streams, ReactiveKafka)
  }




