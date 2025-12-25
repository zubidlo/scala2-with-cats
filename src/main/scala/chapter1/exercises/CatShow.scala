package chapter1.exercises

import cats._
import cats.implicits._
import com.typesafe.scalalogging.LazyLogging

object CatShow extends LazyLogging {
  implicit val catShow: Show[MyCat] =
    (c: MyCat) => s"${c.name} is a ${c.age} year-old ${c.color} cat."

  logger.info("Welcome to the Scala worksheet".show)
  logger.info(1234.show)
  val cat = MyCat("Garfield", 38, "orange and black")
  logger.info(cat.show)
}
