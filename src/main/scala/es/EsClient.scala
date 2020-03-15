package es

import com.sksamuel.elastic4s.http.{ElasticClient, ElasticProperties}

object EsClient {
  var connectStr = "localhost:9200" //localhost:9200,localhost2:9200

  private var client: ElasticClient = ElasticClient(ElasticProperties("http://localhost:9200"))
}