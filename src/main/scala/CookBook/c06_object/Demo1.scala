package CookBook.c06_object

import java.util.{Random => _}

import CookBook.c05_method.Person
import active_support._


object Demo1 {
  def main(args: Array[String]): Unit = {
    val a: Object = "google"

    println(a.asInstanceOf[String])

    val i = 10
    println(i.asInstanceOf[Long].getClass) //long
//    classOf[Long] //类似java的java.lang.Long.class

    classOf[Person].getMethods.foreach(println)

    println("google".length2)
  }
}
