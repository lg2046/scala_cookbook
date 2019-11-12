//package CookBook.c04_class_prop
//
//import org.json4s.JsonDSL._
//import org.json4s.{DefaultFormats, _}
//import scalaj.http.Http
//
//case class Address(stress: String)
//
//object Demo1 {
//  implicit val jsonFormats: DefaultFormats.type = DefaultFormats
//
//
//  def main(args: Array[String]): Unit = {
//    val json3 = JObject(
//      "url" -> JArray(List(
//        "jfs/t18382/116/1159461575/215297/485cb1f8/5abe2698N3b4a488f.jpg",
//        "jfs/t18301/164/1675158665/303716/80625e0f/5ad36a70N572225f8.jpg",
//        "jfs/t14830/179/2557125059/346281/aa933a94/5aa6325eN1cab33cd.jpg",
//        "jfs/t16795/339/1300364779/420543/8fb415ba/5ac1fa87Nda9f7007.jpg",
//        "jfs/t17905/303/1420762558/112267/c63356db/5ac9ea90N8febd39f.jpg",
//        "jfs/t15181/266/2641311991/128729/29ff31e4/5ab0b373N2840056f.jpg",
//        "jfs/t19204/266/1557648393/152180/11be4568/5ad01ebaNadc317c6.jpg",
//        "jfs/t16672/21/1298846275/142944/3bf90c71/5ac383edN324e7ce4.jpg",
//        "jfs/t17176/193/1579734168/178695/da9d02fe/5ad01ec2N99ad14e3.jpg",
//        "jfs/t18742/46/1312889229/130141/50411f93/5ac38505N3f3cc933.jpg",
//        "jfs/t14500/338/1493161649/228838/129859da/5a4edffdNb5acff66.jpg",
//        "jfs/t17002/160/1279919562/156462/1b0722f/5ac394f7N1f6763c6.jpg",
//        "jfs/t16822/204/1254335679/342695/fc53c1e4/5ac3a144N563034e1.jpg",
//        "jfs/t17191/178/1628304158/199829/3e600cbc/5ad04853N9e5c6c23.jpg",
//        "jfs/t19579/104/1583659654/80919/7f899521/5ad0586dNace96476.jpg",
//        "jfs/t17146/309/1480412738/547817/8be4c440/5acc7fc2N7c79122f.jpg"
//      )))
//
//    print(json3)
//
//    val response = Http("http://172.28.203.11:80/heguijiance")
//      .postData(json3.toString)
//      .header("content-type", "application/json")
//      .asString
//    println(response.code)
//    println(response.body)
//    println(response.is3xx)
//  }
//}
