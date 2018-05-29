package me.ngrid.conversion

import scala.collection.GenTraversableOnce

trait ImplicitConversion[-In, +Out] {
  def apply(v: In): Out
}

object ImplicitConversion {
  implicit def seqConverter[T[In] <: GenTraversableOnce[In], In, Out](implicit conv: ImplicitConversion[In, Out]) = (v: T[In]) => {
    v.seq.map(_.convert[Out])
  }

  implicit def combine[A, B, C](implicit ab: ImplicitConversion[A, B], bc: ImplicitConversion[B, C]) =
    (in: A) => bc(ab(in))

  implicit def identity[T] = new ImplicitConversion[T, T] {
    override def apply(v: T): T = v
  }

  implicit def toEither[E, A, B](implicit con: ImplicitConversion[A, B]): ImplicitConversion[A, Either[E, B]] =
    (v: A) => Right(con(v))

  implicit object intToString extends ImplicitConversion[Int, String] {
    override def apply(v: Int): String = v.toString
  }

  implicit object decimalStringToInt extends ImplicitConversion[String, Either[NumberFormatException, Int]] {
    override def apply(v: String): Either[NumberFormatException, Int] =
      try {
        Right(v.toInt)
      } catch {
        case e: NumberFormatException =>
          Left(e)
      }
  }
}

trait ConversionSyntax {
  implicit class Conversion[In](obj: In) {
    def convert[Out](implicit conv: ImplicitConversion[In, Out]) = conv.apply(obj)
  }

}

object test {
  def main(args: Array[String]): Unit = {
    val res = for {
      i <- 12.convert[Either[_, Int]]
      j <- "123".convert[Either[_, Int]]
    } yield i -> j

    println(res)
  }
}
