package playground

import scala.concurrent.{Await, Future}
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration._

class MyThread extends Thread {
  override def run(): Unit = {
    println("i am thread")
    println(Thread.currentThread().getName)

    LockObject.synchronized {
      LockObject.wait()
      println("2")
      1 / 0
    }

    println("continue")
  }
}

object LockObject

object Demo2 {
  def main(args: Array[String]): Unit = {
    val t = new MyThread
    t.setName("thread-name")

    t.setUncaughtExceptionHandler((t, e) => {
      e.printStackTrace()
      println("custom uncaughtException: ---", t, e)
    })

    t.start()


    new Thread(() => {
      println("i am thread 2")
    }).start()


    val f = Future {
      Thread.sleep(1000)
      println("i am in thread 3")
      1
    }

    f.map(_ + 3).foreach(println)

    Await.ready(f, 3 seconds)


    Thread.sleep(3000)

    LockObject.synchronized {
      LockObject.notify()
      println(1)
    }
  }
}
