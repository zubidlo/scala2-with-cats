package chapter4.examples

import cats.implicits._
import helpers.printThis

import scala.util.Try
import scala.util.chaining._

object CatsEither {
  private def countPositive(nums: List[Int]): Either[String, Int] =
    nums.foldLeft(0.asRight[String]) { (acc, num) =>
      if (num > 0) acc.map(_ + 1) else Left("Negative stoping!")
    }

  def apply(): Unit = {
    "Cats Either" pipe printThis(Console.BLUE)

    countPositive(List(1, 2, 3)) tap printThis()
    countPositive(List(1, 2, -3)) tap printThis(Console.RED)

    Either.catchOnly[NumberFormatException] { "not int".toInt } tap printThis(Console.RED)
    Either.catchNonFatal(sys.error("Error")) tap printThis(Console.RED)
    Either.fromOption(1.some, "Must be an integer") tap printThis()
    Either.fromOption(None, "Must be an integer") tap printThis(Console.RED)
    Either.fromTry(Try { countPositive(List(1, 2, 3)) }) flatMap identity tap printThis()
    Either.fromTry(Try { countPositive(List(1, 2, -3)) }) flatMap identity tap printThis(Console.RED)

    "Error".asLeft[Int].getOrElse(0) tap printThis()
    "Error".asLeft[Int].orElse(1.asRight) tap printThis()
    1.asRight[String].ensure("Must be non-negative")(_ > 0) tap printThis()
    -1.asRight[String].ensure("Must be non-negative")(_ > 0) tap printThis(Console.RED)

    "error".asLeft[Int].recover {
      // if there is a string error then recover to -1
      case _: String => -1
    } tap printThis()

    "error".asLeft[Int].recoverWith {
      case _: String => Right(-1)
    } tap printThis()

    "foo".asLeft[Int].leftMap(_.reverse) tap printThis(Console.RED)
    6.asRight[String].bimap(_.reverse, _ * 7) tap printThis()
    "bar".asLeft[Int].bimap(_.reverse, _ * 7) tap printThis(Console.RED)

    123.asRight[String] tap printThis()
    123.asRight[String].swap tap printThis()

    123.asRight[String].toOption tap printThis()
    "error".asLeft[Int].toOption tap printThis(Console.RED)

    123.asRight[String].toList tap printThis()
    "error".asLeft[Int].toList tap printThis(Console.RED)
  }
}
