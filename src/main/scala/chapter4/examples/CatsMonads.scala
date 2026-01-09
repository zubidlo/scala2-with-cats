package chapter4.examples

import implicits._
import cats._
import cats.implicits._

object CatsMonads {
  private def sumSquare[F[_] : Monad](a: F[Int], b: F[Int]): F[Int] =
    for {
      x <- a
      y <- b
    } yield x * x + y * y


  def apply(): Unit = {
    sumSquare(1.some, 2.some).tapPrint()
    sumSquare(List(1, 2, 3), List(4, 5, 6)).tapPrint()
    sumSquare(1: Id[Int], 2: Id[Int]).tapPrint()

    val dave: Id[String] = "Dave"
    dave.getClass.getSimpleName.tapPrint(Console.YELLOW)

    for {
      x <- 3: Id[Int]
      y <- 4: Id[Int]
    } yield (x * y).tapPrint()
  }
}
