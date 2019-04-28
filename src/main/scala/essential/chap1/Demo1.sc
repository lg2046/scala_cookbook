import java.util.Date

"hello world"


object FirstSteps {
  println("welcome to ")

  if (20 > 10) "left" else "right"

  val a = 123
  println(s"""the ultimate answer is $a""")

  def m1 = {
    val a = 1
    val b = 2
    val c = a + b
    c
  }
}

FirstSteps


def makeAdder(init: Int) = (add: Int) => add + init

val adder = makeAdder(10)
adder(20)

class Person(var name: String, var age: Int) {
  def str = name + age
}

val p = new Person("andy", 30)
p.age = 30
p


sealed trait Visitor {
  val id: String
  val created: Date

  def age: Long = new Date().getTime - created.getTime
}

case class Anonymous(id: String, created: Date = new Date()) extends Visitor

case class User(id: String, email: String, created: Date = new Date()) extends Visitor

