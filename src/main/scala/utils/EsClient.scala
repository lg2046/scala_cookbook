//package utils
//
//
//import com.sksamuel.elastic4s.ElasticsearchClientUri
//import com.sksamuel.elastic4s.http.HttpClient
//
////单个jvm共享一个连接
//object EsClient {
//  var connectStr = "localhost:9200" //localhost:9200,localhost2:9200
//
//  private var client: HttpClient = _
//
//  private def connect(): Unit = {
//    try {
//      client = HttpClient(ElasticsearchClientUri(s"elasticsearch://${connectStr.replaceAll("\\s", "")}"))
//    }
//    catch {
//      case e: Exception => e.printStackTrace()
//    }
//  }
//
//  def getClient: HttpClient = {
//    if (client == null) {
//      connect()
//    }
//    client
//  }
//
//  def reconnect(): Unit = {
//    if (client != null) {
//      client.close()
//    }
//    connect()
//  }
//}