package chapter4.examples

import implicits._

import cats._
import cats.data._
import cats.implicits._

import scala.Console._

final case class Cat(name: String, favoriteFood: String)

object CatsReaderMonad {
  "Cats Reader Monad".tapPrint(BLUE)

  def apply(): Unit = {
    val catName = Reader[Cat, String](cat => cat.name)
    catName.run(Cat("Garfield", "Catnip")).tapPrint()

    val greetKitty: Reader[Cat, String] = catName.map(name => s"Hello $name")
    greetKitty.run(Cat("Kitty", "Food")).tapPrint()

    val feedKitty: Reader[Cat, String] = Reader(cat => s"Have a nice bowl of ${cat.favoriteFood}")

    val greetAndFeed = for {
      greet <- greetKitty
      feed <- feedKitty
    } yield s"$greet. $feed."

    greetAndFeed(Cat("Garfield", "Catnip")).tapPrint()
    greetAndFeed(Cat("Heathcliff", "junk food")).tapPrint()
  }
}
