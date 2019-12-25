package dp.d4_scala_special

trait A {
  print("A")
}
trait H {
  print("H")
}
trait S extends H {
  print("S")
}
trait R extends H{
  print("R")
}
trait T extends R with H {
  print("T")
}
class B extends A with T with S {
  print("B")
}

object LinearizationOrder {
  def main(args: Array[String]): Unit = {


    new B // A R H T S B
  }
}
