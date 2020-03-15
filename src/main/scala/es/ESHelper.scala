package es

import com.sksamuel.elastic4s.http.{RequestFailure, RequestSuccess}
import com.sksamuel.elastic4s.http.search.SearchResponse

object ESHelper {
  def formatMap[K, V](map: Map[K, V]): String = {
    if (map == null) {
      return "null"
    }
    val stringBuilder = new StringBuilder
    stringBuilder.append("Map(\n")
    map.foreach {
      case (k, v) =>
        val key = k.toString.padTo(20, ' ')
        val value = v.toString
        stringBuilder.append(s"  $key => $value\n")
    }

    stringBuilder.append(")")
    stringBuilder.toString()
  }

  def showResponse(resp: Either[RequestFailure, RequestSuccess[SearchResponse]]): Unit = {
    resp match {
      case Left(failure) => println(failure.error)
      case Right(results) =>
        val searchResponse: SearchResponse = results.result
        searchResponse.hits.hits.foreach {
          hit =>
            println(
              s"""
                 |--Begin--
                 |index: ${hit.index}
                 |type: ${hit.`type`}
                 |id: ${hit.id}
                 |score: ${hit.score}
                 |_source: ${formatMap(hit.sourceAsMap)}
                 |fields: ${hit.fields}
                 |--End
                 """.stripMargin)
        }
    }
  }
}
