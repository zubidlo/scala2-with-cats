package chapter4.examples

import implicits._
import cats._

import scala.Console._

object CatsEvalMonad {
  def apply(): Unit = {
    "Cats Eval Monad".tapPrint(BLUE)

    // EAGER and MEMOIZED aka call-by-value
    val x = {
      "Computing X".tapPrint(YELLOW)
      math.random()
    }
    // x is already evaluated and memoized
    x.tapPrint()
    // again returned from memoization
    x.tapPrint()
    // Now have same semantics
    val now = Eval.now {
      "Computing Now".tapPrint(MAGENTA)
      math.random() + 1000
    }

    // LAZY and not MEMOIZED aka call-by-name
    def y = {
      "Computing Y".tapPrint(YELLOW)
      math.random()
    }

    // y is evaluated every time
    y.tapPrint()
    y.tapPrint()
    // Always have same semantics
    val always = Eval.always {
      "Computing Always".tapPrint(MAGENTA)
      math.random() + 2000
    }

    // LAZY and MEMOIZED aka call-by-need
    lazy val z = {
      "Computing Z".tapPrint(YELLOW)
      math.random()
    }

    // evaluated now
    z.tapPrint()
    // same value returned from memoization
    z.tapPrint()
    // Later have same semantics
    val later = Eval.later {
      "Computing Later".tapPrint(MAGENTA)
      math.random() + 3000
    }

    now.value.tapPrint()
    now.value.tapPrint()

    always.value.tapPrint()
    always.value.tapPrint()

    later.value.tapPrint()
    later.value.tapPrint()

    val greeting = Eval
      .always { "Step 1".tapPrint(MAGENTA_B); "Hello" }
      .map { str => "Step 2".tapPrint(MAGENTA_B); s"$str World" }

    greeting.value.tapPrint()

    val ans = for {
      a <- Eval.now { "Calculating A".tapPrint(MAGENTA_B); 40 }
      b <- Eval.always { "Calculating B".tapPrint(MAGENTA_B); 2 }
    } yield {
      "Adding A and B".tapPrint(MAGENTA_B)
      a + b
    }

    "first access".tapPrint(BLUE)
    ans.value
    "second access".tapPrint(BLUE)
    ans.value

    "memoization".tapPrint(BLUE)
    val saying = Eval
      .always{ println("Step 1"); "The cat" }
      .map{ str => println("Step 2"); s"$str sat on" }
      .memoize
      .map{ str => println("Step 3"); s"$str the mat" }

    "first access".tapPrint(BLUE)
    saying.value.tapPrint()
    "second access".tapPrint(BLUE)
    saying.value.tapPrint()

    "Trampolining".tapPrint(BLUE)

    def factorial(n: BigInt): Eval[BigInt] =
      if (n == 1)
        Eval.now(n)
      else Eval.defer(factorial(n - 1).map(_ * n))

    "no stack overflow".tapPrint(BLUE)
    factorial(50000).value
  }
}
