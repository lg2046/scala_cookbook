package CookBook.c02_number

import java.text.NumberFormat
import java.util.{Currency, Locale}

import scala.util.Try
import active_support._

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration._
import scala.async.Async.{async, await}
import scala.concurrent.{Await, Future}

object Demo1 {
  def main(args: Array[String]): Unit = {
    println(Try("google".toDouble).toOption)

    Integer.parseInt("100", 2)

    println("123".toInt(8))

    println(123: Long)

    val a = 0.1
    val b = 0.2
    val c = 0.3


    require(a + b ~= c)

    val r = scala.util.Random

    r.nextInt()


    val af = 1.2345
    println(af.formatted("%06.2f"))

    println(f"$af%06.2f")

    val formatter = NumberFormat.getCurrencyInstance
    formatter.setCurrency(Currency.getInstance(new Locale("cn", "CN")))
    println(formatter.format(123456.789))


    val result2 = async {
      val fs = (1 to 10).map { i =>
        Future {
          Thread.sleep(1000)
          i + 10
        }
      }

      val result = await(Future.sequence(fs))
      println(result)
    }


    Await.ready(result2, 10 seconds)
  }
}
