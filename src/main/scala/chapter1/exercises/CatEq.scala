package chapter1.exercises

import cats._
import cats.implicits._
import com.typesafe.scalalogging.LazyLogging

object CatEq extends LazyLogging {
  private val cat1 = MyCat("Garfield",
    38, "orange and black")
  private val cat2 = MyCat("Heathcliff", 33, "orange and black")
  private val optionCat1 = Option(cat1)
  private val optionCat2 = Option.empty[MyCat]

  implicit val myCatEq: Eq[MyCat] = Eq.fromUniversalEquals

  logger.info((cat1 === cat2).show)
  logger.info((cat1 =!= cat2).show)
  logger.info((optionCat1 === optionCat2).show)
  logger.info((optionCat1 =!= optionCat2).show)
}
