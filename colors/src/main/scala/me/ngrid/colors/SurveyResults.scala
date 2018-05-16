package me.ngrid.colors

import akka.actor.ActorSystem
import akka.stream.ActorMaterializer
import akka.stream.alpakka.slick.scaladsl._
import me.ngrid.jiggly.colors.survey.Tables
import slick.jdbc.SQLiteProfile.api._

import scala.concurrent.duration._
import scala.concurrent.{Await, ExecutionContext}
import scala.language.postfixOps

object SurveyResults {
//  lazy val db = Database.forConfig("db.survey-results")

  def getResults(limit: Int)(implicit ec: ExecutionContext, s: SlickSession)  = {
    val query = for (u <- Tables.Users) yield {
      u
    }

//    db.stream(query.result)
    Slick.source(query.take(limit).result)
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
    val res = SurveyResults.getResults(5).runForeach(println)
    res.onComplete{ _ =>
      session.close()
      system.terminate()
    }

    Await.result(res, 10 seconds)

    ()
  }
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