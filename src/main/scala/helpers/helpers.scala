package object helpers {
  def printThis(c: String = Console.GREEN)(x: Any): Unit =
    println(c + x + Console.RESET)
}
