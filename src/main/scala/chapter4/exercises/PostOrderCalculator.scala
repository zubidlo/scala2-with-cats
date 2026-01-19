package chapter4.exercises

import implicits._
import cats._
import cats.implicits._
import cats.data.State

import scala.Console._

object PostOrderCalculator {
  "Post Order Calculator".tapPrint(BLUE)

  def apply(): Unit = {
    type CalcState[A] = State[List[Int], A]

    def operand(num: Int) = State[List[Int], Int] {
      stack => (num :: stack, num)
    }

    def operator(func: (Int, Int) => Int) = State[List[Int], Int] {
      case b :: a :: tail =>
        val ans = func(a, b)
        (ans :: tail, ans)

      case _ => sys.error("Fail!")
    }

    def evalOne(sym: String) = sym match {
      case "+" => operator(_ + _)
      case "-" => operator(_ - _)
      case "*" => operator(_ * _)
      case "/" => operator(_ / _)
      case num => operand(num.toInt)
    }

    def evalAll(input: List[String]): CalcState[Int] =
      input.foldLeft(0.pure[CalcState]) { (a, b) => a.flatMap(_ => evalOne(b)) }

    val program = for {
      _ <- evalOne("1")
      _ <- evalOne("2")
      _ <- evalOne("+")
      _ <- evalOne("10")
      r <- evalOne("*")
    } yield r

    program.run(Nil).value.tapPrint()

    evalOne("1")
      .flatMap(_ => evalOne("2"))
      .flatMap(_ => evalOne("+"))
      .flatMap(_ => evalOne("10"))
      .flatMap(_ => evalOne("*"))
      .run(Nil)
      .value
      .tapPrint()

    evalAll(List("1", "2", "+", "10", "*")).run(Nil).value.tapPrint()

    evalAll(List("1", "2", "+", "10", "*"))
      .flatMap(_ => evalOne("100"))
      .flatMap(_ => evalAll(List("*", "5", "/")))
      .runA(Nil)
      .value
      .tapPrint()

    val biggerProgram = for {
      _ <- evalAll(List("1", "2", "+", "10", "*"))
      _ <- evalOne("100")
      r <- evalAll(List("*", "5", "/"))
    } yield r

    biggerProgram.runA(Nil).value.tapPrint()

    def evalInput(input: String): Int =
      evalAll(input.split(" ").toList).runA(Nil).value

    evalInput("1 2 + 10 * 100 * 5 /").tapPrint()
  }
}
