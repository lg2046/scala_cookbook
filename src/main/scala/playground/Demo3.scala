package playground

import scala.collection.mutable.ArrayBuffer

object Demo3 {
  def main(args: Array[String]): Unit = {
    benchmark("Select Sort: ", 10000000) { () =>
      val arr = ArrayBuffer(3, 2, 7, 1, 4, 6, 9, 5, 0, 8, 10, 13, 14, 12, 16, 18)
      selectSort(arr)
    }

    benchmark("Pop Sort: ", 10000000) { () =>
      val arr = ArrayBuffer(3, 2, 7, 1, 4, 6, 9, 5, 0, 8, 10, 13, 14, 12, 16, 18)
      popSort(arr)
    }
  }

  def selectSort(ints: ArrayBuffer[Int]): Unit = {
    val n = ints.length
    for (i <- (n - 1) to 1 by -1) {
      //从0 - i中先找出最大的索引
      var max = ints(0)
      var max_i = 0
      for (j <- 1 to i) {
        if (ints(j) > max) {
          //交换最大的到最后面
          max = ints(j)
          max_i = j
        }
      }

      //将最大的插入最后面
      if (i != max_i) {
        val t = ints(i)
        ints(i) = ints(max_i)
        ints(max_i) = t
      }
    }
  }

  def popSort(ints: ArrayBuffer[Int]): Unit = {
    val length = ints.length
    for (i <- (length - 1) to 1 by -1) {
      for (j <- 0 until i) {
        if (ints(j) > ints(j + 1)) {
          val t = ints(j)
          ints(j) = ints(j + 1)
          ints(j + 1) = t
        }
      }
    }
  }

  def benchmark(title: String, times: Int)(block: () => Unit): Unit = {
    val start = System.currentTimeMillis()
    for (i <- 1 to times) {
      block()
    }

    val end = System.currentTimeMillis()
    println(s"Benchmark $title: ${end - start}")
  }
}
