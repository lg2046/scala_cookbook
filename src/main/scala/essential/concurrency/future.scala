package essential.concurrency

object future {
  def main(args: Array[String]): Unit = {
    val l = List("a", "b", "c", "c", "d")
    for ((ele, idx) <- l.view.zipWithIndex if idx > 1) {
      println(ele, idx)
    }

    println(l.iterator.next())

    val x = for (x <- l) yield x
    println(x)

    println(l.sliding(2, 2).toList)

    println(l.scanLeft("a") { (a, b) => a + b })

    println(l.distinct)
    println(l.toSet)

    println(l.diff(List("c", "d")))

    Array.concat()

    val a = List(1, 2, 3, 4)

    val b = List(4, 5, 6, 7)

    println(a ++ b)

    (1 to 1000).map { e =>
      Thread.sleep(10)
      e * 2
    }


    (1, 2, 3).productIterator.toList
  }
}
