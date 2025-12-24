import typeClasses.Json
import typeClasses.JsonSyntax.JsonWriterOps
import typeClasses.Person
import typeClasses.JsonWriterInstances._

class Main extends App {
  println("hello world")

  private val martin = Person("Martin", "a@b.com")
  println(Json.toJson(martin))
  println(martin.toJson)
}
