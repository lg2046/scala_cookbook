package pragmatic_scala.chap1

import java.util.concurrent.Executors

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration._
import scala.concurrent.{Await, ExecutionContext, ExecutionContextExecutor, Future}

case class Record(year: Int, month: Int, date: Int, closePrice: Double)


object D1_TopStock {
  val myHttpExecutor: ExecutionContextExecutor =
    ExecutionContext.fromExecutor(Executors.newFixedThreadPool(10))

  def main(args: Array[String]): Unit = {
    val symbols = List("AMD", "AAPL", "AMZN", "IBM", "ORCL", "MSFT")
    val year = 2017

    val futures = Future.sequence(
      symbols.map(symbol => Future {
        (symbol, getYearEndClosingPrice(symbol, year))
      }(myHttpExecutor))
    )

    val (stock, price) = Await.result(futures, Duration.Inf).maxBy(_._2)
    println(s"The max stock in 2017 is $stock with price $$$price")

    println(findMax(List(1, 2, 3, 6, 5, 3): _*))
  }

  //取一年中最大那一天的股价
  def getYearEndClosingPrice(symbol: String, year: Int): Double = {
    val url = s"https://raw.githubusercontent.com/ReactivePlatform/" +
      s"Pragmatic-Scala-StaticResources/master/src/main/resources/" +
      s"stocks/daily/daily_$symbol.csv"

    println(url)

    val data = io.Source.fromURL(url).mkString

    data.split("\n")
      .filter(record => record.startsWith(s"$year-12"))
      .map(record => {
        val Array(timestamp, open, high, low, close, volume) = record.split(",")
        val Array(year, month, date) = timestamp.split("-")
        Record(year.toInt, month.toInt, date.toInt, close.trim.toDouble)
      })
      .maxBy(r => f"${r.month}%02d${r.date}%02d")
      .closePrice
  }

  def findMax(temperatures: Int*): Int = {
    temperatures.foldLeft(Integer.MIN_VALUE)(Math.max)
  }
}
