package CookBook.c03_control_structure

import java.io.IOException

import scala.util.control.Breaks._

object Demo1 {
  def main(args: Array[String]): Unit = {
    //    val result = for {
    //      i <- 1 to 3
    //      j <- 1 to 3
    //    } yield i + j
    //
    //    println(result)
    //
    //
    //    val result2 = (1 to 3).flatMap { i =>
    //      (1 to 3).map { j =>
    //        i + j
    //      }
    //    }
    //    println(result2)
    //
    //    val l = List(1, 2, 3)
    //
    //    for (i <- l if i > 2) {
    //      println(i)
    //    }
    //    //翻译为
    //    l.withFilter(_ > 2).foreach(println)

    for (i <- 1 to 10) {
      breakable {
        if (i > 5 && i < 8) {
          break
        } else {
          println(i)
        }
      }

    }

    println("google".count(_ == 'o'))



  }
}
