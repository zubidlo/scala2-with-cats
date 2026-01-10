package chapter3.examples

import implicits._

import cats._
import cats.implicits._

object InvariantFunctors {
  trait Codec[A] { self =>
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
    "encoded:" tapPrint(Console.YELLOW)
    encode("Hello").tapPrint()
    encode(123).tapPrint()
    encode(true).tapPrint()
    encode[Box[Boolean]](Box(false)).tapPrint()

    "decoded:" tapPrint(Console.YELLOW)
    decode[String]("Hello").tapPrint()
    decode[Int]("123").tapPrint()
    decode[Boolean]("true").tapPrint()
    decode[Box[Boolean]]("false").tapPrint()

    "symbol:" tapPrint(Console.YELLOW)

    implicit val symbolMonoid: Monoid[Symbol] =
      Monoid[String].imap(s => Symbol(s))(s => s.name)

    Monoid[Symbol].empty.tapPrint()
    (Symbol("a") |+| Symbol("few") |+| Symbol("words")).tapPrint()
  }
}
