package kafka

import models.Events.Hello
import play.api.libs.json.{JsValue, Json, Writes}

/**
  * Created by faiaz on 11.03.17.
  */
trait EventJsonFormatter {
  implicit val receivedBtcAddressWrites = new Writes[Hello] {
    override def writes(o: Hello): JsValue = Json.obj("name" -> o.name)
  }
}
