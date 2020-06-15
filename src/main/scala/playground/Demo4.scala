package playground

object Demo4 {
  def main(args: Array[String]): Unit = {
    println(mapScore(List(1, 2, 3, 4), List(1, 2, 3, 4)))


    println(mapScore(List(1, 2, 3, 4), List(2, 1, 3, 4)))
    println(mapScore(List(1, 2, 3, 4), List(1, 3, 2, 4)))
    println(mapScore(List(1, 2, 3, 4), List(1, 2, 4, 3)))

    println(mapScore(List(1, 2, 3, 4), List(4, 3, 2, 1)))
  }

  def mapScore(l1: List[Int], l2: List[Int]): Double = {

    var lastMatchCount = 0.0
    var scoreSum = 0.0
    for (i <- l1.indices) {
      if (l1(i) == l2(i)) {
        scoreSum += (lastMatchCount + 1) / (i + 1)
        lastMatchCount += 1
      } else {
        scoreSum += lastMatchCount / (i + 1)
      }
    }

    ((scoreSum / l1.length) * 1000).round / 1000.0
  }
}
