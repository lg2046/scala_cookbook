package pragmatic_scala.chap1

import java.lang.Thread.UncaughtExceptionHandler

class Demo extends Thread {
  override def run(): Unit = {
    //    println(Thread.currentThread())
    //    println("i am run")
  }
}

object Demo {
  def main(args: Array[String]): Unit = {
    //    println(Thread.currentThread())
    //    (new Demo).start()
    //
    //    new Thread(() => {
    //      println("abc")
    //    }).start()
    //
    //    val t1 = new Thread(() => {
    //      println(Thread.currentThread())
    //      println("i am runnable")
    //
    //      1 / 0
    //      try {
    //        Thread.sleep(10000)
    //      } catch {
    //        case e: InterruptedException => println("我被interrupted")
    //      }
    //
    //    })
    //    t1.setUncaughtExceptionHandler((t: Thread, e: Throwable) => println(t, e, "有异常"))
    //
    //    t1.start()
    //
    //    Thread.sleep(3000)
    //    t1.interrupt()

    //val itemId = "2000550039978"

    //println(isItemValid(itemId))
  }

}
