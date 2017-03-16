package kafka

import java.util

import models.Events.{Event, Hello}
import org.apache.kafka.common.serialization.Deserializer
import play.api.libs.json.Json

/**
  * Created by faiaz on 11.03.17.
  */
class EventDeserializer extends Deserializer[Option[Event]] with EventJsonFormatter {
  override def configure(configs: util.Map[String, _], isKey: Boolean): Unit = {}

  override def close(): Unit = {}

  override def deserialize(topic: String, data: Array[Byte]): Option[Event] = {
    val strMsg = new String(data, "UTF-8")
    val json = Json.parse(strMsg)

    (json \ "name").as[String] match {
      case "hi" =>
        Some(Hello("hi"))
    }
  }
}
