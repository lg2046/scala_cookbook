package dp.bahavioral

object VisitorDemo {

  case class Person(firstName: String, lastName: String)

  object Person {

    implicit class PersonOps(person: Person) {
      def fullName: String = person.firstName + person.lastName
    }

  }


  def main(args: Array[String]): Unit = {
    val person = Person("andy", "fly")
    println(person.fullName)
  }
}


//被访问者
trait ReportVisitable

class FixedPriceContract(val costPerYear: Long) extends ReportVisitable

class TimeAndMaterialsContract(val costPerHour: Long, val hours: Long) extends ReportVisitable

class SupportContract(val costPerMonth: Long) extends ReportVisitable

trait ReportVisitor {
  def visit(contract: ReportVisitable)
}

//访问者
class MonthlyCostReportVisitor(var monthlyCost: Long = 0) extends ReportVisitor {
  override def visit(contract: ReportVisitable) {
    contract match {
      case obj: FixedPriceContract => monthlyCost += obj.costPerYear / 12
      case obj: TimeAndMaterialsContract => monthlyCost += obj.costPerHour * obj.hours
      case obj: SupportContract => monthlyCost += obj.costPerMonth
    }
  }
}

class YearlyReportVisitor(var yearlyCost: Long = 0) extends ReportVisitor {
  override def visit(contract: ReportVisitable) {
    contract match {
      case obj: FixedPriceContract => yearlyCost += obj.costPerYear
      case obj: TimeAndMaterialsContract => yearlyCost += obj.costPerHour * obj.hours
      case obj: SupportContract => yearlyCost += obj.costPerMonth * 12
    }
  }
}

object VisitorDemo2 {
  def main(args: Array[String]): Unit = {
    val projectAlpha = new FixedPriceContract(costPerYear = 10000)
    val projectBeta = new SupportContract(costPerMonth = 500)
    val projectGamma = new TimeAndMaterialsContract(hours = 150, costPerHour = 10)
    val projectKappa = new TimeAndMaterialsContract(hours = 50, costPerHour = 50)

    val projects = List(projectAlpha, projectBeta, projectGamma, projectKappa)

    val monthlyCostReportVisitor = new MonthlyCostReportVisitor()
    projects.foreach(monthlyCostReportVisitor.visit)
    println(s"Monthly cost: ${monthlyCostReportVisitor.monthlyCost}")

    val yearlyReportVisitor = new YearlyReportVisitor()
    projects.foreach(yearlyReportVisitor.visit)
    println(s"Yearly cost: ${yearlyReportVisitor.yearlyCost}")

    val f1: (Int, Int) => Int = (a, b) => a + b

    val f2 = f1.tupled
    f2.apply((1, 2))

    "hello world".split(Array('l', 'r'))

    println(for (c <- "abc" if c != 'b') yield c.toUpper)
    println("abc".map(_.toUpper))
    println("abc".filter(_ != 'b').map(_.toUpper))

    "google".foreach { c => println(c) }

    "google".apply(1)
  }
}