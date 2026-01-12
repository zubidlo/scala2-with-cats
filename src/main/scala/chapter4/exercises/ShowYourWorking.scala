package chapter4.exercises

import implicits._

import cats._
import cats.data._
import cats.implicits._

import scala.concurrent.duration._
import scala.concurrent._
import scala.concurrent.ExecutionContext.Implicits._

import scala.Console._

object ShowYourWorking {
  "Show Your Working".tapPrint(BLUE)

  private def slowly[A](body: => A): A =
    try body finally Thread.sleep(100)

  private def factorial(n: Int): Int = {
    val ans = slowly(if (n == 0) 1 else n * factorial(n - 1))
    ans.tapPrint(s"factorial $n $ans")
  }

  // solution with Writer
  private type Logged[A] = Writer[Vector[String], A]

  private def factorial2(n: Int): Logged[Int] = {
    for {
      ans <- if (n == 0) {
        1.pure[Logged]
      }
      else {
        slowly(factorial2(n - 1).map(_ * n))
      }
      _ <- Vector(s"factorial $n $ans").tell
    } yield ans
  }

  def apply(): Unit = {
    // concurent logging is interleaved
    Await.result(Future.sequence(Vector(
      Future(factorial(5)),
      Future(factorial(5))
    )), 5.seconds)

    // concurent logging is not interleaved
    Await.result(Future.sequence(Vector(
      Future(factorial2(5)),
      Future(factorial2(5))
    )).map(_.map(_.written)), 5.seconds).tapPrint()
  }
}
