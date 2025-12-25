package chapter2.exercises

import cats._
import cats.implicits._

import scala.util.chaining._

object SuperAdder {
  def add[A: Monoid](items: List[A]): A =
    items.foldLeft(Monoid[A].empty)(_ |+| _)

  case class Order(totalCost: Double, quantity: Double)

  object Order {
    implicit val orderMonoidInstance: Monoid[Order] = new Monoid[Order] {
      override def empty: Order = Order(0, 0.0)

      override def combine(x: Order, y: Order): Order =
        Order(x.totalCost + y.totalCost, x.quantity + y.quantity)
    }
  }

  def apply(): Unit = {
    add(List(1, 2, 3, 4)) pipe println
    add(List(5.some, Some(6), Some(7))) pipe println
    add(List(Order(1, 1), Order(1, 1))) pipe println
  }
}
