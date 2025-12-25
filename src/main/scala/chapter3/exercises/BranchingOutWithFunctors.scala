package chapter3.exercises

import cats._
import cats.implicits._
import helpers.Utils.printThis

import scala.util.chaining._

object BranchingOutWithFunctors {
  sealed trait Tree[+A]

  final case class Branch[A](left: Tree[A], right: Tree[A]) extends Tree[A]

  final case class Leaf[A](value: A) extends Tree[A]

  object Tree {
    // smart constructors
    def branch[A](left: Tree[A], right: Tree[A]): Tree[A] = Branch(left, right)

    def leaf[A](value: A): Tree[A] = Leaf(value)
  }

  def apply(): Unit = {
    implicit val treeFunctor: Functor[Tree] = new Functor[Tree] {
      override def map[A, B](ta: Tree[A])(ab: A => B): Tree[B] = ta match {
        case Leaf(a) => Leaf(value = ab(a))
        case Branch(left, right) => Branch(left = map(left)(ab), right = map(right)(ab))
      }
    }

    Tree.branch(left = Leaf(1), right = Leaf(2)) map {
      _ * 100
    } pipe printThis()
  }
}
