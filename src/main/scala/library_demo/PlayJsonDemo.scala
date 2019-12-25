package library_demo

import play.api.libs.json._
import play.api.libs.functional.syntax._

object PlayJsonDemo {
  val jsonStr =
    """
      |{
      |  "name" : "Watership Down",
      |  "location" : {
      |    "lat" : 51.235685,
      |    "long" : -1.309197
      |  },
      |  "residents" : [ {
      |    "name" : "Fiver",
      |    "age" : 4,
      |    "role" : null
      |  }, {
      |    "name" : "Bigwig",
      |    "age" : 6,
      |    "role" : "Owsla"
      |  } ]
      |}
      |""".stripMargin

  def main(args: Array[String]): Unit = {
    //    val json = Json.parse(jsonStr)
    //
    //    println(json)

    val json2: JsValue = Json.obj(
      "name" -> "Watership Down",
      "location" -> Json.obj("lat" -> 51.235685, "long" -> -1.309197),
      "residents" -> Json.arr(
        Json.obj(
          "name" -> "Fiver",
          "age" -> 4,
          "role" -> JsNull
        ),
        Json.obj(
          "name" -> "Bigwig",
          "age" -> 6,
          "role" -> "Owsla"
        )
      )
    )

    println(Json.toJson(List(1, 2, 3, 4)))

    val place = Place(
      "Watership Down",
      Location(51.235685, -1.309197),
      Seq(
        Resident("Fiver", 4, None),
        Resident("Bigwig", 6, Some("Owsla"))
      )
    )

    //    implicit val LocationWrites = new Writes[Location] {
    //      override def writes(o: Location): JsValue = Json.obj(
    //        "lat" -> o.lat,
    //        "long" -> o.long,
    //      )
    //    }

    //    implicit val locationWrites: Writes[Location] = (
    //      (JsPath \ "lat").write[Double] and
    //        (JsPath \ "long").write[Double]
    //      ) (unlift(Location.unapply))

    implicit val ResidentWrites = new Writes[Resident] {
      override def writes(o: Resident): JsValue = Json.obj(
        "name" -> o.name,
        "age" -> o.age,
        "role" -> o.role,
      )
    }

    implicit val PlaceWrites = new Writes[Place] {
      override def writes(o: Place): JsValue = Json.obj(
        "name" -> place.name,
        "location" -> place.location,
        "residents" -> place.residents,
      )
    }

    val json = Json.toJson(place)

    // 导航可以使用字符串或者是索引 分别用于对象与数组
    println((json \ "location" \ "lat").get) // 返回JsValue
    println((json \ "residents" \ 1).get) // getOrElse

    println(json \\ "name") // Seq[JsValue]

    println(json("residents")(1))


    println(Json.arr("a", "b", "c").validate[Seq[String]].get)


    import play.api.libs.json._
    import play.api.libs.functional.syntax._

    //    implicit val locationReads = new Reads[Location] {
    //      override def reads(json: JsValue): JsResult[Location] =
    //        JsSuccess(
    //          Location(
    //            (json \ "lat").as[Double],
    //            (json \ "long").as[Double]
    //          ))
    //    }

    //    implicit val locationReads: Reads[Location] = (
    //      (JsPath \ "lat").read[Double] and
    //        (JsPath \ "long").read[Double]
    //      ) (Location.apply _)

    implicit val residentReads: Reads[Resident] = (
      (JsPath \ "name").read[String] and
        (JsPath \ "age").read[Int] and
        (JsPath \ "role").readNullable[String]
      ) (Resident.apply _)

    implicit val placeReads: Reads[Place] = (
      (JsPath \ "name").read[String] and
        (JsPath \ "location").read[Location] and
        (JsPath \ "residents").read[Seq[Resident]]
      ) (Place.apply _)

    json.as[Place]

    val placeResult: JsResult[Place] = json.validate[Place]
    println(placeResult) // JsSuccess(Place(...

    val residentResult: JsResult[Resident] = (json \ "residents") (1).validate[Resident]
    println(residentResult) // JsSuccess(Resident(Bigwig,6,Some(Owsla)),)


    println((__ \ "abc") (1).getClass)


    (JsPath \ "name").read[String](Reads.minLength[String](2))


    println(Json.fromJson[Place](json))
    println(json.validate[Place])

    val jsa = Json.arr(
      Json.obj(
        "name" -> "Fiver",
        "age" -> 4,
        "role" -> JsNull
      ),
      Json.obj(
        "name" -> "Bigwig",
        "age" -> 6,
        "role" -> "Owsla"
      )
    )

    println(jsa.value.size)
  }
}


case class Location(lat: Double, long: Double)

object Location {

  implicit val locationFormat: Format[Location] = Json.format[Location]

  //  implicit val locationFormat: Format[Location] = (
  //    (JsPath \ "lat").format[Double] and
  //      (JsPath \ "long").format[Double]
  //    ) (Location.apply, unlift(Location.unapply))
}

case class Resident(name: String, age: Int, role: Option[String])

case class Place(name: String, location: Location, residents: Seq[Resident])