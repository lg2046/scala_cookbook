package dp.trait_and_mixin

// trait as interface
trait Alarm {
  def trigger(): String

  def name: String //
}

class A extends Alarm {
  override def trigger(): String = "Trigger"

  val name = "google"

  def clear(): Unit = println("ha ha")
}

trait Notifier {
  def notificationMessage: String

  def printNotification(): Unit = {
    println(notificationMessage)
  }

  def clear(): Unit
}

class NotifierImpl(val notificationMessage: String) extends Notifier {
  val name = "abc"

  override def clear(): Unit = println("cleared")
}

object Traits {
  def main(args: Array[String]): Unit = {
    val a: Alarm = new A
    a.trigger()
    println(a.name)

    val notifier = new NotifierImpl("hello")
    notifier.printNotification()
    notifier.clear()

    val b = new A() with Notifier {
      override def notificationMessage: String = "google"

      override def clear(): Unit = {
        println("Cleared")
        super[A].clear()
      }
    }
    println("b:")
    println(b.isInstanceOf[Notifier])
    b.clear()
  }
}


