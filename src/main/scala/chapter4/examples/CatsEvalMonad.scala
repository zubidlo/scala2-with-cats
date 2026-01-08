package chapter4.examples

import helpers.Utils.printThis

import cats._
import cats.implicits._

import scala.util.chaining._

object CatsEvalMonad {
  def apply(): Unit = {
    "Cats Eval Monad" pipe printThis(Console.BLUE)

    // EAGER and MEMOIZED aka call-by-value
    val x = {
      "Computing X" pipe printThis(Console.YELLOW)
      math.random()
    }
    // x is already evaluated and memoized
    x tap printThis()
    // again returned from memoization
    x tap printThis()
    // Now have same semantics
    val now = Eval.now {
      "Computing Now" pipe printThis(Console.MAGENTA)
      math.random() + 1000
    }

    // LAZY and not MEMOIZED aka call-by-name
    def y = {
      "Computing Y" pipe printThis(Console.YELLOW)
      math.random()
    }

    // y is evaluated every time
    y pipe printThis()
    y pipe printThis()
    // Always have same semantics
    val always = Eval.always {
      "Computing Always" pipe printThis(Console.MAGENTA)
      math.random() + 2000
    }

    // LAZY and MEMOIZED aka call-by-need
    lazy val z = {
      "Computing Z" pipe printThis(Console.YELLOW)
      math.random()
    }

    // evaluated now
    z pipe printThis()
    // same value returned from memoization
    z pipe printThis()
    // Later have same semantics
    val later = Eval.later {
      "Computing Later" pipe printThis(Console.MAGENTA)
      math.random() + 3000
    }

    now.value pipe printThis()
    now.value pipe printThis()

    always.value pipe printThis()
    always.value pipe printThis()

    later.value pipe printThis()
    later.value pipe printThis()
  }
}
