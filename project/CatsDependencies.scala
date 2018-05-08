import sbt._, sbt.Keys._

object CatsDependencies extends AutoPlugin{
  object autoImport {
    lazy val catsVersion = settingKey[String]("typelevel cats version for the project")

    object CatsModules {
      /**
        * Main meat of the library (required)
        */
      lazy val CatsCore =  libraryDependencies += "org.typelevel" %% "cats-core" % catsVersion.value
      /**
        * Special syntax requires special macros, so this is (required)
        */
      lazy val CatsMacros =  libraryDependencies += "org.typelevel" %% "cats-macros" % catsVersion.value
      /**
        * Basic type classes (required)
        */
      lazy val CatsKernel = libraryDependencies += "org.typelevel" %% "cats-kernel" % catsVersion.value

      lazy val CatsLaws = libraryDependencies += "org.typelevel" %% "cats-laws" % catsVersion.value

      lazy val CatsFree = libraryDependencies += "org.typelevel" %% "cats-free" % catsVersion.value

      lazy val CatsTestkit = libraryDependencies += "org.typelevel" %% "cats-testkit" % catsVersion.value

      lazy val AlleyCats = libraryDependencies += "org.typelevel" %% "alleycats" % catsVersion.value

      lazy val CatsEffect = libraryDependencies += "org.typelevel" %% "cats-effect" % "0.5"

      lazy val Mouse = "org.typelevel" %% "mouse" % "0.11"
    }
  }
}
