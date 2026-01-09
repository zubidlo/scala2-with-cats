import helpers.printThis

package object implicits {
  implicit class ChainingOpsWithPrintln[A](private val self: A) extends AnyVal {
    def tapPrint(color: String = Console.GREEN) = {
      printThis(color)(self)
      self
    }
  }
}
