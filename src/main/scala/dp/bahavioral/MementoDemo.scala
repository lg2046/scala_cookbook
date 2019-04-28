package dp.bahavioral

import scala.collection.mutable
import scala.collection.mutable.ListBuffer

trait Memento[T] {
  protected val state: T

  def getState: T = state
}

trait Caretaker[T] {
  val states: ListBuffer[Memento[T]] = mutable.ListBuffer[Memento[T]]()
}

trait Originator[T] {
  def createMemento: Memento[T]

  def restore(memento: Memento[T])
}

private class TextEditorMemento(val state: String) extends Memento[String]

class TextEditor extends Originator[String] {
  /**
    * 文本框的内容操作 及增删
    * Originator应该是只知道如何创建memento的对象
    */
  var builder = new mutable.StringBuilder()

  def append(text: String): Unit = {
    builder.append(text)
  }

  def delete(): Unit = {
    if (builder.nonEmpty) {
      builder.deleteCharAt(builder.length - 1)
    }
  }

  def text(): String = builder.toString()

  /**
    * support for memento
    */
  override def createMemento: Memento[String] = {
    new TextEditorMemento(builder.toString)
  }

  override def restore(memento: Memento[String]): Unit = {
    this.builder = new mutable.StringBuilder(memento.getState)
  }
}

/**
  * 对TextEditor的包装, 对外直接使用
  */
class TextEditorManipulator extends Caretaker[String] {
  private val textEditor = new TextEditor

  def save(): Unit = {
    states.append(textEditor.createMemento)
  }

  def undo(): Unit = {
    if (states.nonEmpty) {
      textEditor.restore(states.remove(states.length - 1))
    }
  }

  def append(text: String): Unit = {
    save()
    textEditor.append(text)
  }

  def delete(): Unit = {
    save()
    textEditor.delete()
  }

  def readText(): String = textEditor.text()
}

object MementoDemo {
  def main(args: Array[String]): Unit = {
    val textEditorManipulator = new TextEditorManipulator
    textEditorManipulator.append("a")
    textEditorManipulator.append("b")
    textEditorManipulator.append("c")
    textEditorManipulator.delete()
    textEditorManipulator.delete()
    textEditorManipulator.delete()

    textEditorManipulator.undo()
    textEditorManipulator.undo()
    textEditorManipulator.undo()
    println(textEditorManipulator.readText())
  }
}
