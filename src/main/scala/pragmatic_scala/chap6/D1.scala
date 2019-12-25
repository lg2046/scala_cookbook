package pragmatic_scala.chap6

import java.util.Date

object D1 {
  def main(args: Array[String]): Unit = {
    println(totalResultOverRange(10, i => i * 2))

    val array = Array(2, 3, 5, 1, 6, 4)
    //    val result = reduce(array, 0) { (acc, e) => acc + e }
    val result = reduce(array, Integer.MIN_VALUE)(Math.max)
    println(result)

    val partialFun = log(new Date(1420095600000L), _)
    partialFun("google")
  }

  def totalResultOverRange(number: Int, block: Int => Int): Int = {
    var result = 0
    (1 to number).foreach { i =>
      result += block(i)
    }
    result
  }

  def reduce(list: Iterable[Int], init: Int)(f: (Int, Int) => Int): Int = {
    var acc = init
    list.foreach { e =>
      acc = f(acc, e)
    }
    acc
  }


  def log(date: Date, message: String): Unit = { //...
    println(s"$date ---- $message")
  }

}
