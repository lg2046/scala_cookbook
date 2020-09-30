package CollectionBook

import scala.collection.mutable.ArrayBuffer

object ListDemo {
  def main(args: Array[String]): Unit = {
    //1: Populate
    List(1, 2, 3)
    List.range(1, 10)
    List.fill(3)("google")
    List.tabulate(5) { n => n * n }
    collection.mutable.ListBuffer(1, 2, 3)

    //2: 拼接
    "a" :: List("a", "b", "c")

    "c" +: List("a", "b", "c") //:在集合的位置 对于List 等同于::
    List("a", "b", "c") :+ "c"


    //3: 删除
    val originList = List(1, 2, 3, 4)
    val newList = originList.filter(_ > 2)
    println(originList.partition(_ > 2))
    println(originList.splitAt(3))
    println(originList.take(3))

    import scala.collection.mutable.ListBuffer
    val x = ListBuffer((1 to 10).toList: _*)
    x -= 5
    x --= List(1, 2, 3)
    x.remove(0)
    x.append(2, 3)
    println(x)
    //其他方法 +: :+ ++= --=
    //4: 串接
    x ++ List(3, 4, 5)
    println(Traversable.concat(x, List(1, 2, 3)))

    //5: Stream (lazy version of list)
    val stream = 1 #:: 2 #:: 3 #:: Stream.empty[Int]
    println(stream) //Stream(1, ?)
    println((1 to 10000).toStream)
    println(stream.head) //1
    println(stream.tail) // Stream(2, ?)
    println(stream.take(2)) // 下面三个都是返回Stream
    println(stream.filter(_ > 2)) //
    println(stream.map(_ * 2)) // 其他reverse

    println(stream.max)
    println(stream.sum)
    println(stream.size)
    println(stream) //Stream(1, 2, 3)  上面的max sum size strict方法会让stream在内存具体化

    //6: create 与 update Array
    val a = Array(1, 2, 3)
    val fruits = Array("apple", "banana", "orange")
    val x1 = Array(1, 2.0, 33D, 400L)
    val x2 = Array[Number](1, 2.0, 33D, 400L)

    // array不能动态变更大小，但是元素是可变的 不可变的优先使用Vector  可变的使用ArrayBuffer(更灵活)
    // array与java Array一一对应 即Array[Int]就是int[] Array[Double]就是 double[] Array[String]就是String[]
    a(0) = 10
    println(a.mkString(", "))
    println(x2)

    val x3 = Array.range(0, 10, 2) //fill tabulate toArray
    println(x3.mkString(", "))

    //7: ArrayBuffer
    val af = ArrayBuffer(1, 2, 3)
    af(0) = 10
    af += 4
    af ++= (7 to 9)
    println(af) // ArrayBuffer(10, 2, 3, 4, 7, 8, 9)

    af -= 8
    af --= List(2, 9) // remove clear
    println(af) // ArrayBuffer(10, 3, 4, 7)

    af.drop(2) //等同于slice
    af.take(2) //等同于slice

    println(af)

    // 9: sort array and arrayBuffer
    val fruits2 = Array("cherry", "apple", "banana")
    util.Sorting.quickSort(fruits2) // sort inPlace; 应存在Ordering[T]
    // fruits2.sortBy // sorted sortWith都可以接受扩展的Ordering
    println(fruits2.mkString(", "))

    // 10: 多维数组  ofDim只在数组中有
    val rows = 2
    val cols = 3
    val ma1 = Array.ofDim[String](rows, cols) //相当于java String arr[][]
    pretty2Array(ma1)
    pretty2Array(Array.fill(4)(Array.fill(4)(0)))
  }

  def pretty2Array[T](array: Array[Array[T]]): Unit = {
    println(array.map(_.mkString("[", ", ", "]")).mkString(", "))
  }
}
