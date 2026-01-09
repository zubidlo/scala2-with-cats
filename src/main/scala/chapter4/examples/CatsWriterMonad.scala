package chapter4.examples

import implicits._

import cats._
import cats.implicits._

import scala.Console._

object CatsWriterMonad {
  def apply(): Unit = {
    "Cats Writer Monad".tapPrint(BLUE)
  }
}
