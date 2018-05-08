import sbt._, sbt.Keys._

object AmmonitePlugin extends AutoPlugin {

  object autoImport {
    lazy val amm = taskKey[Unit]("run ammonite")
  }

  import autoImport._

  override def projectSettings: Seq[Def.Setting[_]] =
    (amm := {
      (console in Test).value
    }) ::
      (libraryDependencies += "com.lihaoyi" % "ammonite_2.12.4" % "1.0.3" % "test") ::
      (initialCommands in console in Test := "ammonite.Main.main(Array())") ::
      Nil
}
