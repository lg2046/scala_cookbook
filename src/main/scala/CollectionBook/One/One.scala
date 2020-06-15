package CollectionBook.One

object One {

  val isEven: Int => Boolean = (i: Int) => if (i % 2 == 0) true else false

  val isEven2: Int => Boolean = _ % 2 == 0

  def main(args: Array[String]): Unit = {
    println(scala.collection.immutable.Map("a" -> "b").getClass)
    println(scala.collection.mutable.Map("a" -> "b").getClass)

    println(sum(List(1, 2, 3): _*))

    io.Source.fromFile("pom.xml").getLines().foreach(println)
  }

  def sum(ints: Int*): Int = {
    println(ints)

    val a = 1
    val b = a + 1

    val x = a / b

    println(x)

    ints.sum
  }
}
