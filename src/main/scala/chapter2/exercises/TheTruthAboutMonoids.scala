package chapter2.exercises

import chapter2.examples.Monoid
import chapter2.examples.Monoid._
import com.typesafe.scalalogging.LazyLogging

object TheTruthAboutMonoids extends LazyLogging {
  private def assertLaws(name: String)(implicit m: Monoid[Boolean]): Unit = {
    logger.info(s"$name monoid laws should hold")
    assert(identityLaw(true))
    assert(identityLaw(false))
    assert(associativeLaw(true, true, true))
    assert(associativeLaw(false, true, true))
    // ...
    assert(associativeLaw(false, false, false))
  }

  private def andMonoid(): Unit = {
    implicit val m: Monoid[Boolean] = new Monoid[Boolean] {
      override def empty: Boolean = true

      override def combine(x: Boolean, y: Boolean): Boolean = x && y
    }

    assertLaws("AND")
  }

  private def orMonoid(): Unit = {
    implicit val m: Monoid[Boolean] = new Monoid[Boolean] {
      override def empty: Boolean = false

      override def combine(x: Boolean, y: Boolean): Boolean = x || y
    }

    assertLaws("OR")
  }

  private def xorMonoid(): Unit = {
    implicit val m: Monoid[Boolean] = new Monoid[Boolean] {
      override def empty: Boolean = false

      override def combine(x: Boolean, y: Boolean): Boolean = (x && !y) || (!x && y)
    }

    assertLaws("XOR")
  }

  private def xandMonoid(): Unit = {
    implicit val m: Monoid[Boolean] = new Monoid[Boolean] {
      override def empty: Boolean = true

      override def combine(x: Boolean, y: Boolean): Boolean = (!x || y) && (x || !y)
    }

    assertLaws("XAND")
  }

  def apply(): Unit = {
    andMonoid()
    orMonoid()
    xorMonoid()
    xandMonoid()
  }
}
