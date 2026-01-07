package chapter3.examples

import cats._
import cats.implicits._

import helpers.Utils._
import scala.util.chaining._

object CatsMonads {
  private def sumSquare[F[_] : Monad](a: F[Int], b: F[Int]): F[Int] =
    for {
      x <- a
      y <- b
    } yield x * x + y * y


  def apply(): Unit = {
    sumSquare(1.some, 2.some) pipe printThis()
    sumSquare(List(1, 2, 3), List(4, 5, 6)) pipe printThis()
    sumSquare(1: Id[Int], 2: Id[Int]) pipe printThis()

    val dave: Id[String] = "Dave"
    dave.getClass.getSimpleName pipe printThis(Console.YELLOW)

    for {
      x <- 3: Id[Int]
      y <- 4: Id[Int]
    } yield (x * y) pipe printThis()
  }
}
