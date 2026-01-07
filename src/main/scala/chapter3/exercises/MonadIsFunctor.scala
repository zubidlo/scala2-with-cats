package chapter3.exercises

import helpers.Utils.printThis

import scala.util.chaining._

object MonadIsFunctor {
  trait Monad[F[_]] {
    def pure[A](a: A): F[A]

    def flatMap[A, B](fa: F[A])(afb: A => F[B]): F[B]

    def map[A, B](fa: F[A])(ab: A => B): F[B] =
      flatMap(fa)(a => pure(ab(a)))
  }

  object Monad {
    def apply[F[_] : Monad]: Monad[F] = implicitly[Monad[F]]
  }


  def apply(): Unit = {
    "Monad isFunctor" pipe printThis(Console.BLUE)
  }
}
