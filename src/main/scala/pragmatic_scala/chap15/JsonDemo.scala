package pragmatic_scala.chap15

import play.api.libs.json.{JsValue, Json}

object JsonDemo {
  def main(args: Array[String]): Unit = {
    val json: JsValue = Json.parse(
      """
{
  "name" : "Watership Down",
  "location" : {
    "lat" : 51.235685,
    "long" : -1.309197
  },
  "residents" : [ {
    "name" : "Fiver",
    "age" : 4,
    "role" : null
  }, {
    "name" : "Bigwig",
    "age" : 6,
    "role" : "Owsla"
  } ]
}
""")
    println((json \ "location" \ "lat").get)
  }
}
