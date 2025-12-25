package chapter1.examples

import cats._
import cats.implicits._
import com.typesafe.scalalogging.LazyLogging

import java.util.Date

object EqExample extends LazyLogging {
  logger.info((1 === 1).show)
  logger.info(("1" =!= "1").show)

  logger.info((1.some === none).show)

  implicit val dateEq: Eq[Date] = Eq.instance((x, y) => x.getTime === y.getTime)

  logger.info((new Date(365 * 24 * 60 * 60 * 1000) === new Date(365 * 24 * 60 * 60 * 1000)).show)
}
