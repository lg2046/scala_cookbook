package dp.d2_structural.d2_decorator

//所有可叠加功能的基类 Stack Trait
trait InputWriter {
  def write(str: String): Unit
}

trait InputWriterDecorator extends InputWriter


trait AddLineNumberWriter extends InputWriterDecorator {
  private var lineNumber_AddLineNumberWriter = 1

  abstract override def write(str: String): Unit = {
    println("add line number")
    super.write(s"""$lineNumber_AddLineNumberWriter: $str""")
    lineNumber_AddLineNumberWriter += 1
  }
}

trait AddTimeStampWriter extends InputWriterDecorator {
  abstract override def write(str: String): Unit = {
    println(println("add time stamp"))
    super.write(s"""${System.currentTimeMillis()}: $str""")
  }
}

trait UpperCaseWriter extends InputWriterDecorator {
  abstract override def write(str: String): Unit = {
    println("add upper case")
    super.write(str.toUpperCase)
  }
}

class BasicWriter extends InputWriter {
  override def write(str: String): Unit = {
    println("basic")
    println(str)
  }
}

object DecoratorMain {
  def main(args: Array[String]): Unit = {
    //使用Trait实现 只增强现有的方法 且不会覆盖其他方法

    //传递的参数会从右往左进行应用，走到BasicWriter的write, 相当于对BasicWriter进行从左往右一层一层的包裹
    val writer = new BasicWriter with UpperCaseWriter with AddTimeStampWriter with AddLineNumberWriter

    writer.write("hello")
    writer.write("world")
  }
}
