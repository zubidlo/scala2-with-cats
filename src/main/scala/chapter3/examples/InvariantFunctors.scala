package chapter3.examples

import helpers.Utils.printThis

import cats._
import cats.data._
import cats.implicits._

import scala.util.chaining._

object InvariantFunctors {
  trait Codec[A] {
    self =>
    def encode(value: A): String

    def decode(value: String): A

    def imap[B](dec: A => B, enc: B => A): Codec[B] = new Codec[B] {
      def encode(value: B): String = self.encode(enc(value))

      def decode(value: String): B = dec(self.decode(value))
    }
  }

  final case class Box[A](a: A)

  object Codec {
    implicit val stringCodec: Codec[String] = new Codec[String] {
      def encode(value: String): String = value

      def decode(value: String): String = value
    }

    implicit val intCodec: Codec[Int] =
      stringCodec.imap(_.toInt, _.toString)

    implicit val booleanCodec: Codec[Boolean] =
      stringCodec.imap(_.toBoolean, _.toString)

    implicit def boxCodec[A: Codec]: Codec[Box[A]] =
      implicitly[Codec[A]].imap(a => Box(a), b => b.a)
  }

  private def encode[A: Codec](a: A): String =
    implicitly[Codec[A]].encode(a)

  private def decode[A: Codec](a: String): A =
    implicitly[Codec[A]].decode(a)

  def apply(): Unit = {
    "encoded:" pipe printThis(Console.YELLOW)
    encode("Hello") pipe printThis()
    encode(123) pipe printThis()
    encode(true) pipe printThis()
    encode[Box[Boolean]](Box(false)) pipe printThis()

    "decoded:" pipe printThis(Console.YELLOW)
    decode[String]("Hello") pipe printThis()
    decode[Int]("123") pipe printThis()
    decode[Boolean]("true").toString pipe printThis()
    decode[Box[Boolean]]("false").toString pipe printThis()

    "symbol:" pipe printThis(Console.YELLOW)

    implicit val symbolMonoid: Monoid[Symbol] =
      Monoid[String].imap(s => Symbol(s))(s => s.name)

    Monoid[Symbol].empty pipe printThis()
    Symbol("a") |+| Symbol("few") |+| Symbol("words") pipe printThis()
  }
}
