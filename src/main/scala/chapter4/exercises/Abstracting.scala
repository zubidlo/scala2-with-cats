package chapter4.exercises

import helpers.Utils._

import cats._
import cats.implicits._

import scala.util.chaining._
import scala.util.Try

object Abstracting {
  private def validateAdult[F[_]](age: Int)(implicit me: MonadError[F, Throwable]): F[Int] =
    if (age > 18) age.pure[F]
    else new IllegalArgumentException("Age must be greater than or equal to 18").raiseError[F, Int]

  def apply(): Unit = {
    "Abstracting" pipe printThis(Console.BLUE)

    validateAdult[Try](19) tap printThis()
    validateAdult[Try](8) tap printThis(Console.RED)

    type ExceptionOr[A] = Either[Throwable, A]

    validateAdult[ExceptionOr](1)
  }
}
