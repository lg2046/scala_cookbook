package kafka.chap1

import java.util.Properties

import org.apache.kafka.clients.producer.{Callback, KafkaProducer, ProducerConfig, ProducerRecord, RecordMetadata}
import org.apache.kafka.common.serialization.StringSerializer

import scala.util.Random

object ProducerTest {
  val BROKER_LIST = "localhost:9092"
  val TOPIC = "topic-demo"

  def main(args: Array[String]): Unit = {
    val props = new Properties()
    props.put("key.serializer", classOf[StringSerializer].getName)
    props.put("value.serializer", classOf[StringSerializer].getName)
    props.put(ProducerConfig.INTERCEPTOR_CLASSES_CONFIG, classOf[InterceptorPrefix].getName)

    props.put("bootstrap.servers", BROKER_LIST)

    val producer = new KafkaProducer[String, String](props)


    (1 to 10) foreach { i =>
      val producerRecord = new ProducerRecord[String, String](TOPIC, i.toString, "Hello World")
      producer.send(producerRecord, (recordMetadata: RecordMetadata, e: Exception) => {
        if (e != null) {
          println("发送失败")
        } else {
          println("发送成功")
        }
      })
    }


    producer.flush()

    producer.close()
  }
}
