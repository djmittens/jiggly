package me.ngrid.jiggly.colors

import slick.jdbc.SQLiteProfile.api._

import scala.language.postfixOps

object SurveyResults {
  lazy val db = Database.forConfig("db.survey-results")
}

object GenerateServeyResults {
  def main(args: Array[Byte]): Unit = {
    slick.codegen.SourceCodeGenerator.main(
      "slick.jdbc.SQLiteProfile" ::
        "scala.slick.driver.SQLiteDriver" ::
        "jdbc:sqlite:data/jigglydat.sqlite" ::
        "src/main/scala/" ::
        "me.ngrid.jiglly.color" ::
        Nil toArray
    )
  }
}