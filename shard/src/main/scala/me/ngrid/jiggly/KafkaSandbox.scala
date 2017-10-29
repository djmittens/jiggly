package me.ngrid.jiggly

import akka.actor.ActorSystem
import akka.kafka.scaladsl.{Consumer, Producer}
import akka.kafka.{ConsumerSettings, ProducerMessage, ProducerSettings, Subscriptions}
import akka.stream.ActorMaterializer
import akka.stream.scaladsl.Source
import org.apache.kafka.clients.producer.ProducerRecord
import org.apache.kafka.common.serialization.{StringDeserializer, StringSerializer}

import scala.concurrent.{Await, Future}
import scala.concurrent.duration._

object KafkaSandbox {
  import scala.concurrent.ExecutionContext.Implicits.global

  def main(args: Array[String]): Unit = {
    println("hello world")
    implicit val system = ActorSystem("jigglySystem")
    implicit val mat = ActorMaterializer()

    val prodSettings = ProducerSettings(system, new StringSerializer, new StringSerializer).
      withBootstrapServers(
        "localhost:9092"
      )

    val conSettings = ConsumerSettings(system, new StringDeserializer, new StringDeserializer).
      withBootstrapServers(
        "localhost:9092"
      )

    val done1 = Source(1 to 100)
      .map(_.toString)
      .map { elem =>
        new ProducerRecord[String, String]("topic1", elem, elem)
      }
      .runWith(Producer.plainSink(prodSettings))

    val done2 = Consumer.committableSource(conSettings, Subscriptions.topics("topic1")).
      map { msg =>
        println(s"topic1 -> topic2: $msg")
        ProducerMessage.Message(new ProducerRecord[String, String](
          "topic2",
          msg.record.key(),
          msg.record.value()),
          msg.committableOffset)
      }.runWith(Producer.commitableSink(prodSettings))

    Await.result(Future.sequence(Seq(done1, done2)), 10.seconds)
    ()
  }
}
