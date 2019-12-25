package pragmatic_scala.chap1

import scala.collection.mutable

object D2_simplify {
  def main(args: Array[String]): Unit = {
    //public class Greetings {
    //public static void main(String[] args) {
    // for(int i = 1; i < 4; i++) {
    //  System.out.print(i + ","); }
    //  System.out.println("Scala Rocks!!!");
    //}
    //}
    (1 to 3).foreach(i => println(s"$i, Scala Rocks!!!"))


    println(raw"regex_extract(column, 'a\\\\.b') ")

    println(Marker("blue"))
    println(Marker("red"))
    println(Marker("red"))
    println(Marker("green"))
    Currency.values.foreach(println)
  }
}

class Message[T](val content: T) {
  override def toString = s"message content is $content"

  def is(value: T): Boolean = value == content
}


class Marker private(val color: String) {
  override def toString: String = s"marker color $color"
}

object Marker {
  val markers: mutable.Map[String, Marker] = mutable.Map(
    "red" -> new Marker("red"),
    "blue" -> new Marker("blue"),
    "yellow" -> new Marker("yellow")
  )

  def apply(color: String): Marker = {
    markers.getOrElseUpdate(color, new Marker(color))
  }
}

object Currency extends Enumeration {
  type Currency = Value
  val CNY, GBP, INR = Value
}