package dp.d1_creational.d6_prototype

import scala.collection.mutable.ListBuffer

case class Cell(dna: String, proteins: ListBuffer[String]) extends Cloneable {
  //深拷贝  Cloneable接口中的clone方法
  override def clone(): Cell = {
    Cell(this.dna, this.proteins.clone())
  }
}

object Demo20 {
  def main(args: Array[String]): Unit = {
    val initializeCell = Cell("abcd", ListBuffer("protein1", "protein2"))

    //使用case class实现简单版的原型模式
    val copy1 = initializeCell.copy()
    val copy2 = initializeCell.copy(dna = "1234")

    val clone = initializeCell.clone()

    initializeCell.proteins += "google"

    println(copy1)
    println(copy2)

    println(clone)

  }
}