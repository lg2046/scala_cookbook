package pragmatic_scala.chap5

object CorGeneric {
  //指定T是Pet的子类  这里Pet是类型T的上界
  def workWithPets[T <: Pet](pets: Array[T]): Unit = {
    pets.foreach(p => println(p.getClass == classOf[Dog]))
  }

  def copyPets[S, D >: S](fromPets: Array[S], toPets: Array[D]): Unit = {

  }

  def main(args: Array[String]): Unit = {
    val dogs: Array[Dog] = Array(new Dog("Rover"), new Dog("Comet"))
    workWithPets(dogs)

    val pets: Array[Pet] = Array[Pet]()

    copyPets(dogs, dogs)
  }
}

class Pet(val name: String) {
  override def toString: String = name
}

class Dog(override val name: String) extends Pet(name) {
  override def toString: String = s"Dog: $name"
}

