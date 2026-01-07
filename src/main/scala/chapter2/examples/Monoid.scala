package chapter2.examples

trait Semigroup[A] {
  def combine(x: A, y: A): A
}

trait Monoid[A] extends Semigroup[A] {
  def empty: A
}

object Monoid {
  def apply[A](implicit monoid: Monoid[A]): Monoid[A] = monoid

  def associativeLaw[A](x: A, y: A, z: A)
                       (implicit m: Monoid[A]): Boolean =
    m.combine(m.combine(x, y), z) == m.combine(x, m.combine(y, z))

  def identityLaw[A](x: A)(implicit m: Monoid[A]): Boolean =
    m.combine(x, m.empty) == x && m.combine(m.empty, x) == x
}
