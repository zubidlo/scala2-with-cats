package chapter4.exercises

import cats.data._
import cats.implicits._
import implicits._

import scala.Console._

final case class Db(usernames: Map[Int, String], passwords: Map[String, String])

object HackingOnReaders {
  "Hacking On Readers".tapPrint(BLUE)

  private val users = Map(
    1 -> "dade",
    2 -> "kate",
    3 -> "margo"
  )

  private val passwords = Map(
    "dade"  -> "zerocool",
    "kate"  -> "acidburn",
    "margo" -> "secret"
  )

  val db = Db(users, passwords)

  private type DbReader[A] = Reader[Db, A]

  private def findUsername(userId: Int): DbReader[Option[String]] =
    Reader(_.usernames.get(userId))

  private def checkPasswd(username: String, password: String): DbReader[Boolean] =
    Reader(_.passwords.get(username).contains(password))

  private def checkLogin(userId: Int, password: String): DbReader[Boolean] =
    findUsername(userId)
      .flatMap(_.map(checkPasswd(_, password))
      .getOrElse(false.pure[DbReader]))

  def apply(): Unit = {
    checkLogin(1, "zerocool").run(db).tapPrint()
    checkLogin(2, "acidburn").run(db).tapPrint()
    checkLogin(4, "davinci").run(db).tapPrint()
  }
}
