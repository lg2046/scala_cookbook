package CollectionBook

import collection.mutable.{Map => MMap}
import scala.collection.immutable.{ListMap, TreeMap}
import scala.collection.mutable

object MapDemo {
  def main(args: Array[String]): Unit = {
    // 1: 创建
    val states = Map("AL" -> "Alabama", "AK" -> "Alaska")
    println(states.size)

    // 2: operate
    val states2 = MMap("AL" -> "Alabama", "AK" -> "Alaska").withDefault { _ => "d" }
    states2 += ("CN" -> "Hala")
    states2 -= "AL"
    println(states2)

    println(states2("v")) // d
    println(states2) // withDefault也不会修改原Map

    //put retain remove clear

    val new_stat = states ++ List("ck" -> "google", "hl" -> "fk")
    println(new_stat)
    println(new_stat + ("c" -> "d"))
    println(new_stat - "ck")
    println(new_stat - "ck" - "hl")

    // 3: access
    println(new_stat("ck")) // google
    //println(new_stat("cks")) // exception
    println(new_stat.get("cks")) // 以Option或None返回
    println(new_stat.getOrElse("cks", "default")) // 以default返回

    val defaultMap = new_stat.withDefault { _ => "default_value" }
    //作为default值返回，但不会修改原map
    println(defaultMap("think"))
    println(defaultMap)

    // 4: traverse
    for ((k, v) <- states2) {
      println(k, v)
    }
    //迭代优先使用for 或者是foreach+case
    states2.foreach {
      case (k, v) => println(k, v)
    }

    states2.keys.foreach(println)
    states2.values.foreach(println)

    states2.mapValues(_.toUpperCase).foreach(println)
    // transform会修改原map
    states2.transform((k, v) => (k + v).toUpperCase).foreach(println)

    println(states2.keys) // keySet // keysIterator
    println(states2.values) // valuesIterator

    // 5: reverse
    println(states2.toList.map { case (k, v) => (v, k) }.toMap)
    println(for ((k, v) <- states2) yield (v, k)) //类似python的 for-comprehension

    // 6: check
    println(states2.contains("ck"))
    println(states2.contains("AK"))
    println(states2.values.exists(_ == "Alaska"))
    println(states2)

    // 7: Filter
    // mutable使用mutable
    val x = MMap(1 -> "a", 2 -> "b", 3 -> "c")
    x.retain((k, v) => k > 2)
    println(x)

    //immutable使用filter filterKeys filterNot
    val x2 = MMap(1 -> "a", 2 -> "b", 3 -> "c")
    println(x2.toMap.filter { case (k, _) => k <= 2 })
    println(x2.toMap.filterKeys(_ <= 2))
    println(x2.toMap.filterKeys(Set(1, 3)))

    // 8: Sort
    val grades = Map(
      "Kim" -> 90,
      "Al" -> 85,
      "Melissa" -> 95,
      "Emily" -> 91,
      "Hannah" -> 92
    )
    // ListMap反向保持插入的顺序
    println(ListMap(grades.toSeq.sortBy(_._1): _*))
    // TreeMap按key保持顺序
    println(TreeMap(grades.toSeq.sortBy(_._1): _*))
    //sortWith接两个参数
    println(ListMap(grades.toSeq.sortWith(_._1 > _._1): _*))
    println(ListMap(grades.toSeq.sortWith(_._1 < _._1): _*))

    //LinkedHashMap: 使用hashTable实现的mutable map, 以插入顺序保留   只有mutable版
    //ListMap有immutable和mutable版
    println(mutable.LinkedHashMap(grades.toSeq.sortWith(_._1 > _._1): _*))

    // 9: Max
    println(grades.max) //(Melissa,95)按key的最大 返回kv pair
    println(grades.keysIterator.max) // Melissa
    println(grades.valuesIterator.max) // 95
    println(grades.keysIterator.reduceLeft((x, y) => if (x > y) x else y)) //可以达到类似于maxBy的效果
    println(grades.keysIterator.maxBy(_.length))
  }
}
