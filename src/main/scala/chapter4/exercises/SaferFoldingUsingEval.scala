package chapter4.exercises

import implicits._
import cats._

import scala.Console._

object SaferFoldingUsingEval {
  private def foldRightEval[A, B](as: List[A], acc: Eval[B])(fn: (A, Eval[B]) => Eval[B]): Eval[B] =
    as match {
      case Nil => acc
      case head :: tail => Eval.defer(fn(head, foldRightEval(tail, acc)(fn)))
    }

  private def foldRight[A, B](as: List[A], acc: B)(fn: (A, B) => B): B =
    foldRightEval(as, Eval.now(acc)) { (a, b) => b.map(fn(a, _)) }.value

  def apply(): Unit = {
    "Safer Folding using Eval: no stack overflow".tapPrint(BLUE)
    foldRight((1 to 1000000).toList, 0)(_ + _).tapPrint()
  }
}
