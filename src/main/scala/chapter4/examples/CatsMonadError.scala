package chapter4.examples

import helpers.Utils._

import cats._
import cats.implicits._

import scala.util.chaining._
import scala.util.Try

object CatsMonadError {
  private type ErrorOr[A] = Either[String, A]

  def apply(): Unit = {
    "Cats MonadError" pipe printThis(Console.BLUE)

    val monadError = MonadError[ErrorOr, String]

    val success = monadError.pure(1) tap printThis()

    monadError.ensure(success)("It's higher than 1000")(_ >= 1000) tap printThis()
    monadError.ensure(success)("It's lower than 1000")(_ >= 1000) tap printThis(Console.RED)

    val failure = monadError.raiseError("Error") tap printThis(Console.RED)
    val failure2 = monadError.raiseError("Impossible")

    // handle only some error
    def stringHandler(fa: ErrorOr[String]): ErrorOr[String] = monadError.handleErrorWith(fa) {
        case "Error" => monadError.pure("It's ok")
        case _       => monadError.raiseError("It's not ok")
      }

    stringHandler(failure) tap printThis()
    stringHandler(failure2) tap printThis(Console.RED)

    // handle every error
    def intHandler(fa: ErrorOr[Int]): ErrorOr[Int] = monadError.handleError(fa) {
      case "Error" => 1
      case _       => -1
    }

    intHandler(failure) tap printThis()
    intHandler(failure2) tap printThis()

    // syntax
   41.pure[ErrorOr] tap printThis()

    val failure3 = "Badness".raiseError[ErrorOr, Int]

    failure3.handleErrorWith {
      case "Badness" => 256.pure[ErrorOr]
      case _ => "Not good".raiseError[ErrorOr, Int]
    } tap printThis()

    // MonadError instance for Try
    new RuntimeException("It's all gone wrong").raiseError[Try, Int] tap printThis(Console.RED)
  }
}
