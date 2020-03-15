package es

//    //脚本做为查询 可以放在query, 也可以放在filter里面 并返回脚本字段
//    val scriptQuery3 = client.execute {
//      search("sat/scores").query {
//        scriptQuery(
//          ScriptDefinition(
//            lang = Some("painless"),
//            script =
//              s"""
//                 |return doc['AvgScrRead'].value < 350 && doc['AvgScrMath'].value > 350;
//               """.stripMargin.replaceAll("\n", " ")
//          )
//        )
//      }.scriptfields(
//        ScriptFieldDefinition("sat_scores",
//          ScriptDefinition(
//            lang = Some("painless"),
//            script =
//              s"""
//                 |String[] score_names = new String[] {'AvgScrRead', 'AvgScrWrit', 'AvgScrMath'};
//                 |
//                 |ArrayList sat_scores = new ArrayList();
//                 |int total_score = 0;
//                 |
//                 |for(int i = 0; i < score_names.length; i++) {
//                 |  int sc = (int) doc[score_names[i]].value;
//                 |  total_score += sc;
//                 |  sat_scores.add(sc);
//                 |}
//                 |
//                 |sat_scores.add(total_score);
//                 |return sat_scores;
//                 |
//              """.stripMargin.replaceAll("\n", " ")
//          )
//        )
//      ).sourceExclude("_all")
//    }.await
//
//    ESHelper.showResponse(scriptQuery3)


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
