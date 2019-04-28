package dp.bahavioral

import scala.collection.mutable

trait Notifiable {
  def notify(message: String)
}

case class Student(name: String, age: Int) extends Notifiable {
  override def notify(message: String): Unit = {
    println(s"""Student $name was notified with message: '$message'""")
  }
}

case class Group(name: String)

trait Mediator {
  def addStudentToGroup(student: Student, group: Group): Unit

  def isStudentInGroup(student: Student, group: Group): Boolean

  def removeStudentFromGroup(student: Student, group: Group): Unit

  def notifyStudentsInGroup(group: Group, message: String): Unit
}

class SchoolMediator extends Mediator {
  val studentsToGroups: mutable.Map[Student, mutable.Set[Group]] = mutable.Map()
  val groupsToStudents: mutable.Map[Group, mutable.Set[Student]] = mutable.Map()

  override def addStudentToGroup(student: Student, group: Group): Unit = {
    studentsToGroups.getOrElseUpdate(student, mutable.Set()) += group
    groupsToStudents.getOrElseUpdate(group, mutable.Set()) += student
  }

  override def isStudentInGroup(student: Student, group: Group): Boolean = {
    groupsToStudents.getOrElse(group, mutable.Set()).contains(student) &&
      studentsToGroups.getOrElse(student, mutable.Set()).contains(group)
  }

  override def removeStudentFromGroup(student: Student, group: Group): Unit = {
    studentsToGroups.getOrElseUpdate(student, mutable.Set()) -= group
    groupsToStudents.getOrElseUpdate(group, mutable.Set()) -= student
  }

  override def notifyStudentsInGroup(group: Group, message: String): Unit = {

    groupsToStudents.get(group).foreach { students =>
      students.foreach(s => s.notify(message))
    }
  }
}

object MediatorDemo {
  def main(args: Array[String]): Unit = {
    val schoolMediator = new SchoolMediator
    // create students
    val student1 = Student("Ivan", 26)
    val student2 = Student("Maria", 26)
    val student3 = Student("John", 25)
    // create groups
    val group1 = Group("Scala design patterns")
    val group2 = Group("Databases")
    val group3 = Group("Cloud computing")

    schoolMediator.addStudentToGroup(student1, group1)
    schoolMediator.addStudentToGroup(student1, group2)
    schoolMediator.addStudentToGroup(student2, group2)
    schoolMediator.addStudentToGroup(student2, group3)
    schoolMediator.addStudentToGroup(student3, group1)
    schoolMediator.addStudentToGroup(student3, group3)

    println(schoolMediator.isStudentInGroup(student1, group1))
    println(schoolMediator.isStudentInGroup(student1, group3))

    schoolMediator.notifyStudentsInGroup(group3, s"${group3.name} is about to open")
  }
}
