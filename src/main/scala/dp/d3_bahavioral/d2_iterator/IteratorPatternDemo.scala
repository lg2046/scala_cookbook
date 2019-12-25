package dp.d3_bahavioral

import scala.collection.mutable.ArrayBuffer


case class Student(name: String, age: Int)

class ClassRoom extends Iterable[d7_mediator.Student] {
  private val students = new ArrayBuffer[d7_mediator.Student]()


  override def iterator: Iterator[d7_mediator.Student] = new Iterator[d7_mediator.Student] {
    private var curPos = 0

    override def hasNext: Boolean = curPos < students.length

    override def next(): d7_mediator.Student = {
      val elem = students(curPos)
      curPos += 1
      elem
    }
  }

  def add(student: d7_mediator.Student): Unit = {
    students.append(student)
  }
}

object IteratorPatternDemo {
  def main(args: Array[String]): Unit = {
    val classRoom = new ClassRoom
    classRoom.add(d7_mediator.Student("andy", 10))
    classRoom.add(d7_mediator.Student("fly", 10))
    classRoom.add(d7_mediator.Student("lucy", 20))


    classRoom.foreach(println)
  }
}
