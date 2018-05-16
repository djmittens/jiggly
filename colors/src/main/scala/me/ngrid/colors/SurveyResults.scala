package me.ngrid.colors

import slick.jdbc.SQLiteProfile.api._

import scala.language.postfixOps

object SurveyResults {
  lazy val db = Database.forConfig("db.survey-results")
}

object GenerateServeyResults {
  def main(args: Array[String]): Unit = {
    slick.codegen.SourceCodeGenerator.main(
      "slick.jdbc.SQLiteProfile" ::
        "org.sqlite.JDBC" ::
        "jdbc:sqlite:data/jigglydat.sqlite" ::
        "colors/src/main/scala/" ::
        "me.ngrid.jiggly.colors.survey" ::
        Nil toArray
    )
  }
}