package services.kafka

import akka.actor.{ActorRef, ActorSystem}
import com.google.inject.Inject
import models.KafkaEvents.Event

/**
  * Created by faiaz on 11.03.17.
  */
trait EventBus {
  def publish(e: Event): Unit
  def subscribe(s: ActorRef, event: Class[_]): Unit
}

class StreamEventBus @Inject()(system: ActorSystem) extends EventBus {

  private val eventStream = system.eventStream

  override def publish(e: Event): Unit = eventStream.publish(e)

  override def subscribe(s: ActorRef, event: Class[_]): Unit = eventStream.subscribe(s, event)

}
