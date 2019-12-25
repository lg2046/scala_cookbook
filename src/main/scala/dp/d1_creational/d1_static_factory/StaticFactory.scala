package dp.d1_creational.d1_static_factory

//简单工厂
//弱点是增加类要改代码，但好处是简单
trait Animal

class Bird extends Animal

class Mammal extends Animal

class Fish extends Animal

object Animal {
  def apply(animal: String): Animal = animal.toLowerCase match {
    case "bird" => new Bird
    case "mammal" => new Mammal
    case "fish" => new Fish
    case x => throw new Exception(s"""Unknown animal $x""")
  }

  def apply(klass: Class[_]): Animal =
    klass.newInstance().asInstanceOf[Animal]
}

object Demo1 {
  def main(args: Array[String]): Unit = {
    println(Animal("Bird"))
    println(Animal("Mammal"))


    println(Animal(classOf[Bird]))
  }
}