package events

import java.util

import models.KafkaEvents.Event

/**
  * Created by faiaz on 10.03.17.
  */
object Serializer {

  class EventSerializer extends Serializer[Event]{
    override def configure(configs: util.Map[String, _], isKey: Boolean): Unit = {}

    override def serialize(topic: String, data: Event): Array[Byte] =
      Json.stringify(asJson(data)).getBytes("UTF-8")

    override def close(): Unit = {}

    private def asJson(e: Event): JsObject = Json.obj("name" -> e.name)
  }

}
