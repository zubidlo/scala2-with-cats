package chapter2.examples

import cats._
import cats.implicits._

import scala.util.chaining.scalaUtilChainingOps

object CatsMonoid {
  def apply(): Unit = {
    Semigroup[Int].combine(1, 2) pipe println
    Monoid[String].combine("Cats", "Cats") pipe println
    Monoid[String].empty

    Monoid[Either[String, Double]].combine(Right(3.0), Right(1.5d)) pipe println
    Monoid[Either[String, Double]].combine(Left("hello"), Right(1.5d)) pipe println

    ("Hi" |+| "there") pipe println
    (1.some |+| None) pipe println
    (1.asRight |+| 2.asRight) pipe println

    "Scala" |+| " with" |+| " cats" pipe println

    1.some |+| None pipe println

    Map(1 -> "one", 2 -> "two") |+| Map(3 -> "three") pipe println
    ("hello", 123) |+| ("world", 456) pipe println

    def addAll[A: Monoid](values: List[A]): A =
      values.foldLeft(Monoid[A].empty)(_ |+| _)

    addAll(List(1, 2, 3)) pipe println
    addAll(List(1.some, 2.some, None)) pipe println
  }
}
