package chapter3.examples

import helpers.printThis

import scala.util.chaining._

object ContravariantFunctors {
  trait Printable[A] {
    self =>
    def format(a: A): String

    def contramap[B](ba: B => A): Printable[B] = (b: B) => self.format(ba(b))
  }

  final case class Box[A](value: A)

  object Printable {
    implicit val intPrintable: Printable[Int] = (value: Int) => value.toString

    implicit val stringPrintable: Printable[String] =
      (value: String) => s"'$value'"

    implicit val booleanPrintable: Printable[Boolean] =
      (value: Boolean) => if (value) "yes" else "no"

    implicit def boxPrintable[A: Printable]: Printable[Box[A]] =
      implicitly[Printable[A]].contramap[Box[A]](_.value)
  }

  def format[A: Printable](a: A): String = implicitly[Printable[A]].format(a)

  def apply(): Unit = {
    format(123) pipe printThis(Console.GREEN)
    format("Foo") pipe printThis(Console.YELLOW)
    format(true) pipe printThis(Console.RED)
    format(Box(123)) pipe printThis(Console.GREEN)
    format(Box("Foo")) pipe printThis(Console.YELLOW)
    format(Box(true)) pipe printThis(Console.RED)
  }
}
