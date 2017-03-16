package events

import models.KafkaEvents.{Event, Hello}

/**
  * Created by faiaz on 10.03.17.
  */
class KafkaEventProcessor @Inject()(config: Configuration) extends Actor with ActorLogging {

  private val eventStream = context.system.eventStream
  implicit val materializer: ActorMaterializer = ActorMaterializer()(context.system)
  implicit val ctx: ExecutionContextExecutor = context.dispatcher

  private val server = "localhost:9092"
  private val topic = "test"

  override def preStart(): Unit = {
    super.preStart()
    self ! Init
    log.info("Start EventsProcessorActor")
  }

  override def postStop(): Unit = {
    eventStream.unsubscribe(self)
    super.postStop()
  }

  override def receive = {
    case Init => createProducer()
  }

  private def createProducer() = {
    val producerSettings = ProducerSettings(context.system, new ByteArraySerializer(), new EventSerializer())
      .withBootstrapServers(server)

    val jobManagerSource = Source.actorPublisher[Event](Props(classOf[KafkaEventPublisher]))

    Flow[Event].map {
      case e: Hello => new ProducerRecord[Array[Byte], Event](topic, e)
    }.to(Producer.plainSink(producerSettings))
      .runWith(jobManagerSource)

    Logger.info("init producer")
  }
}

object KafkaEventProcessor {
  case object Init
}
