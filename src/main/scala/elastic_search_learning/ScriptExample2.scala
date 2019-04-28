//package elastic_search_learning
//
//import com.sksamuel.elastic4s.http.ElasticDsl._
//import com.sksamuel.elastic4s.http.HttpClient
//import com.sksamuel.elastic4s.script.ScriptDefinition
//import utils.EsClient
//
//import scala.concurrent.ExecutionContext.Implicits.global
//
//object ScriptExample2 {
//  def main(args: Array[String]): Unit = {
//    val client: HttpClient = EsClient.getClient
//
//    //脚本评分查询 为什么不需要加doc[].value doc['key'] 取出来的是数组就不需要加value doc['key'].length doc['key'][i]就是对应的元素
//    val scriptScore1 = client.execute {
//      search("academics/student").query {
//        functionScoreQuery()
//          .functions(
//            scriptScore(ScriptDefinition(
//              lang = Some("painless"),
//              script =
//                s"""
//                   |int total = 0;
//                   |for(int i = 0; i < doc['base_score'].length; i++) {
//                   |  total += (int) doc['base_score'][i];
//                   |}
//                   |return doc['age'].value;
//                 """.stripMargin.replaceAll("\n", " ")
//            ))
//          ).boostMode("replace")
//      }.explain(true)
//    }.await
//
//    ESHelper.showResponse(scriptScore1)
//
//    client.close()
//  }
//}
