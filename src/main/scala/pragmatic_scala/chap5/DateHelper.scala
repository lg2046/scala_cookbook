package pragmatic_scala.chap5

import org.joda.time.DateTime

object DateHelper {
  val ago = "ago"
  val after = "after"

  implicit class IntegerDateHelper(i: Int) {
    def days(t: String): DateTime = {
      val base = new DateTime()
      t match {
        case "ago" => base.minusDays(i)
        case "after" => base.plusDays(i)
      }
    }
  }


  implicit class Interpolator(val context: StringContext) extends AnyVal {
    def mask(args: Any*): StringBuilder = {
      println(context.parts.zip(args))
      val processed = context.parts.zip(args).map { item =>
        val (text, expression) = item
        if (text.endsWith("^"))
          s"${text.split('^')(0)}$expression" else
          s"$text...${expression.toString takeRight 4}"
      }.mkString
      new StringBuilder(processed).append(context.parts.last)
    }
  }

}


object Demo {

  import DateHelper._

  def main(args: Array[String]): Unit = {
    println(3 days ago)
    println(3 days after)

    val ssn = "123-45-6789"
    val account = "0246781263"
    val balance = 20145.23
    println(
      mask"""Account: $account
            |Social Security Number: $ssn
            |Balance: $$^$balance
            |Thanks for your business.""".stripMargin)

  }

}




