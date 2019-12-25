package akka_demo.inaction.akkastream

import java.time.ZonedDateTime

import play.api.libs.json.{Format, JsError, JsString, JsSuccess, Json}
import library_demo.JsonReadWrite._

case class Event(host: String,
                 service: String,
                 state: State,
                 time: ZonedDateTime,
                 description: String,
                 tag: Option[String] = None,
                 metric: Option[Double] = None)


object Event {
  implicit val eventErrorFormat: Format[Event] = Json.format[Event]
}


sealed trait State

case object Critical extends State

case object Error extends State

case object Ok extends State

case object Warning extends State


object State {
  def norm(str: String): String = str.toLowerCase

  def norm(state: State): String = norm(state.toString)


  val ok = norm(Ok)
  val warning = norm(Warning)
  val error = norm(Error)
  val critical = norm(Critical)

  def unapply(str: String): Option[State] = {
    val normalized = norm(str)

    if (normalized == norm(Ok)) Some(Ok)
    else if (normalized == norm(Warning)) Some(Warning)
    else if (normalized == norm(Error)) Some(Error)
    else if (normalized == norm(Critical)) Some(Critical)
    else None
  }

  implicit val stageFormat: Format[State] =
    Format(
      {
        case JsString("ok") => JsSuccess(Ok)
        case JsString("warning") => JsSuccess(Warning)
        case JsString("error") => JsSuccess(Error)
        case JsString("critical") => JsSuccess(Critical)
        case js => JsError(s"Could not deserialize $js to State.")
      },

      (o: State) =>
        JsString(State.norm(o))
    )
}

case class LogReceipt(logId: String, written: Long)

object LogReceipt {
  implicit val logReceiptFormat: Format[LogReceipt] = Json.format[LogReceipt]
}

case class ParseError(logId: String, msg: String)

object ParseError {
  implicit val parseErrorFormat: Format[ParseError] = Json.format[ParseError]
}

object JsonDemo {
  def main(args: Array[String]): Unit = {
    val js = Json.toJson(Event(
      "my-host-1",
      "web-app",
      Ok,
      ZonedDateTime.parse("2015-08-12T12:12:00.127Z"),
      "5 tickets sold",
      None,
      None
    ))

    println(Json.prettyPrint(js))
    println(js.as[Event])
    println(Json.parse(js.toString()))
  }
}