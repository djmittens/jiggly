package me.ngrid.colors

import akka.NotUsed
import akka.actor.ActorSystem
import akka.stream.ActorMaterializer
import akka.stream.alpakka.slick.scaladsl._
import akka.stream.scaladsl.Source
import me.ngrid.jiggly.colors.survey
import me.ngrid.jiggly.colors.survey.Tables.AnswersRow
import slick.jdbc.SQLiteProfile.api._

import scala.concurrent.duration._
import scala.concurrent.{Await, ExecutionContext}
import scala.language.postfixOps

object SurveyResults {
  //  lazy val db = Database.forConfig("db.survey-results")

  def getResults(limit: Int)(implicit ec: ExecutionContext, s: SlickSession): Source[survey.Tables.AnswersRow, NotUsed] = {
//    val query = for (u <- Tables.Users) yield {
//      u
//    }
    // language=SQL
    val query =
      sql"""select * from answers where colorname like '%mom%' group by colorname;""".as[AnswersRow]

    Slick.source(query)
  }
}

object PrintResults {
  implicit val system: ActorSystem = ActorSystem()
  implicit val mat: ActorMaterializer = ActorMaterializer()
  //  implicit val ec: ExecutionContextExecutor = system.dispatcher

  implicit val session: SlickSession = SlickSession.forConfig("db.survey-results")

  system.registerOnTermination(session.close())

  def main(args: Array[String]): Unit = {
    import scala.concurrent.ExecutionContext.Implicits._

    //    val res = SurveyResults.getResults(5).runWith(Sink.ignore)
    val res = SurveyResults.getResults(100).
      log("color").
      runForeach(println)
    res.onComplete { _ =>
      session.close()
      system.terminate()
    }

    Await.result(res, 10 seconds)

    ()
  }
}

object GenerateServeyResults {
  def main(args: Array[String]): Unit = {
    slick.codegen.SourceCodeGenerator.run(
      "slick.jdbc.SQLiteProfile",
      "org.sqlite.JDBC",
      "jdbc:sqlite:data/jigglydat.sqlite",
      "colors/src/main/scala/",
      "me.ngrid.jiggly.colors.survey",
      None, None, ignoreInvalidDefaults = true, None
    )
  }
}