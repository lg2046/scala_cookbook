import java.security.MessageDigest

import org.apache.commons.codec.binary.Hex

import scala.collection.mutable
//package pragmatic_scala.chap15
//import scala.collection.JavaConverters._
//import scala.concurrent.ExecutionContext.Implicits.global
//import scala.concurrent.duration.Duration
//import scala.concurrent.{Await, Future}
//import scala.util.Try
//
//object Demo1 {
//  def main(args: Array[String]): Unit = {
//    //1: 读用户输入
//    //val line = StdIn.readLine()
//    //println(line)
//
//    //    val xmlFragment =
//    //      <symbols>
//    //        <symbol ticker="AAPL">
//    //          <units>200</units>
//    //        </symbol>
//    //        <symbol ticker="IBM">
//    //          <units>215</units>
//    //        </symbol>
//    //      </symbols>
//    //
//    //    println(xmlFragment.getClass)
//    //    //直接子元素
//    //    println(xmlFragment \ "symbol")
//    //    //所有元素
//    //    val unitsNodes = xmlFragment \\ "units"
//
//    //    unitsNodes.head match {
//    //      case <units>
//    //        {numberOfUnits}
//    //        </units> => println(s"Units: $numberOfUnits")
//    //    }
//    val props = System.getProperties
//    props.keySet().asScala.foreach { k =>
//      println(k, props.getProperty(k.toString))
//    }
//
//    Future {
//      println(Thread.currentThread().getName)
//    }
//
//    Thread.sleep(10)
//  }
//}

object Hasher {
  def md5(input: String) = {
    new String(
      Hex.encodeHex(MessageDigest.getInstance("MD5").digest(input.getBytes)))
  }

  def memo[A, B](f: A => B): (A => B) = {
    val cache = mutable.Map[A, B]()
    (x: A) =>
      cache.getOrElseUpdate(x, f(x))
  }


  def main(args: Array[String]): Unit = {
    val md52 = memo(Hasher.md5)
    val start = System.currentTimeMillis()
    (1 to 20000000).foreach { _ =>
      md52("google")
    }

    println(System.currentTimeMillis() - start)

  }
}


