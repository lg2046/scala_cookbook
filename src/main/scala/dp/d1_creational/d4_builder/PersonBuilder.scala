package dp.d1_creational.d4_builder

case class Person(firstName: String, lastName: String, age: Int)

//对于有大量字段的对象 使用构造器比较方便
class PersonBuilder {
  private var firstName = ""
  private var lastName = ""
  private var age = 0

  def setFirstName(firstName: String): PersonBuilder = {
    this.firstName = firstName
    this
  }

  def setLastName(lastName: String): PersonBuilder = {
    this.lastName = lastName
    this
  }

  def setAge(age: Int): PersonBuilder = {
    this.age = age
    this
  }

  //可以进行一些有效性检验  //以及是否允许重复性构造
  def build: Person = {
    require(firstName != "", "should assign firstName")
    require(lastName != "", "should assign lastName")
    require(age > 0, "should assign age")
    Person(firstName, lastName, age)
  }
}


object Demo10 {
  def main(args: Array[String]): Unit = {
    val personBuilder = new PersonBuilder
    personBuilder.setFirstName("li")
    personBuilder.setLastName("guang")
    personBuilder.setAge(10)

    println(personBuilder.build)
  }
}