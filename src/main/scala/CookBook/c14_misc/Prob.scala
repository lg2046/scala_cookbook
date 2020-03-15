package CookBook.c14_misc

import scala.util.control.Breaks._

object Prob {
  def main(args: Array[String]): Unit = {
    //几何分布其实与方差
    val p = 0.2

    val epsilon = 1.0E-6

    //期望
    var expect: Double = 0
    var x = 1

    def prob(x: Int): Double = p * math.pow((1 - p), x - 1)

    breakable {
      while (true) {
        val delta = x * prob(x)
        if (delta > epsilon) {
          expect += delta
        } else {
          break()
        }

        x += 1
      }
    }


    println(s"max x: $x")
    println(s"expect: $expect")
  }
}
