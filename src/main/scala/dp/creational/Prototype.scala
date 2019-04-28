package dp.creational

case class Cell(dna: String, proteins: List[String])

object Demo20 {
  def main(args: Array[String]): Unit = {
    val initializeCell = Cell("abcd", List("protein1", "protein2"))

    //使用case class实现简单版的原型模式
    val copy1 = initializeCell.copy()
    val copy2 = initializeCell.copy(dna = "1234")

    println(copy1)
    println(copy2)
  }
}