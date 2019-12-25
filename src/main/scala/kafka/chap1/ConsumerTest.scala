package kafka.chap1

import java.time.Duration
import java.util.Properties

import org.apache.kafka.clients.consumer.{ConsumerConfig, KafkaConsumer}
import org.apache.kafka.common.serialization.StringDeserializer

import scala.collection.JavaConverters._

object ConsumerTest {
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

    consumer.subscribe(List(TOPIC).asJava)

    var assignment = consumer.assignment()
    while(assignment.size() == 0){
      consumer.poll(Duration.ofMillis(1000))
      assignment = consumer.assignment()
    }

    println(consumer.assignment())

    //assignment消费者所分配到的分区信息 //poll一次(以获取分区信息) 后强行让每个分区从10开始消费
    consumer.assignment().forEach(tp => {
      println("set tp: " + tp)
      consumer.seek(tp, 10)
    })

    //每个分区的末尾位置
    println(consumer.endOffsets(consumer.assignment()))

    while (true) {
      val records = consumer.poll(Duration.ofMillis(1000))

      //按分区处理
      //println(records.partitions())

      //for (partition <- records.partitions().asScala) {
      //  println(records.records(partition))   //还有records.records(topic)
      //}

      for (record <- records.iterator().asScala) {
        println(record)
      }
    }
  }
}
