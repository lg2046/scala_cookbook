package dp.structural

import scala.collection.mutable.ListBuffer


trait Node {
  def print(prefix: String = ""): Unit
}

case class Leaf(data: String) extends Node {
  override def print(prefix: String): Unit = println(s"""$prefix$data""")
}

case class Tree() extends Node {
  private val children = ListBuffer.empty[Node]

  override def print(prefix: String): Unit = {
    println(s"""$prefix(""")
    children.foreach(_.print(s"$prefix$prefix"))
    println(s"""$prefix)""")
  }

  def add(child: Node): Unit = {
    children += child
  }

  def remove(): Unit = {
    if (children.nonEmpty) {
      children.remove(0)
    }
  }
}


object Composite {
  def main(args: Array[String]): Unit = {
    val tree = Tree()
    tree.add(Leaf("leaf 1"))

    val subtree1 = Tree()
    subtree1.add(Leaf("leaf 2"))

    val subtree2 = Tree()
    subtree2.add(Leaf("leaf 3"))
    subtree2.add(Leaf("leaf 4"))
    subtree1.add(subtree2)

    tree.add(subtree1)

    val subtree3 = Tree()
    val subtree4 = Tree()
    subtree4.add(Leaf("leaf 5"))
    subtree4.add(Leaf("leaf 6"))
    subtree3.add(subtree4)
    tree.add(subtree3)

    tree.print("-")
  }
}
