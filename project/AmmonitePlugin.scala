import sbt._, sbt.Keys._

object AmmonitePlugin extends AutoPlugin{

  object autoImport {
    lazy val amm = taskKey[Unit]("run ammonite")
  }
  import autoImport._

  override def projectSettings: Seq[Def.Setting[_]] = Seq(

    amm := {
      (console in Test).value
    },

    // Ammonite
    libraryDependencies += "com.lihaoyi" % "ammonite" % "1.0.3" % "test" cross CrossVersion.full,
    initialCommands in console in Test := "ammonite.Main.main(Array())",
  )
}
