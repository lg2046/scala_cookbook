package dp.trait_and_mixin

object LinearizationOrder {
  def main(args: Array[String]): Unit = {
    trait A {
      print("A")
    }
    trait H {
      print("H")
    }
    trait S extends H {
      print("S")
    }
    trait R {
      print("R")
    }
    trait T extends R with H {
      print("T")
    }
    class B extends A with T with S {
      print("B")
    }

    new B // A R H T S B
  }
}
