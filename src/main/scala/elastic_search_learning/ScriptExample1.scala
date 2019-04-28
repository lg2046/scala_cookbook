//package elastic_search_learning
//
//import com.sksamuel.elastic4s.http.ElasticDsl._
//import com.sksamuel.elastic4s.http.search.SearchResponse
//import com.sksamuel.elastic4s.http.{HttpClient, RequestFailure, RequestSuccess}
//import com.sksamuel.elastic4s.script.{ScriptDefinition, ScriptFieldDefinition}
//import com.sksamuel.elastic4s.searches.queries.funcscorer.FieldValueFactorFunctionModifier
//import utils.EsClient
//
//import scala.concurrent.ExecutionContext.Implicits.global
//
//object ScriptExample1 {
//  def main(args: Array[String]): Unit = {
//    val client: HttpClient = EsClient.getClient
//
//    //插入文档
//    client.execute {
//      indexInto("myindex/type") id "2" fields(
//        "screen_name" -> "coldplay",
//        "followers_count" -> 2000,
//        "create_at" -> "2015-09-21"
//      )
//    }.await
//
//    //简单查询
//    val resp: Either[RequestFailure, RequestSuccess[SearchResponse]] = client.execute {
//      search("bookdb_index/book").query {
//        termQuery("title", "action")
//      }
//        .sourceInclude("title", "summary")
//        .from(0) //从0开始
//        .size(2) //返回文档数
//    }.await
//
//
//    //bool查询
//    val resp2 = client.execute {
//      search("bookdb_index/book").query {
//        boolQuery()
//          .must(
//            matchQuery("authors", "clinton gormely"),
//            boolQuery().should(
//              matchQuery("title", "Elasticsearch"),
//              matchQuery("title", "Solr")
//            )
//          )
//          .not(matchQuery("authors", "radu gheorge"))
//      }
//    }.await
//
//
//    //term range query
//    val resp3 = client.execute {
//      search("bookdb_index/book").query {
//        boolQuery()
//          .must(
//            termsQuery("publisher", "oreilly", "packt"),
//            rangeQuery("publish_date").gte("2015-01-01").lte("2015-12-31")
//          )
//
//      }
//    }.await
//
//
//    val resp4 = client.execute {
//      search("bookdb_index/book").query {
//        boolQuery().must(
//          multiMatchQuery("elasticsearch").fields("title", "summary")
//        ).filter(
//          boolQuery()
//            .must(rangeQuery("num_reviews").gte(20))
//            .not(rangeQuery("publish_date").lte("2014-12-31"))
//            .should(termQuery("publisher", "oreilly")) //should在filter中会以至少匹配一个运行
//        )
//
//      }
//    }.await
//
//    val scoreQuery0 = client.execute {
//      search("bookdb_index/book").query {
//        multiMatchQuery("search engine").fields("title", "summary")
//      }
//    }.await
//
//    //old_score * log(1 + factor * number_of_votes)
//    val scoreQuery1 = client.execute {
//      search("bookdb_index/book").query {
//        functionScoreQuery(multiMatchQuery("search engine").fields("title", "summary"))
//          .functions(
//            fieldFactorScore("num_reviews").modifier(FieldValueFactorFunctionModifier.LN1P).factor(2)
//            //, weightScore(2).filter(termQuery("publisher", "oreilly"))
//          )
//        //.maxBoost(1.5) /*限制对原分数的最大提升 _score * 1.5*/
//      }
//    }.await
//
//    //old_score * log(1 + factor * number_of_votes)
//    val scoreQuery2 = client.execute {
//      search("bookdb_index/book").query {
//        functionScoreQuery(multiMatchQuery("search engine").fields("title", "summary"))
//          .functions(
//            //"2014-06-05"附近一个月的为1分，再扩展一个月 降低一半的分
//            exponentialScore("publish_date", "2014-06-05", "30d").offset("30d")
//          )
//          .boostMode("replace")
//      }
//    }.await
//
//    //返回脚本字段
//    val scriptFieldQuery = client.execute {
//      search("sat/scores").query {
//        boolQuery().must(
//          matchQuery("cname", "Alameda"),
//          matchQuery("rtype", "S"),
//          termQuery("NumTstTakr", 92)
//        )
//      }.scriptfields(
//        ScriptFieldDefinition(
//          field = "some_scores",
//          script = ScriptDefinition(
//            script =
//              s"""
//                 |long year = doc['year'].value;
//                 |return year + 20;
//              """.stripMargin.replaceAll("\n", " ")
//            , lang = Some("painless")
//          )
//        )
//      )
//        .sourceExclude("_all") //用了script fields默认不返回source
//        .size(10)
//    }.await
//
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
//
//    client.close()
//  }
//}
