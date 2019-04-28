package CollectionBook

import scala.collection.mutable

object SetDemo {
  def main(args: Array[String]): Unit = {
    // 1: create add
    val set = mutable.Set[Int]()
    set.add(1)
    set ++= Seq(2, 3, 4)
    println(set)

    println(set.contains(3)) // true

    println(Set(1, 2, 3))

    // 2: del
    set -= 3
    set --= Seq(2, 4)
    println(set)
    //retain remove clear

    // 3: sorted
    val s = mutable.SortedSet(10, 4, 8, 2) //保持key的顺序
    s.add(5)
    println(s) // TreeSet(2, 4, 5, 8, 10)

    //LinkedHashSet 保持插入的顺序

    // 4: custom class of set
    case class Person(var name: String)
    implicit val PersonOrdering: Ordering[Person] = Ordering.fromLessThan[Person]((p1, p2) => p1.name < p2.name)
    val aleka = Person("Aleka")
    val christina = Person("Christina")
    val molly = Person("Molly")
    val tyler = Person("Tyler")
    val tyler2 = Person("Tyler2")

    val cs = mutable.SortedSet(molly, tyler, tyler2, christina, aleka)
    println(cs)
  }
}
