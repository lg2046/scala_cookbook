package library_demo

import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

import play.api.libs.json.{Format, JsNumber, JsString, JsSuccess, JsValue}

object JsonReadWrite {
  implicit val zonedDateTimeFormat: Format[ZonedDateTime] =
    Format(
      (json: JsValue) =>
        JsSuccess(ZonedDateTime.parse(json.as[String])),

      (o: ZonedDateTime) =>
        JsString(o.format(DateTimeFormatter.ISO_INSTANT))
    )
}
