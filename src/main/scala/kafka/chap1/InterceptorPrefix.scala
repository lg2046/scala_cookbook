package kafka.chap1

import java.util

import org.apache.kafka.clients.producer.{ProducerInterceptor, ProducerRecord, RecordMetadata}

class InterceptorPrefix extends ProducerInterceptor[String, String] {
  private var successValue = 0
  private var failureValue = 0


  //序列化与分区之前调用
  override def onSend(record: ProducerRecord[String, String]): ProducerRecord[String, String] = {
    val modifiedValue = "prefix-" + record.value()
    new ProducerRecord[String, String](
      record.topic(),
      record.partition(),
      record.timestamp(),
      record.key(),
      modifiedValue,
      record.headers()
    )
  }

  override def onAcknowledgement(metadata: RecordMetadata, exception: Exception): Unit = {
    if (exception == null) {
      successValue += 1
    } else {
      failureValue += 1
    }
  }

  override def close(): Unit = println(s"[INFO]发送成功率: ${successValue / (successValue + failureValue) * 100}%")

  override def configure(configs: util.Map[String, _]): Unit = {}
}
