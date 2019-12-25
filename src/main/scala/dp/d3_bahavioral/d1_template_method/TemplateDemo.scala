package dp.d3_bahavioral.d1_template_method

import java.io.InputStream
import java.nio.charset.StandardCharsets

import org.json4s.jackson.JsonMethods.{parse => json_parse}
import org.json4s.{DefaultFormats, _}

case class Person(name: String, age: Int, address: String)

abstract class DataFinder[T, Y] {
  def find(f: T => Option[Y]): Option[Y] = {
    try {
      val data: Array[Byte] = readData()
      val parsed: T = parse(data)
      f(parsed)
    } finally {
      cleanup()
    }
  }

  def readData(): Array[Byte]

  def parse(data: Array[Byte]): T

  def cleanup()
}


class JsonDataFinder extends DataFinder[List[Person], Person] {
  implicit val jsonFormats: DefaultFormats.type = DefaultFormats

  override def readData(): Array[Byte] = {
    val stream: InputStream = this.getClass.getClassLoader.getResourceAsStream("people.json")
    Stream.continually(stream.read).takeWhile(_ != -1).map(_.toByte).toArray
  }

  override def parse(data: Array[Byte]): List[Person] = {
    val data_str = new String(data, StandardCharsets.UTF_8)
    json_parse(data_str).extract[List[Person]]
  }

  override def cleanup(): Unit =
    println("Reading json: nothing to do.")
}


object TemplateDemo {
  def main(args: Array[String]): Unit = {
    val jsonDataFinder = new JsonDataFinder
    jsonDataFinder.find(_.find(_.name == "Ivan")).foreach(println)
  }
}
