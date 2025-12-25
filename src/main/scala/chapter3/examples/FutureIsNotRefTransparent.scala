package chapter3.examples

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration.DurationInt
import scala.concurrent.{Await, Future}
import scala.util.Random
import scala.util.chaining._

object FutureIsNotRefTransparent {
  def apply(): Unit = {
    val future1 = {
      val r = new Random(0L)
      val x = Future(r.nextInt())

      for {
        a <- x
        b <- x
      } yield (a, b)
    }

    val future2 = {
      val r = new Random(0L)

      for {
        a <- Future(r.nextInt())
        b <- Future(r.nextInt())
      } yield (a, b)
    }

    val result1 = Await.result(future1, 1.second) tap println
    val result2 = Await.result(future2, 1.second) tap println
  }
}
