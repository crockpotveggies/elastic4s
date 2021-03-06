package com.sksamuel.elastic4s.testkit

import com.sksamuel.elastic4s.http.JavaClient
import com.sksamuel.elastic4s.{ElasticClient, ElasticDsl, ElasticProperties}

import scala.util.Try

trait DockerTests extends ElasticDsl with ClientProvider {

  val client = ElasticClient(JavaClient(ElasticProperties(s"http://${sys.env.getOrElse("ES_HOST", "127.0.0.1")}:${sys.env.getOrElse("ES_PORT", "9200")}")))

  protected def deleteIdx(indexName: String): Unit = {
    Try {
      client.execute {
        ElasticDsl.deleteIndex(indexName)
      }.await
    }
  }

  protected def createIdx(name: String) = Try {
    client.execute {
      createIndex(name)
    }.await
  }

  protected def cleanIndex(indexName: String): Unit = {
    deleteIdx(indexName)
    createIdx(indexName)
  }
}
