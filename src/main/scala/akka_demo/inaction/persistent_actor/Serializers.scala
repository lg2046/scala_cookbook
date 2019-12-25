package akka_demo.inaction.persistent_actor

import akka.serialization.Serializer
import akka_demo.inaction.persistent_actor.Calculator.Event
import play.api.libs.json.Json


//序列化器必须创建Event或Snapshot的字节数组展示
class CalculatorEventSerializer extends Serializer {
  override def identifier: Int = 123678213

  //在fromBinary方法中 该序列化器是否需要manifest
  override def includeManifest: Boolean = false

  //序列化对于为字节数组
  override def toBinary(o: AnyRef): Array[Byte] = o match {
    case e: Calculator.Event => Json.toJson(e).toString().getBytes
  }

  //从字节数组中产生对象
  override def fromBinary(bytes: Array[Byte], manifest: Option[Class[_]]): AnyRef = {
    Json.parse(new String(bytes)).as[Event]
  }
}

class CalculatorSnapshotSerializer extends Serializer {
  override def identifier: Int = 123678214

  //在fromBinary方法中 该序列化器是否需要manifest
  override def includeManifest: Boolean = false

  //序列化对于为字节数组
  override def toBinary(o: AnyRef): Array[Byte] = o match {
    case e: Calculator.Snapshot => Json.toJson(e).toString().getBytes
  }

  //从字节数组中产生对象
  override def fromBinary(bytes: Array[Byte], manifest: Option[Class[_]]): AnyRef = {
    Json.parse(new String(bytes)).as[Calculator.Snapshot]
  }
}

