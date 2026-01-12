package chapter4.examples

import implicits._

import cats._
import cats.data._
import cats.implicits._

import scala.Console._

object CatsWriterMonad {
  "Cats Writer Monad".tapPrint(BLUE)

  def apply(): Unit = {

    val w1: WriterT[Id, Vector[String], Int] = Writer(Vector(
      "It was the best of times",
      "It was the worst of times"
    ), 1859).tapPrint(MAGENTA)

    w1.value.tapPrint(MAGENTA)
    w1.written.tapPrint(MAGENTA)

    val (written, value) = w1.run
    value.tapPrint(CYAN)
    written.tapPrint(CYAN)

    val w2 = 1859.writer(Vector(
      "It was the best of times",
      "It was the worst of times"
    )).tapPrint(YELLOW_B)

    w2.map(_ + 1000).tapPrint(YELLOW_B)
    w2.mapWritten(_.map(_.toUpperCase)).tapPrint(YELLOW_B)
    w2.mapBoth((l, v) => (l.map(_.toUpperCase), v + 1000)).tapPrint(YELLOW_B)
    w2.bimap(_.map(_.toUpperCase), _ + 1000).tapPrint(YELLOW_B)

    type Logged[A] = Writer[Vector[String], A]

    123.pure[Logged].tapPrint(YELLOW)

    Vector("msg1", "msg2", "msg3").tell.tapPrint(YELLOW)

    val w3 = for {
      a <- 10.pure[Logged]
      _ <- Vector("a", "b").tell
      b <- 32.writer(Vector("c", "d"))
    } yield a + b

    val (log, result) = w3.run

    log.tapPrint(BLUE)
    result.tapPrint(BLUE)

    w3.reset.tapPrint(BLUE)

    val (log2, result2) = w3.swap.run.tapPrint(BLUE)

    log2.tapPrint(BLUE)
    result2.tapPrint(BLUE)
  }
}
