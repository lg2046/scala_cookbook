package dp.d1_creational.d5_singlton

//当引用变量时 会初始化
object LazyInit {
  val basicPi = 3.14
  println(this)
  //使用sync双重检查 线程安全
  //引用该变量时才会触发lazy的代码体运行
  lazy val precisePi: Double = {
    println("init pi")
    3.1415926
  }
}

object Demo2 {
  def main(args: Array[String]): Unit = {
    LazyInit.precisePi
  }
}
