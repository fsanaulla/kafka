package kafka

import akka.actor.ActorLogging
import akka.stream.actor.ActorSubscriberMessage.{OnError, OnNext}
import akka.stream.actor.{ActorSubscriber, OneByOneRequestStrategy, RequestStrategy}
import models.Events.Event
import play.api.Logger

/**
  * Created by faiaz on 11.03.17.
  */
class KafkaEventSubscriber extends ActorSubscriber with ActorLogging {
  override protected def requestStrategy: RequestStrategy = OneByOneRequestStrategy

  override def receive = {
    case OnNext(e: Option[Event]) => Logger.info(s"Receive Event: $e")
    case OnError(err: Exception) => Logger.error(err.getMessage)
    case other => Logger.warn(s"Receive unsupported event $other")
  }
}
