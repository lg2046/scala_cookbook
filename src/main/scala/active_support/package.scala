
package object active_support {

  implicit class StringOps(repr: String) {
    def increment: String = repr.map { c => (c + 1).toChar }

    def toInt(radix: Int): Int = {
      Integer.parseInt(repr, radix)
    }

    def length2: Int = repr.length
  }

  implicit class DoubleOps(repr: Double) {
    def ~=(other: Double): Boolean = {
      if (math.abs(repr - other) < 0.00001) true else false
    }
  }

  implicit class AnyOps[T](obj: T) {
    def tap(block: T => Unit): T = {
      block.apply(obj)
      obj
    }
  }

}
