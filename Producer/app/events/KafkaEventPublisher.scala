package events

import models.KafkaEvents.Event

/**
  * Created by faiaz on 10.03.17.
  */
class KafkaEventPublisher extends ActorPublisher[Event] {

  override def preStart(): Unit = {
    super.preStart()
    context.system.eventStream.subscribe(self, classOf[Event])
  }

  override def postStop(): Unit = {
    super.postStop()
    context.system.eventStream.unsubscribe(self)
  }

  override def receive = {
    case e: Event =>
      Logger.info(s"send event: $e to kafka")
      onNext(e)

    case other =>
      Logger.warn(s"Receive unsupported event $other")
  }
}
