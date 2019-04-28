
package CookBook.c01_string

import active_support._

object Demo2 {
  def main(args: Array[String]): Unit = {
    println("google".increment)

    val add: (Int, Int) => Int = (x, y) => x + y

    println(add(10, 20))
    println(add.getClass.getSimpleName)
    println(add.tap(println))

    println(sum2(1, 2, 3))

    val l = List(1, 2, 3)
    println(sum2(l: _*))

    val par: (Int, Int) => Int = aa(_, 20, _)
    println(par.curried)

    "a|b|c".split("|")

    try {
      val a = 1 / (3 - 3)
      println(a)
    } catch {
      case e: Exception => println(e)
    } finally {
      println("finally")
    }

    // == 值比较 eq引用比较
    println("a" == "a")
    println("a".eq(new String("a")))

    val a: Byte = 10
    println(a)

    //Try("google".toInt)

    (1 to 10).map { x => scala.util.Random.nextPrintableChar() }.mkString(",")
  }

  def aa(x: Int, y: Int, z: Int): Int = {
    x + y + z
  }

  def sum2(xs: Int*): Int = {
    xs.sum
  }
}
