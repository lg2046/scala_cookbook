package dp.d3_bahavioral.d6_command

import scala.collection.mutable.ArrayBuffer


//Receiver
case class Robot(name: String) {
  def cleanup(): Unit = println("Cleaning up")

  def pourJuice(): Unit = println("Pouring juice")

  def makeSandwich(): Unit = println("Making a sandwich")
}

//Command
trait RobotCommand {
  def execute(): Unit
}

case class CleanupCommand(robot: Robot) extends RobotCommand {
  override def execute(): Unit = robot.cleanup()
}

case class PourJuiceCommand(robot: Robot) extends RobotCommand {
  override def execute(): Unit = robot.pourJuice()
}

case class MakeSandwichCommand(robot: Robot) extends RobotCommand {
  override def execute(): Unit = robot.makeSandwich()
}

//Invoker: 向Invoker发布命令
class RobotController {
  private val history = ArrayBuffer[RobotCommand]()

  def issueCommand(command: RobotCommand): Unit = {
    history += command
  }

  def showHistory(): Unit = {
    history foreach println
  }

  def run(): Unit = {
    history.foreach(_.execute())
  }
}

object Demo {
  def main(args: Array[String]): Unit = {
    val robotController = new RobotController
    val robot = Robot("ev3")
    robotController.issueCommand(MakeSandwichCommand(robot))
    robotController.issueCommand(PourJuiceCommand(robot))
    robotController.issueCommand(CleanupCommand(robot))

    println("Here is what i asked my robot to do")
    robotController.showHistory()

    robotController.run()
  }
}

//不进行命令类封装 直接使用一个函数封装
class RobotByNameController {
  private val history = ArrayBuffer[() => Unit]()

  def issueCommand(command: () => Unit): Unit = {
    command +=: history
  }

  def executeAll(): Unit = {
    history.foreach(f => f())
  }

  def showHistory(): Unit = {
    history.foreach(println)
  }

  def run(): Unit = {
    history.foreach(_.apply())
  }
}

object Demo2 {
  def main(args: Array[String]): Unit = {
    val robotController = new RobotByNameController
    val robot = Robot("ev3")
    robotController.issueCommand { () => MakeSandwichCommand(robot).execute() }
    robotController.issueCommand { () => PourJuiceCommand(robot).execute() }
    robotController.issueCommand { () => CleanupCommand(robot).execute() }

    println("Here is what i asked my robot to do")
    robotController.showHistory()

    robotController.run()
  }
}