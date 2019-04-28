package CookBook.c09_functional_prog

object Demo1 {
  def main(args: Array[String]): Unit = {
    val x = List.range(1, 10)
    x.filter(_ % 2 == 0) foreach println
    val a = (i: Int) => i % 2 == 0
    a.andThen(_.toString)
    a(2)

    val b = a.andThen(_.toString)

    applyCalc(29, math.cos)

    val c: Double => Double = math.pow(10, _: Double)
    c(2)

    val add3 = makeAdder(3)
    add3(10) // 13


    val divide = new PartialFunction[Int, Int] {
      override def apply(x: Int): Int = 42 / x

      override def isDefinedAt(x: Int): Boolean = x != 0
    }

    println(divide(20))
    println(divide.isDefinedAt(0))


    val divide2: PartialFunction[Int, Int] = {
      case x: Int if x != 0 => 42 / x
    }

    println(divide2(20))
    println(divide2.isDefinedAt(0))

    //divide2.orElse()
    //divide2.applyOrElse()
    //divide2.andThen()
  }

  def applyCalc(v: Double, f: Double => Double): Double = {
    f(v)
  }

  def makeAdder(adder: Int): Int => Int = _ + adder


}
