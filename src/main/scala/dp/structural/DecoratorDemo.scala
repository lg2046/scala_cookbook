package dp.structural

import java.security.MessageDigest

import org.apache.commons.codec.binary.Hex

//所有可叠加功能的基类
trait InputWriter {
  def write(str: String): Unit
}

trait AddLineNumberWriter extends InputWriter {
  private var AddLineNumberWriterLine = 1

  abstract override def write(str: String): Unit = {
    super.write(s"""$AddLineNumberWriterLine: $str""")
    AddLineNumberWriterLine += 1
  }
}

trait AddTimeStampWriter extends InputWriter {
  abstract override def write(str: String): Unit = {
    super.write(s"""${System.currentTimeMillis()}: $str""")
  }
}

trait UpperCaseWriter extends InputWriter {
  abstract override def write(str: String): Unit = super.write(str.toUpperCase)
}

class BasicWriter extends InputWriter {
  override def write(str: String): Unit = println(str)
}

object DecoratorDemo {
  def main(args: Array[String]): Unit = {
    //传递的参数会从右往左进行应用，走到BasicWriter的write, 相当于对BasicWriter进行从左往右一层一层的包裹
    val writer = new BasicWriter with UpperCaseWriter with AddTimeStampWriter with AddLineNumberWriter

    writer.write("hello")

    val crypt = MessageDigest.getInstance("MD5")
    //Hex.encodeHex(crypt, "google")
    crypt.reset()
    crypt.update("hello world".getBytes())
    println(Hex.encodeHexString(crypt.digest()))

    println(crypt.digest())
    println(Hex.encodeHexString(crypt.digest("hello world".getBytes())))
  }
}
