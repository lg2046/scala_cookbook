package dp.bahavioral

//import scala.collection.mutable.ArrayBuffer
//
//
//case class Student(name: String, age: Int)
//
//class ClassRoom extends Iterable[Student] {
//  private val students = new ArrayBuffer[Student]()
//
//  override def iterator: Iterator[Student] = new Iterator[Student] {
//    var currentPos = 0
//    val allSize: Int = students.size
//
//    override def hasNext: Boolean = currentPos < allSize
//
//    override def next(): Student = {
//      val item = students(currentPos)
//      currentPos += 1
//      item
//    }
//  }
//
//  def add(student: Student): Unit = {
//    students.append(student)
//  }
//}
//
//object IteratorPatternDemo {
//  def main(args: Array[String]): Unit = {
//    val classRoom = new ClassRoom
//    classRoom.add(Student("andy", 10))
//    classRoom.add(Student("fly", 10))
//    classRoom.add(Student("lucy", 20))
//
//
//    classRoom.filter(_.age > 10).foreach(println)
//  }
//}
