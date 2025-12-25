package chapter2

import chapter2.examples.Monoid
import chapter2.examples.Monoid._
import org.scalatest.flatspec._
import org.scalatest.matchers._

class MonoidTest extends AnyFlatSpec with should.Matchers {
  "addition monoid laws" should "hold" in {
    implicit val additionMonoid: Monoid[Double] = new Monoid[Double] {
      override def combine(x: Double, y: Double): Double = x + y

      override def empty: Double = 0
    }

    associativeLaw(1.0, 2.0, 3.0) should be(true)
    identityLaw(1.0) should be(true)
  }

  "multiplication monoid laws" should "hold" in {
    implicit val multiplicationMonoid: Monoid[Double] = new Monoid[Double] {
      override def combine(x: Double, y: Double): Double = x * y

      override def empty: Double = 1
    }

    associativeLaw(1.0, 2.0, 3.0) should be(true)
    identityLaw(1.0) should be(true)
  }

  "string concat monoid laws" should "hold" in {
    implicit val stringConcatenationMonoid: Monoid[String] = new Monoid[String] {
      override def combine(x: String, y: String): String = x ++ y

      override def empty: String = ""
    }

    associativeLaw("a", "b", "c") should be(true)
    identityLaw("d") should be(true)
  }
}
