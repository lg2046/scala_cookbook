
package es

import com.sksamuel.elastic4s.http.ElasticDsl._


import com.sksamuel.elastic4s.http.search.SearchResponse
import com.sksamuel.elastic4s.http.{HttpClient, RequestFailure, RequestSuccess}
import com.sksamuel.elastic4s.searches.queries.funcscorer.FieldValueFactorFunctionModifier

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

//    client.close()
//  }
//}
