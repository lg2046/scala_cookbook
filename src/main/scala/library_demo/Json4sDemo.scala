package library_demo

import active_support._
import org.json4s.JsonDSL._
import org.json4s.jackson.Serialization.{read, write}
import org.json4s.jackson.JsonMethods.{parse, render, compact, pretty}
import org.json4s.{DefaultFormats, _}

//1: 最简单方便的情况就是直接使用case class与json进行映射
//case class Student(studentName: String, studentAge: Int)

//2: 自定义Student
class Student(val studentName: String, val studentAge: Int) {
  def allName(): String = {
    studentName + "google"
  }

  override def toString: String = s"{ name:  $studentName , age: $studentAge }"
}

object Student {
  def apply(studentName: String, studentAge: Int): Student = new Student(studentName, studentAge)
}

class StudentSerializer extends CustomSerializer[Student](
  implicit formats =>
    (
      //接两个偏函数 一个将JValue => Domain  一个将Domain转成JValue
      {
        case jv: JValue =>
          val e = (jv \ "n").extract[String]
          val c = (jv \ "a").extract[Int]
          Student(e, c)
      }, {
      case student: Student =>
        ("n" -> student.studentName) ~
          ("a" -> student.studentAge) ~
          ("all" -> student.allName())
    }
    ))

object Json4sDemo {
  def main(args: Array[String]): Unit = {
    implicit val jsonFormats: DefaultFormats.type = DefaultFormats

    //json 简单对象
    val JSON_OBJ_STR: String = "{\"studentName\":\"lily\",\"studentAge\":12}"

    //json字符串-数组类型
    val JSON_ARRAY_STR: String =
      "[{\"studentName\":\"lily\",\"studentAge\":12},{\"studentName\":\"lucy\",\"studentAge\":15}]"

    //复杂格式json字符串
    val COMPLEX_JSON_STR: String =
      "{\"teacherName\":\"crystall\",\"teacherAge\":27,\"course\":{\"courseName\":\"english\",\"code\":1270},\"students\":[{\"studentName\":\"lily\",\"studentAge\":12},{\"studentName\":\"lucy\",\"studentAge\":15}]}"


    //1: json字符串与json对象之间的转换
    println("1: json字符串与json对象之间的转换")
    val obj = parse(JSON_OBJ_STR)
    (obj \ "studentName").extract[String] tap println
    (obj \ "studentAge").extract[Int] tap println
    println("--object prop iterator")

    //obj.values可视作Map[String, Any]
    obj.values tap println
    obj.values.asInstanceOf[Map[String, _]] tap println
    obj.extract[Map[String, _]] tap println


    //2: json字符串与json数组之间的转换
    println("2: json字符串与json数组之间的转换")
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
    println("3: 复杂json字符串与jsonobject的转换")
    val jsonComplexObj = parse(COMPLEX_JSON_STR)
    (jsonComplexObj \ "teacherName").extract[String] tap println
    (jsonComplexObj \ "teacherAge").extract[Int] tap println
    (jsonComplexObj \ "course") tap println
    (jsonComplexObj \ "students") tap println

    val student = read[Student](JSON_OBJ_STR)
    println("read to custom class: " + student.studentName)
    println("read to custom class: " + student.studentAge)
    println(parse(JSON_OBJ_STR).extract[Student])

    //与数组之间的映射
    println("--arr obj map")
    val students = parse(JSON_ARRAY_STR).extract[List[Student]]
    students foreach println

    val s = Student("google", 20)
    val jsonStr = write(s)
    jsonStr tap println

    println("------test null------")
    val s2 = Student(null, 20)
    println(write(s2)(DefaultFormats + new StudentSerializer))

    println(read[Student](jsonStr))

    println("use custom formats")
    useCustomFormat(s)
  }

  def useCustomFormat(student: Student): Unit = {
    implicit val formats1: Formats = DefaultFormats + new StudentSerializer
    val jsonStr = write(student)
    jsonStr tap println

    println(read[Student](jsonStr))
  }
}



