package CollectionBook.One

import java.util.concurrent.{ConcurrentHashMap, ConcurrentMap, ForkJoinPool}

import scala.concurrent.ExecutionContext

object Two {
  def printIt(c: Char): Unit = {
    println(c)
  }

  def main(args: Array[String]): Unit = {
    val x = Vector(1, 2, 3)
    "abc".foreach(printIt)
    "abc".foreach { c => println(c) }

    val m = Map("fname" -> "Tyler", "lname" -> "LeDude")

    println(m.collect {
      //      case ("g", "b") => "gb"
      case (k, v) => println(k, v)
    })

    val fruids = Traversable(1, 2, 3)

    val tm = scala.collection.concurrent.TrieMap(1 -> 2)
    tm ++= List(2 -> 3, 3 -> 4, 3 -> 5)

    println(tm)

    val r = new Thread() {
      override def run(): Unit = println("i am thread")
    }

    val r2: Runnable = () => println("i am runnable")

    r.run()

    val executor: ExecutionContext = ExecutionContext.fromExecutorService(new ForkJoinPool(10))

    executor.execute { () => println("abc") }


  }
}
