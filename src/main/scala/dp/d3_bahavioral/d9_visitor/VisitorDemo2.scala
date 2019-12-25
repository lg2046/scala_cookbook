//package dp.d3_bahavioral
//
////被访问者
//trait ReportVisitable {
//  //决定传给被访问者什么类
//  def accept(reportVisitor: ReportVisitor): Unit
//}
//
//class FixedPriceContract(val costPerYear: Long) extends ReportVisitable {
//  override def accept(reportVisitor: ReportVisitor): Unit = reportVisitor.visit(this)
//}
//
//class TimeAndMaterialsContract(val costPerHour: Long, val hours: Long) extends ReportVisitable {
//  override def accept(reportVisitor: ReportVisitor): Unit = reportVisitor.visit(this)
//}
//
//class SupportContract(val costPerMonth: Long) extends ReportVisitable {
//  //不让年报访问者访问到SupportContract
//  override def accept(reportVisitor: ReportVisitor): Unit = reportVisitor.visit(this)
//}
//
//
////访问者
//trait ReportVisitor {
//  def visit(contract: ReportVisitable)
//}
//
//class MonthlyCostReportVisitor(var monthlyCost: Long = 0) extends ReportVisitor {
//  override def visit(contract: ReportVisitable): Unit = contract match {
//    case contract: FixedPriceContract => monthlyCost += contract.costPerYear / 12
//    case contract: TimeAndMaterialsContract => monthlyCost += contract.costPerHour * contract.hours
//    case contract: SupportContract => monthlyCost += contract.costPerMonth
//    case _ =>
//  }
//
//}
//
//class YearlyReportVisitor(var yearlyCost: Long = 0) extends ReportVisitor {
//
//  override def visit(contract: ReportVisitable): Unit = contract match {
//    case contract: FixedPriceContract => yearlyCost += contract.costPerYear
//    case contract: TimeAndMaterialsContract => yearlyCost += contract.costPerHour * contract.hours
////    case contract: SupportContract => yearlyCost += contract.costPerMonth * 12
//    case _ =>
//  }
//}
//
//object VisitorDemo2 {
//  def main(args: Array[String]): Unit = {
//    val projectAlpha = new FixedPriceContract(costPerYear = 10000)
//    val projectBeta = new SupportContract(costPerMonth = 500)
//    val projectGamma = new TimeAndMaterialsContract(hours = 150, costPerHour = 10)
//    val projectKappa = new TimeAndMaterialsContract(hours = 50, costPerHour = 50)
//
//    val projects = List(projectAlpha, projectBeta, projectGamma, projectKappa)
//
//    val monthlyCostReportVisitor = new MonthlyCostReportVisitor()
//    projects.foreach(_.accept(monthlyCostReportVisitor))
//    println(s"Monthly cost: ${monthlyCostReportVisitor.monthlyCost}")
//
//    val yearlyReportVisitor = new YearlyReportVisitor()
//    projects.foreach(_.accept(yearlyReportVisitor))
//    println(s"Yearly cost: ${yearlyReportVisitor.yearlyCost}")
//
//  }
//}