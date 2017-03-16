package models

/**
  * Created by faiaz on 10.03.17.
  */
object KafkaEvents {

  sealed trait Event {
    val name: String
  }

  case class Hello(override val name: String) extends Event
}
