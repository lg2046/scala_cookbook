package CollectionBook

import scala.collection.mutable

object QueueDemo {
  def main(args: Array[String]): Unit = {
    // 1: Queue
    val strq = mutable.Queue[String]()


    strq += "apple"
    strq += ("kiwi", "banana")
    println(strq)

    strq.enqueue("pineapple")
    println(strq) // Queue(apple, kiwi, banana, pineapple)

    val next = strq.dequeue()
    println(next) // apple
    println(strq) // Queue(kiwi, banana, pineapple)

    println(strq.dequeueFirst(_.startsWith("b"))) // Some(banana)  dequeueFirst取出满足条件的第一个元素
    println(strq) //Queue(kiwi, pineapple)

    println(strq.dequeueAll(_.length > 6)) //ArrayBuffer(pineapple) 取出所有满足条件的元素
    println(strq) //Queue(kiwi)

    //Queue extends from Iterable 和 Traversable ， 所以有所有collection通用方法，比如  foreach map

    // 2: Stack
    // LIFO后进先出数据结构 使用push pop通用操作
    val is = mutable.Stack[Int]()
    is.push(1)
    is.push(2)
    is.push(3)
    println(is) //Stack(3, 2, 1)

    val ints = mutable.Stack(1, 2, 3)
    ints.push(4) //push到顶部
    println(ints) // Stack(4, 1, 2, 3)
    println(ints.pop()) // 4 从顶部取出
    println(ints) // Stack(1, 2, 3)

    println(ints.top) // 1 不从顶部取出，只是查看
    println(ints) // Stack(1, 2, 3)

    // Stack extends  Seq, 所以可以使用一些通用方法
    println(ints.size) // 3
    println(ints.isEmpty) // false

    ints.clear()
    println(ints)
  }
}
