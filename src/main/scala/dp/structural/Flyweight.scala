package dp.structural

sealed abstract class Color

case object Red extends Color

case object Green extends Color

case object Blue extends Color

case object Yellow extends Color

case object Magenta extends Color


case class Circle(color: Color) {
  println(s"""Create a circle with $color color""")

  override def toString: String = s"""Circle($color)"""
}

object CircleFactory {
  val cache = collection.mutable.Map.empty[Color, Circle]

  def apply(color: Color): Circle = {
    cache.getOrElseUpdate(color, Circle(color))
  }

  def circlesCreated(): Int = cache.size
}

object Flyweight {
  def main(args: Array[String]): Unit = {
    println(CircleFactory(Blue))
    println(CircleFactory(Blue))
    println(CircleFactory(Blue))
    println(CircleFactory(Blue))
    println(CircleFactory(Blue))
    println(CircleFactory(Blue))
  }
}
