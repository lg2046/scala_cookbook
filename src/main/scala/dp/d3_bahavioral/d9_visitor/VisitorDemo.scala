package dp.d3_bahavioral.d9_visitor

//被访问者
trait ReportVisitable {
  //决定传给被访问者什么类
  def accept(reportVisitor: ReportVisitor): Unit
}

class FixedPriceContract(val costPerYear: Long) extends ReportVisitable {
  override def accept(reportVisitor: ReportVisitor): Unit = reportVisitor.visit(this)
}

class TimeAndMaterialsContract(val costPerHour: Long, val hours: Long) extends ReportVisitable {
  override def accept(reportVisitor: ReportVisitor): Unit = reportVisitor.visit(this)
}

class SupportContract(val costPerMonth: Long) extends ReportVisitable {
  override def accept(reportVisitor: ReportVisitor): Unit = reportVisitor.visit(this)
}


//访问者
trait ReportVisitor {
  //能访问到的每个对象 以及所做的工作
  def visit(contract: FixedPriceContract)

  def visit(contract: TimeAndMaterialsContract)

  def visit(contract: SupportContract)
}

class MonthlyCostReportVisitor(var monthlyCost: Long = 0) extends ReportVisitor {

  override def visit(contract: FixedPriceContract): Unit = monthlyCost += contract.costPerYear / 12

  override def visit(contract: TimeAndMaterialsContract): Unit = monthlyCost += contract.costPerHour * contract.hours

  override def visit(contract: SupportContract): Unit = monthlyCost += contract.costPerMonth
}

class YearlyReportVisitor(var yearlyCost: Long = 0) extends ReportVisitor {

  override def visit(contract: FixedPriceContract): Unit = yearlyCost += contract.costPerYear

  override def visit(contract: TimeAndMaterialsContract): Unit = yearlyCost += contract.costPerHour * contract.hours

  override def visit(contract: SupportContract): Unit = yearlyCost += contract.costPerMonth * 12
}

object VisitorDemo2 {
  def main(args: Array[String]): Unit = {
    val projectAlpha = new FixedPriceContract(costPerYear = 10000)
    val projectBeta = new SupportContract(costPerMonth = 500)
    val projectGamma = new TimeAndMaterialsContract(hours = 150, costPerHour = 10)
    val projectKappa = new TimeAndMaterialsContract(hours = 50, costPerHour = 50)

    val projects = List(projectAlpha, projectBeta, projectGamma, projectKappa)

    val monthlyCostReportVisitor = new MonthlyCostReportVisitor()
    projects.foreach(_.accept(monthlyCostReportVisitor))
    println(s"Monthly cost: ${monthlyCostReportVisitor.monthlyCost}")

    val yearlyReportVisitor = new YearlyReportVisitor()
    projects.foreach(_.accept(yearlyReportVisitor))
    println(s"Yearly cost: ${yearlyReportVisitor.yearlyCost}")

  }
}