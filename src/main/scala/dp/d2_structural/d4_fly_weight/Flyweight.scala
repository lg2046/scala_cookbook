package dp.d2_structural.d4_fly_weight

case class Circle(color: String) {
  override def toString: String = s"""Circle($color)"""
}

object CircleFactory {
  val cache = collection.mutable.Map.empty[String, Circle]

  def apply(color: String): Circle = {
    cache.getOrElseUpdate(color, Circle(color))
  }

  def circlesCreated(): Int = cache.size
}

object Flyweight {
  def main(args: Array[String]): Unit = {
    println(CircleFactory("Blue") eq CircleFactory("Blue"))
    println(CircleFactory("Yellow") eq CircleFactory("Yellow"))
  }
}
