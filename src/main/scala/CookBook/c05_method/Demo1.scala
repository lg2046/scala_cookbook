package CookBook.c05_method

class Person(val name: String) {
  def printName(): Unit = println(name)

  def returnTuple(): (Int, Int) = (1, 2)

  def printAll(strings: String*): Unit = {
    strings foreach println
  }

  def apply2(name: String)(block: String => String): String = {
    block.apply(name)
  }
}

class Employee(name: String, val age: Int) extends Person(name) {
  override def printName(): Unit = super.printName()
}


object Demo1 {
  def main(args: Array[String]): Unit = {
    val employee = new Employee("google", 20)
    employee.printName()

    //    val (a, b) = employee.returnTuple()

    //    println(a + b)

    val ss = List("1", "2", "3")
    employee.printAll("1", "2", "3")

    employee.printAll(ss: _*)
    employee.printAll()

    val a = (i: Int) => i + 1
    println(a(10))

    val b: PartialFunction[Int, Double] = {
      case e if e > 1 => e + 1
    }

    employee.apply2("google") { st => st.toUpperCase }

    (employee.printName _).apply()
  }
}
