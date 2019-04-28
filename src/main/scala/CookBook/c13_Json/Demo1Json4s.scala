package CookBook.c13_Json

import org.json4s.DefaultFormats
import org.json4s._
import org.json4s.JsonDSL._
import org.json4s.native.JsonMethods.{parse, render, compact}
import org.json4s.native.Serialization.{read, write}
import active_support._

object Demo1Json4s {
  implicit val jsonFormats: DefaultFormats.type = DefaultFormats

  def main(args: Array[String]): Unit = {
    //json 简单对象
    val JSON_OBJ_STR: String = "{\"studentName\":\"lily\",\"studentAge\":12}"

    //json字符串-数组类型
    val JSON_ARRAY_STR: String =
      "[{\"studentName\":\"lily\",\"studentAge\":12},{\"studentName\":\"lucy\",\"studentAge\":15}]"

    //复杂格式json字符串
    val COMPLEX_JSON_STR: String =
      "{\"teacherName\":\"crystall\",\"teacherAge\":27,\"course\":{\"courseName\":\"english\",\"code\":1270},\"students\":[{\"studentName\":\"lily\",\"studentAge\":12},{\"studentName\":\"lucy\",\"studentAge\":15}]}"


    //1: json字符串与json对象之间的转换
    val obj = parse(JSON_OBJ_STR)
    (obj \ "studentName").extract[String] tap println
    (obj \ "studentAge").extract[Int] tap println
    println("--object prop iterator")

    //obj.values可视作Map[String, Any]
    obj.values tap println
    obj.values.asInstanceOf[Map[String, _]] tap println
    obj.extract[Map[String, _]] tap println


    //2: json字符串与json数组之间的转换
    val jsonArray = parse(JSON_ARRAY_STR).asInstanceOf[JArray]
    jsonArray.extract[List[Map[String, _]]].foreach(println)

    // for语法
    for {
      JArray(objList) <- jsonArray
      JObject(obj) <- objList
    } {
      // do something
      val kvList = for ((key, JString(value)) <- obj) yield (key, value)
      println("obj : " + kvList.mkString(","))
    }

    //3: 复杂json字符串与jsonobject的转换
    val jsonComplexObj = parse(COMPLEX_JSON_STR)
    (jsonComplexObj \ "teacherName").extract[String] tap println
    (jsonComplexObj \ "teacherAge").extract[Int] tap println
    (jsonComplexObj \ "course") tap println
    (jsonComplexObj \ "students") tap println

    val student = read[Student](JSON_OBJ_STR)
    println(student)
    println(parse(JSON_OBJ_STR).extract[Student])

    //与数组之间的映射
    println("--arr obj map")
    val students = parse(JSON_ARRAY_STR).extract[List[Student]]
    students foreach println

    val jsonStr = write(Student("google", 20))
    jsonStr tap println

    println(read[Student](jsonStr))
  }
}

case class Student(studentName: String, studentAge: Int)