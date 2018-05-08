
import sbt.{Def, _}
import sbt.Keys._

object GdxDependencies extends AutoPlugin {

  object autoImport {
    lazy val libGdxVersion = Def.settingKey[String]("version of libgdx to use")

    object LibGdxModules {
      lazy val GdxCore = libraryDependencies +=
        "com.badlogicgames.gdx" % "gdx" % libGdxVersion.value

      lazy val GdxHeadlessBackend = libraryDependencies +=
        "com.badlogicgames.gdx" % "gdx-backend-headless" % libGdxVersion.value

      lazy val GdxNatives = libraryDependencies +=
        "com.badlogicgames.gdx" % "gdx-platform" % libGdxVersion.value classifier "natives-desktop"

      lazy val GdxBullet = libraryDependencies +=
        "com.badlogicgames.gdx" % "gdx-bullet" % libGdxVersion.value

      lazy val GdxBulletNatives = libraryDependencies +=
        "com.badlogicgames.gdx" % "gdx-bullet-platform" % libGdxVersion.value classifier "natives-desktop"
    }
  }
}
