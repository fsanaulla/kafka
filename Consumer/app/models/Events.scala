package models

/**
  * Created by faiaz on 11.03.17.
  */
object Events {

  sealed trait Event {
    val name: String
  }

  case class Hello(override val name: String) extends Event

}
