package events

import java.util.UUID

import akka.actor.{Actor, Props}
import akka.kafka.scaladsl.Consumer
import akka.kafka.{ConsumerSettings, Subscriptions}
import akka.stream.ActorMaterializer
import akka.stream.scaladsl.Sink
import events.KafkaEventProcessor.Init
import org.apache.kafka.common.serialization.ByteArrayDeserializer
import play.api.Logger

import scala.concurrent.ExecutionContextExecutor
import scala.concurrent.duration._
import scala.language.implicitConversions

/**
  * Created by faiaz on 11.03.17.
  */
class KafkaEventProcessor extends Actor {

  private val topic = "test"
  private val servers = "localhost:9092"
  private val groupId = "test-groupId"
  private val eventStream = context.system.eventStream

  implicit val materializer: ActorMaterializer = ActorMaterializer()(context.system)
  implicit val ctx: ExecutionContextExecutor = context.dispatcher


  override def preStart(): Unit = {
    super.preStart()
    self ! Init
    Logger.info("Start EventsProcessorActor")
  }

  override def postStop(): Unit = {
    eventStream.unsubscribe(self)
    super.postStop()
  }

  override def receive = {
    case _ => createGlobalConsumer()
  }

  private def createGlobalConsumer() = {
    val consumerSettings = ConsumerSettings(context.system, new ByteArrayDeserializer(), new EventDeserializer())
      .withCommitTimeout(1200.milliseconds)
      .withBootstrapServers(servers)
      .withGroupId(groupId)
      .withClientId(UUID.randomUUID().toString)

    Consumer.plainSource(consumerSettings, Subscriptions.topics(topic)).map(record => record.value)
      .to(Sink.actorSubscriber(Props(classOf[KafkaEventSubscriber])))
      .run()

    Logger.info("Init consumer")
  }
}

object KafkaEventProcessor {
  case object Init
}
