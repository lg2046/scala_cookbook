package kafka.chap1

import java.time.Duration
import java.util.Properties

import org.apache.kafka.clients.consumer.{ConsumerConfig, KafkaConsumer}
import org.apache.kafka.common.TopicPartition
import org.apache.kafka.common.errors.WakeupException
import org.apache.kafka.common.serialization.StringDeserializer

import scala.collection.JavaConverters._
import scala.util.control.Breaks._

object OffsetTest {
  val BROKER_LIST = "localhost:9092"
  val TOPIC = "topic-demo"
  val GROUP_ID = "group.demo"
  val CLIENT_ID = "client.id.demo"


  def main(args: Array[String]): Unit = {
    val props = new Properties()
    props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, classOf[StringDeserializer].getName)
    props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, classOf[StringDeserializer].getName)

    props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, BROKER_LIST)
    props.put(ConsumerConfig.GROUP_ID_CONFIG, GROUP_ID)
    props.put(ConsumerConfig.CLIENT_ID_CONFIG, CLIENT_ID)

    val consumer = new KafkaConsumer[String, String](props)

    val tp = new TopicPartition(TOPIC, 3)
    consumer.assign(List(tp).asJava)

    println(consumer.committed(tp))


    val lastConsumedOffset = -1

    breakable {
      while (true) {
        val records = consumer.poll(Duration.ofMillis(1000))

        if (records.isEmpty) {
          break()
        }

        val partitionRecords = records.records(tp)
        partitionRecords.get(partitionRecords.size() - 1).offset()
        //同步提交消费位移
        consumer.commitSync()
      }
    }

    println("lastConsumedOffset: " + lastConsumedOffset)

    println("committed offset is " + consumer.committed(tp).offset())

    //下一个要取的值
    println("next record offset" + consumer.position(tp))
  }
}
