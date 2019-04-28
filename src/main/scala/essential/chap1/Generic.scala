package essential.chap1

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future


case class Box[A](value: A)

trait MayBe[+A] {
  def flatMap[B](f: A => MayBe[B]): MayBe[B] = this match {
    case Full(a) => f(a)
    case Empty => Empty
  }

  def map[B](f: A => B): MayBe[B] = this match {
    case Full(a) => Full(f(a))
    case Empty => Empty
  }

}

case class Full[A](a: A) extends MayBe[A]

case object Empty extends MayBe[Nothing]


object Generic {
  def main(args: Array[String]): Unit = {
    println(Box("google").value)

    val callable: Runnable = () => println("runnable")

    callable.run()

    Future {
      println("run in future")
    }

    Thread.sleep(1)

    val mightFail1: MayBe[Int] = Full(1)
    val mightFail2: MayBe[Int] = Full(2)
    val mightFail3: MayBe[Int] = Full(2)

    println(mightFail1.flatMap { v1 =>
      mightFail2.flatMap { v2 =>
        mightFail3.map { v3 => v1 + v2 + v3 }
      }
    })
  }
}