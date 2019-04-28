package CookBook.c10_collection

import active_support._

case class Person(name: String)


//1:
//case class Person(name: String) extends Ordered[Person] {
//  override def compare(that: Person): Int = this.name.compareTo(that.name)
//}

//2:
//object Person {
//  implicit val PersonOrdering: Ordering[Person] = (x: Person, y: Person) => x.name.compareTo(y.name)
//}

//3:
//println(persons.sorted(Ordering.fromLessThan[Person](_.name > _.name)))


object Demo1 {


  def main(args: Array[String]): Unit = {
    val t = (1, 2, 3)
    t.productIterator.foreach { e => println(e.asInstanceOf[Int]) }

    val a = List.range(0, 1000000)
    println(a.sum)

    val x = List(1, 2, 3)
    println((1 to 10).foldLeft(Nil: List[Int]) { case (sum, _) => sum ++ x })
    println((1 to 10).flatMap(_ => x))


    val persons = List(Person("google"), Person("baidu"), Person("net"))
    persons.mkString("[", ", ", "]") tap println

    val m = Map(1 -> 2, 2 -> 3).withDefault(_ + 1)


    println(m.get(3))
  }
}
