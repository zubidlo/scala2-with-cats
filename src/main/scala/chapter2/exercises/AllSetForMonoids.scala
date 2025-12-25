package chapter2.exercises

import chapter2.examples.Monoid
import chapter2.examples.Monoid.{associativeLaw, identityLaw}
import com.typesafe.scalalogging.LazyLogging

object AllSetForMonoids extends LazyLogging {
  private def assertIntSetLaws(name: String)(implicit m: Monoid[Set[Int]]): Unit = {
    logger.info(s"$name monoid laws should hold")
    assert(identityLaw(Set.empty[Int]))
    assert(identityLaw(Set(1)))
    assert(associativeLaw(Set(1), Set(1), Set(1)))
    assert(associativeLaw(Set.empty[Int], Set(1), Set(1)))
    // ...
    assert(associativeLaw(Set.empty[Int], Set.empty[Int], Set.empty[Int]))
  }

  private def unionMonoid(): Unit = {
    implicit def m[A]: Monoid[Set[A]] = new Monoid[Set[A]] {
      override def empty: Set[A] = Set.empty[A]

      override def combine(x: Set[A], y: Set[A]): Set[A] = x union y
    }

    assertIntSetLaws("intSetUnionMonoid")

    logger.info("stringSetUnionMonoid monoid laws should hold")
    assert(identityLaw(Set.empty[String]))
    assert(identityLaw(Set("a")))
    assert(associativeLaw(Set("a"), Set("a"), Set("a")))
    assert(associativeLaw(Set.empty[String], Set("a"), Set("a")))
    // ...
    assert(associativeLaw(Set.empty[String], Set.empty[String], Set.empty[String]))
  }

  private def symDiffMonoid(): Unit = {
    implicit def m[A]: Monoid[Set[A]] = new Monoid[Set[A]] {
      override def empty: Set[A] = Set.empty

      override def combine(x: Set[A], y: Set[A]): Set[A] = (x diff y) union (y diff x)
    }

    assertIntSetLaws("symDiffMonoid")
  }


  def apply(): Unit = {
    unionMonoid()
    symDiffMonoid()
  }
}
