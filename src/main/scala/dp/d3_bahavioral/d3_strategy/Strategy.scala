package dp.d3_bahavioral.d3_strategy

object StrategyFactory {
  def apply(filename: String): (String) => List[String] = {
    filename match {
      case f if f.endsWith(".json") => jsonMethod
      case f if f.endsWith(".csv") => csvMethod
      case f => throw new Exception(s"unknow format: $f")
    }
  }

  def jsonMethod(file: String): List[String] = {
    List("i", "am", "json")
  }

  def csvMethod(file: String): List[String] = {
    List("i", "am", "csv")
  }
}

object Strategy {
  def main(args: Array[String]): Unit = {

    val strategy = StrategyFactory("a.json")
    println(strategy)
  }
}