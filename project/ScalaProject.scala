import sbt.Keys._
import sbt.{Def, _}

object ScalaProject extends AutoPlugin {

  object autoImport {
  }

  import autoImport._


  override def buildSettings: Seq[Def.Setting[_]] = Seq(
    scalaOrganization := "org.typelevel",
    scalaVersion := "2.12.3-bin-typelevel-4"
//    crossScalaVersions := Seq("2.10.6", "2.11.11", "2.12.4", "2.13.0-M2")
  )

  override def projectSettings: Seq[Def.Setting[_]] = Seq(
    scalacOptions ++=
      Seq(
        "-Yinduction-heuristics", // speeds up the compilation of inductive implicit resolution
        "-Ykind-polymorphism", // type and method definitions with type parameters of arbitrary kinds
        "-Yliteral-types", // literals can appear in type position
        "-Xstrict-patmat-analysis", // more accurate reporting of failures of match exhaustivity
        "-Xlint:strict-unsealed-patmat" // warn on inexhaustive matches against unsealed traits
      ),
    // Test dependenciesk
//    scalaVersion := "2.12.3",

    // Test dependencies
    libraryDependencies += "org.scalactic" %% "scalactic" % "3.0.4",
    libraryDependencies += "org.scalatest" %% "scalatest" % "3.0.4" % "test",

    libraryDependencies += "ch.qos.logback" % "logback-classic" % "1.2.3",
    libraryDependencies += "com.typesafe.scala-logging" %% "scala-logging" % "3.7.2",
    libraryDependencies += "org.scala-lang.modules" %% "scala-java8-compat" % "0.8.0",

    scalacOptions ++= Seq(
      "-encoding", "UTF-8",
      "-deprecation",
      "-explaintypes",
      "-unchecked",
      "-feature",
      "-language:_",
      "-language:experimental.macros",
      "-Ypartial-unification",
      "-Ywarn-adapted-args",
      "-Ywarn-dead-code",
      "-Ywarn-inaccessible",
      "-Ywarn-infer-any",
      "-Ywarn-nullary-override",
      "-Ywarn-nullary-unit",
      "-Ywarn-numeric-widen",
      "-Ywarn-unused:implicits",
      "-Ywarn-unused:imports",
      "-Ywarn-unused:locals",
      "-Ywarn-unused:params",
      "-Ywarn-unused:patvars",
      "-Ywarn-unused:privates",
      "-Ywarn-value-discard",
      "-Xlint:adapted-args", // Warn if an argument list is modified to match the receiver.
      "-Xlint:by-name-right-associative", // By-name parameter of right associative operator.
      "-Xlint:constant", // Evaluation of a constant arithmetic expression results in an error.
      "-Xlint:delayedinit-select", // Selecting member of DelayedInit.
      "-Xlint:doc-detached", // A Scaladoc comment appears to be detached from its element.
      "-Xlint:inaccessible", // Warn about inaccessible types in method signatures.
      "-Xlint:infer-any", // Warn when a type argument is inferred to be `Any`.
      "-Xlint:missing-interpolator", // A string literal appears to be missing an interpolator id.
      "-Xlint:nullary-override", // Warn when non-nullary `def f()' overrides nullary `def f'.
      "-Xlint:nullary-unit", // Warn when nullary methods return Unit.
      "-Xlint:option-implicit", // Option.apply used implicit view.
      "-Xlint:package-object-classes", // Class or object defined in package object.
      "-Xlint:poly-implicit-overload", // Parameterized overloaded implicit methods are not visible as view bounds.
      "-Xlint:private-shadow", // A private field (or class parameter) shadows a superclass field.
      "-Xlint:stars-align", // Pattern sequence wildcard must align with sequence component.
      "-Xlint:type-parameter-shadow", // A local type parameter shadows a type already in scope.
      "-Xlint:unsound-match", // Pattern match may not be typesafe.
      "-Xfuture",
      "-target:jvm-1.8"
    ),
    scalacOptions ++= Seq(
      "-deprecation",
      "-encoding", "UTF-8", // yes, this is 2 args
      "-feature",
      "-language:existentials",
      "-language:higherKinds",
      "-language:implicitConversions",
      "-unchecked",
      //      "-Xfatal-warnings", // Really? why would you want this on all the time?
      "-Ypartial-unification", // This is for cats fun
      "-Xlint",
      "-Yno-adapted-args",
      "-Ywarn-dead-code", // N.B. doesn't work well with the ??? hole
      "-Ywarn-numeric-widen",
      "-Ywarn-value-discard",
      "-Xfuture"
    )
  )
}
