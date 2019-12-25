package pragmatic_scala.chap6

import java.io.BufferedReader
import better.files._
import scala.collection.JavaConverters._

trait InputReader {
  def readLines(): Stream[String]
}

class AdvancedInputReader(reader: BufferedReader) extends InputReader {
  override def readLines(): Stream[String] = reader.lines().iterator.asScala.toStream
}

trait CapitalizedInputReader extends InputReader {
  abstract override def readLines(): Stream[String] = super.readLines().map(_.toUpperCase)
}

trait AddLineNumberInputReader extends InputReader {
  var line = 0

  abstract override def readLines(): Stream[String] = super.readLines().map(l => s"""${line += 1; line}: $l""")
}

trait AddLineLengthInputReader extends InputReader {
  abstract override def readLines(): Stream[String] = super.readLines().map(l => s"""(size: ${l.length})$l""")
}

object One {
  def main(args: Array[String]): Unit = {
    val reader = new AdvancedInputReader(File("./pom.xml").newBufferedReader)
      with CapitalizedInputReader
      with AddLineLengthInputReader
      with AddLineNumberInputReader

    reader.readLines().foreach(println)
  }
}
