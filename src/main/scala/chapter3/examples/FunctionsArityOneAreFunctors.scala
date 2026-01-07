package chapter3.examples

import cats.implicits._

import scala.util.chaining._

object FunctionsArityOneAreFunctors {
  def apply(): Unit = {
    val func1: Int => Double = (x: Int) => x.toDouble
    val func2: Double => Double = (x: Double) => x * 2

    val func3 = ((x: Int) => x.toDouble)
      .map(func2)
      .map(x => x + 2)
      .map(x => s"$x!")

    func2(func1(1)) pipe println
    (func1 map func2)(1) pipe println
    (func1 andThen func2)(1) pipe println
    func3(1) pipe println
  }
}
