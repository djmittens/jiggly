cancelable in ThisBuild := true

lazy val `jiggly` = (project in file(".")).
  settings (
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
    Seq(
      Actors, Streams, ReactiveKafka,
      GdxBullet, GdxCore, GdxHeadlessBackend, GdxNatives, GdxBulletNatives
    )
  }
