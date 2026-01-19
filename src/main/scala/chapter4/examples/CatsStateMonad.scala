package chapter4.examples

import implicits._

import cats._
import cats.data._
import cats.implicits._

import scala.Console._

object CatsStateMonad {
  "The State Monad".tapPrint(BLUE)

  def apply(): Unit = {
    val a = State[Int, String] {
      state => (state, s"state is $state")
    }

    "(state, result)".tapPrint(YELLOW)
    a.run(10).value.tapPrint()
    "just a state".tapPrint(YELLOW)
    a.runS(10).value.tapPrint()
    "just a result".tapPrint(YELLOW)
    a.runA(10).value.tapPrint()

    val step1 = State[Int, String] { num =>
      val result = num + 1
      (result, s"result of step1: $result")
    }

    val step2 = State[Int, String] { num =>
      val result = num * 2
      (result, s"result of step2: $result")
    }

    val result = for {
      a <- step1
      b <- step2
    } yield (a, b)

    result.run(10).value.tapPrint()

    "syntax".tapPrint(YELLOW_B)
    State.get[Int].run(10).value.tapPrint()
    State.set[Int](30).run(10).value.tapPrint()
    State.pure[Int, String]("Result").run(10).value.tapPrint()
    State.inspect[Int, String](n => s"it's $n!").run(10).value.tapPrint()
    State.modify[Int](_ + 10).run(10).value.tapPrint()

    val program: State[Int, (Int, Int, Int)] = for {
      a <- State.get[Int]
      _ <- State.set[Int](10)
      b <- State.get[Int]
      _ <- State.modify[Int](_ + 1)
      c <- State.inspect[Int, Int](_ * 1000)
    } yield (a, b, c)

    program.run(0).value.tapPrint()
  }
}
