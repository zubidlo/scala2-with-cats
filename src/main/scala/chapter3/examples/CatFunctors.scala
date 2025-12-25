package chapter3.examples

import cats._
import cats.implicits._

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.{ExecutionContext, Future}
import scala.util.chaining._

object CatFunctors {
  def apply(): Unit = {
    val func = (x: Int) => x + 1

    val liftedFunc = Functor[Option].lift(func)

    liftedFunc(1.some) pipe println
    Functor[List].as(List(1, 2, 3), 2.some) pipe println

    def doMath[F[_] : Functor](start: F[Int]): F[Int] =
      start.map(_ + 2)

    doMath(20.some) pipe println
    doMath(List(1, 2, 3)) pipe println

    final case class Box[A](value: A)

    implicit val boxFunctor: Functor[Box] = new Functor[Box] {
      override def map[A, B](ba: Box[A])(ab: A => B): Box[B] =
        Box(ab(ba.value))
    }

    doMath(Box(1)) pipe println

    implicit def futureFunctor(implicit ec: ExecutionContext): Functor[Future] =
      new Functor[Future] {
        override def map[A, B](fa: Future[A])(ab: A => B): Future[B] =
          fa.map(ab)(ec)
      }

    Functor[Future]
  }
}
