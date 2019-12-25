package pragmatic_scala.chap6

import scala.annotation.tailrec


trait Friend {
  def sayHello(): Unit = {
    println("sayHello")
  }
}

class Animal

object ResourceDemo {
  def main(args: Array[String]): Unit = {
    Resource.use(r => {
      r.op1()
      r.op2()
      r.op3()
      r.sayHello()
    })

    val s = new Animal with Friend
    s.sayHello()

    println(generate(0).take(200000).force)
  }

  @scala.annotation.tailrec
  def mad(parameter: Int): Int = {
    if (parameter == 0) {
      throw new RuntimeException("Error")
    } else {
      mad(parameter - 1)
    }
  }

  def generate(starting: Int): Stream[Int] = {
    starting #:: generate(starting + 1)
  }
}


class Resource private() extends Friend {
  println("Starting transaction...")

  private def cleanUp(): Unit = {
    println("Ending transaction...")
  }

  def op1(): Unit = println("Operation 1")

  def op2(): Unit = println("Operation 2")

  def op3(): Unit = println("Operation 3")
}

object Resource {
  def use(block: Resource => Unit): Unit = {
    val resource = new Resource
    try {
      block(resource)
    } finally {
      resource.cleanUp()
    }
  }
}