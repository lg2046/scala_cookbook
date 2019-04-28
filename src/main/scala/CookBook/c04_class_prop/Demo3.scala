package CookBook.c04_class_prop

class Animal(val name: String, val age: Int) {
  def this(name: String) = {
    this(name, 0)
  }
}

//子类在extends时即主构造器可以指定调父类的主构造器或者是副构造器
//在调用构造器时基本可以有默认参数
//class Dog(name: String, age: Int) extends Animal(name, age)
//class Dog(name: String) extends Animal(name)
class Dog(name: String) extends Animal(name, 0) {

  //但子类的辅助构造器只能调用自己类定义的主或副构造器
  def this() = {
    this("google")
  }
}


trait A {
  val a: Int
}

trait B {
  val a = 2
}

object Demo3 extends A {
  def main(args: Array[String]): Unit = {
    val dog = new Dog("dog")

    println(dog)

    println(a)

    Person2("google") match {
      case Person2(name) => println(name)
    }

    println(Person2("google").copy(name = "fk"))
  }

  override val a: Int = 20
}

case class Person2(name: String)

class Person(name: String, age: Int) {
  def canEqual(a: Any): Boolean = a.isInstanceOf[Person]

  override def equals(that: Any): Boolean = that match {
    case that: Person => that.canEqual(this) && this.hashCode == that.hashCode
    case _ => false
  }

  override def hashCode: Int = {
    val prime = 31
    var result = 1
    result = prime * result + age
    result = prime * result + (if (name == null) 0 else name.hashCode)
    result
  }
}