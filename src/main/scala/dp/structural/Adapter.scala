package dp.structural

trait Log {
  def info(message: String)

  def debug(message: String)

  def warning(message: String)

  def error(message: String)
}

object LogAdapter {

  //基于隐式类直接实现
  implicit class FileLoggerOps(fileLogger: FileLogger) {
    def info(message: String): Unit = fileLogger.log("info: file logger")

    def debug(message: String): Unit = fileLogger.log("debug: file logger")

    def warning(message: String): Unit = fileLogger.log("warning: file logger")

    def error(message: String): Unit = fileLogger.log("error: file logger")
  }

}

class AppLogger extends Log {
  override def info(message: String): Unit = log(message, "info")

  override def debug(message: String): Unit = log(message, "debug")

  override def warning(message: String): Unit = log(message, "warning")

  override def error(message: String): Unit = log(message, "error")

  def log(message: String, severity: String): Unit = {
    println(s"""${severity.toUpperCase}: $message""")
  }
}

class FileLogger {
  def log(message: String): Unit = println(s"""print to file: $message""")
}

//传统的适配器
class FileLoggerAdapter(fileLogger: FileLogger) extends Log {
  override def info(message: String): Unit = fileLogger.log("info: file logger")

  override def debug(message: String): Unit = fileLogger.log("debug: file logger")

  override def warning(message: String): Unit = fileLogger.log("warning: file logger")

  override def error(message: String): Unit = fileLogger.log("error: file logger")
}


object AdapterExample {
  def main(args: Array[String]): Unit = {
    val logger = new AppLogger
    logger.info("this is an info message.")
    logger.debug("this is an debug message.")
    logger.warning("this is an warning message.")
    logger.error("this is an error message.")

    val logger2 = new FileLoggerAdapter(new FileLogger)
    logger2.info("this is an info message.")
    logger2.debug("this is an debug message.")
    logger2.warning("this is an warning message.")
    logger2.error("this is an error message.")

    import LogAdapter._
    val logger3 = new FileLogger
    logger3.info("this is an info message.")
    logger3.debug("this is an debug message.")
    logger3.warning("this is an warning message.")
    logger3.error("this is an error message.")
  }
}