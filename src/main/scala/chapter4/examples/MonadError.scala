package chapter4.examples

import helpers.Utils._

import scala.util.chaining._

object MonadError {
  def apply(): Unit = {
    "MonadError" pipe printThis(Console.BLUE)
  }
}
