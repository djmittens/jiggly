
import sbt.{Def, _}
import sbt.Keys._

object GdxDependencies extends AutoPlugin {

  object autoImport {
    lazy val libGdxVersion = Def.settingKey[String]("version of libgdx to use")

    object LibGdxModules {
      lazy val GdxBullet = libraryDependencies +=
        "com.badlogicgames.gdx" % "gdx-bullet" % libGdxVersion.value

      lazy val GdxBulletNatives = libraryDependencies +=
        "com.badlogicgames.gdx" % "gdx-bullet-platform" % libGdxVersion.value

    }
  }

  import autoImport._

  override def projectSettings: Seq[Def.Setting[_]] = Seq(
    libGdxVersion := "1.9.7"
  )
}
