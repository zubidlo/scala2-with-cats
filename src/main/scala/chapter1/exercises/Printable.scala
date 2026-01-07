package chapter1.exercises

trait Printable[A] {
  def format(value: A): String
}

object Printable {
  def format[A](value: A)(implicit p: Printable[A]): String =
    p.format(value)

  def print[A](value: A)(implicit p: Printable[A]): Unit =
    println(format(value))
}

object PrintableInstances {
  implicit val stringPrintable: Printable[String] =
    (value: String) => value

  implicit val intPrintable: Printable[Int] =
    (value: Int) => value.toString
}

object PrintableSyntax {
  implicit class PrintableOps[A](value: A) {
    def f(implicit p: Printable[A]): String = p.format(value)

    def print(implicit p: Printable[A]): Unit = println(p.format(value))
  }
}

object PrintableUsages {
  implicit val catPrintable: Printable[MyCat] = (c: MyCat) =>
    s"${c.name} is a ${c.age} year-old ${c.color} cat"

  import PrintableInstances._
  import PrintableSyntax._

  Printable.format("A")
  Printable.print("A")
  "B".f.print
  Printable.format(1)
  Printable.print(1)
  2.f.print

  MyCat("cat", 10, "red").f.print
}
