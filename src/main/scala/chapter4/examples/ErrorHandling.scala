package chapter4.examples

import helpers.Utils._
import cats.implicits._

import scala.util.chaining._

object ErrorHandling {
  private sealed trait LoginError extends Product with Serializable

  private final case class UserNotFound(username: String) extends LoginError
  private final case class PasswordIncorrect(username: String) extends LoginError
  private case object UnexpectedError extends LoginError

  private case class User(username: String, password: String)

  private type LoginResult = Either[LoginError, User]

  private def handleError(error: LoginError): String = error match {
    case UserNotFound(u) => s"User not found: $u" tap printThis(Console.RED)
    case PasswordIncorrect(u) => s"Password incorrect: $u" tap printThis(Console.RED)
    case UnexpectedError => "Unexpected error" tap printThis(Console.RED)
  }

  def apply(): Unit = {
    "Error Handling" pipe printThis(Console.BLUE)

    def division(x: Int, y: Int): Either[String, Int] = for {
      a <- x.asRight
      b <- y.asRight
      c <- if (b == 0) "divided by 0".asLeft else (a / b).asRight
    } yield c

    division(4, 2) tap printThis()
    division(4, 0) tap printThis(Console.RED)

    val result1: LoginResult = User("dave", "password").asRight
    result1.fold(handleError, printThis())
    val result2: LoginResult = UserNotFound("dave").asLeft
    result2.fold(handleError, printThis(Console.RED))
  }
}
