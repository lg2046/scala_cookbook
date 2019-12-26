package akka_demo.inaction.persistent_actor

import akka.actor.{ActorLogging, Props}
import akka.persistence.{PersistentActor, Recovery, RecoveryCompleted, SnapshotMetadata, SnapshotOffer}
import akka_demo.inaction.persistent_actor.Calculator._
import play.api.libs.json._


class Calculator extends PersistentActor with ActorLogging {
  var state = CalculationResult()

  //恢复后用于处理消息 //不是receive
  override def receiveCommand: Receive = {
    //persist两个参数: 一个是Event 第二个是保存后调用的回调函数
    //persist是异步的 存储成功后调用后面的回调函数
    //但persist保证了在handler 完成前不会处理新command
    //所以可以使用sender() ! 会stash message

    //如果不需要stash message  可以使用persistAsync
    case Add(value) => persist(Added(value))(updateState)
    case Subtract(value) => persist(Subtracted(value))(updateState)
    case Divide(value) => if (value != 0) persist(Divided(value))(updateState)
    case Multiply(value) => persist(Multiplied(value))(updateState)
    case PrintResult => println(s"the result is: ${state.result}")
    case GetResult => sender() ! state.result
    case Clear => persist(Reset) { e =>
      updateState(e)
      saveSnapshot(Calculator.Snapshot(state))
    }
    case "fail" => throw new RuntimeException("Test")
  }

  val updateState: Event => Unit = {
    case Reset => state = state.reset
    case Added(value) => state = state.add(value)
    case Subtracted(value) => state = state.subtract(value)
    case Divided(value) => state = state.divide(value)
    case Multiplied(value) => state = state.multiply(value)
  }

  //started和restarted时使用 接到past event与snapshot
  override def receiveRecover: Receive = {
    case event: Event =>
      println(s"recovery event: $event")
      updateState(event)

    case SnapshotOffer(metadata: SnapshotMetadata, snapshot: Calculator.Snapshot) =>
      println(s"recovering calc from snapshot $metadata")
      state = snapshot.result

    //当恢复完毕 会收到该消息 处理完后才开始处理command
    case RecoveryCompleted => log.info("Calculator recovery completed")
  }

  override def persistenceId: String = Calculator.name
}


object Calculator {

  def props = Props(new Calculator)

  val name = "my_calculator"

  //actor可执行的命令
  sealed trait Command

  case object Clear extends Command

  case class Add(value: Double) extends Command

  case class Subtract(value: Double) extends Command

  case class Divide(value: Double) extends Command

  case class Multiply(value: Double) extends Command

  case object PrintResult extends Command

  case object GetResult extends Command

  //Event
  sealed trait Event

  case object Reset extends Event

  case class Added(value: Double) extends Event

  case class Subtracted(value: Double) extends Event

  case class Divided(value: Double) extends Event

  case class Multiplied(value: Double) extends Event


  case class CalculationResult(result: Double = 0) {
    def reset = copy(result = 0)

    def add(value: Double) = copy(result = this.result + value)

    def subtract(value: Double) = copy(result = this.result - value)

    def divide(value: Double) = copy(result = this.result / value)

    def multiply(value: Double) = copy(result = this.result * value)
  }

  object CalculationResult {
    implicit val calculationResultFormat: Format[CalculationResult] = Json.format[CalculationResult]
  }

  case class Snapshot(result: CalculationResult)

  object Snapshot {
    implicit val snapshotFormat: Format[Snapshot] = Json.format[Snapshot]
  }


  object Event {
    val addedId = JsNumber(1)
    val subtractedId = JsNumber(2)
    val dividedId = JsNumber(3)
    val multipliedId = JsNumber(4)
    val resetId = JsNumber(5)


    implicit val eventFormat: Format[Event] = Format(
      {
        case JsArray(Seq(`addedId`, value)) => JsSuccess(Added(value.as[Double]))
        case JsArray(Seq(`subtractedId`, value)) => JsSuccess(Subtracted(value.as[Double]))
        case JsArray(Seq(`dividedId`, value)) => JsSuccess(Divided(value.as[Double]))
        case JsArray(Seq(`multipliedId`, value)) => JsSuccess(Multiplied(value.as[Double]))
        case JsArray(Seq(`resetId`, _)) => JsSuccess(Reset)
      },

      {
        case e: Added => Json.arr(addedId, e.value)
        case e: Subtracted => Json.arr(subtractedId, e.value)
        case e: Divided => Json.arr(dividedId, e.value)
        case e: Multiplied => Json.arr(multipliedId, e.value)
        case Reset => Json.arr(resetId, JsNull)
      }
    )
  }


}