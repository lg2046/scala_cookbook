//package CookBook.c04_class_prop
//
//class Person(val firstName: String) {
//  println("主构造器调用")
//
//  def printHello(): Unit = println("hello")
//
//  printHello()
//
//  def printFirstName(): Unit = {
//    println()
//  }
//
//
//  def this() = {
//    this("google")
//  }
//
//  val a = {
//    println("abc")
//    println("def")
//    123
//  }
//}
//
//
//class Exployee(firstName: String, val age: Int) extends Person(firstName) {
//
//}
//
//object Person {
//  def apply(): Person = new Person("google")
//}
//
//object Demo2 {
//  def main(args: Array[String]): Unit = {
//    val p = Person()
//    //    p.firstName = "a"
//    //    p.firstName = "google"
//
//    p.printFirstName()
//    println(p.firstName)
//  }
//}
//
//
