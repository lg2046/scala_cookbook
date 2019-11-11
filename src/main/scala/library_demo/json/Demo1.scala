//package library_demo.c13_Json
//
//import java.util
//import scala.collection.JavaConverters._
//import com.alibaba.fastjson.{JSON, JSONObject, TypeReference}
//import active_support._
//import beans.Student
//
//object Demo1 {
//  def main(args: Array[String]): Unit = {
//    //json 简单对象
//    val JSON_OBJ_STR: String = "{\"studentName\":\"lily\",\"studentAge\":12}"
//
//    //json字符串-数组类型
//    val JSON_ARRAY_STR: String =
//      "[{\"studentName\":\"lily\",\"studentAge\":12},{\"studentName\":\"lucy\",\"studentAge\":15}]"
//
//    //复杂格式json字符串
//    val COMPLEX_JSON_STR: String =
//      "{\"teacherName\":\"crystall\",\"teacherAge\":27,\"course\":{\"courseName\":\"english\",\"code\":1270},\"students\":[{\"studentName\":\"lily\",\"studentAge\":12},{\"studentName\":\"lucy\",\"studentAge\":15}]}"
//
//
//    //1: json字符串与json对象之间的转换
//    val obj = JSON.parseObject(JSON_OBJ_STR)
//    obj.getString("studentName") tap println
//    obj.getInteger("studentAge") tap println
//    println("--object prop iterator")
//    obj.forEach {
//      case (k, v) => println(k, v)
//    }
//
//    //2: json字符串与json数组之间的转换
//    val jsonArray = JSON.parseArray(JSON_ARRAY_STR)
//
//    //遍历方式一
//    println("--array iterator")
//    jsonArray.forEach { obj =>
//      obj.asInstanceOf[JSONObject].getString("studentName") tap println
//      obj.asInstanceOf[JSONObject].getInteger("studentAge") tap println
//    }
//
//    //遍历方式二
//    println("--array iterator 2")
//    val size = jsonArray.size()
//    (0 until size).foreach { i =>
//      val obj = jsonArray.getJSONObject(i)
//      println(obj.getString("studentName"))
//    }
//
//
//    //3: 复杂json字符串与jsonobject的转换
//    val jsonObject = JSON.parseObject(COMPLEX_JSON_STR)
//
//    println("--completex json ")
//    jsonObject.getString("teacherName") tap println
//    jsonObject.getInteger("teacherAge") tap println
//    jsonObject.getJSONObject("course") tap println
//    jsonObject.getJSONArray("students") tap println
//
//    //4： 与对象之间的映射
//    val student = JSON.parseObject(JSON_OBJ_STR, new TypeReference[Student]() {})
//    println(student)
//
//    //与数组之间的映射
//    println("--arr obj map")
//    val students = JSON.parseObject(JSON_ARRAY_STR, new TypeReference[util.ArrayList[Student]]() {}).asScala
//    students foreach println
//  }
//}
//
////
////class Student {
////  var studentName: String = _
////  var studentAge: Int = _
////
////  override def toString: String = s"$studentName:$studentAge"
////}
