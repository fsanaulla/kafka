package controllers

import com.google.inject.Inject
import models.KafkaEvents.Hello
import play.api.mvc.{Action, Controller}
import services.kafka.EventBus

/**
  * Created by faiaz on 10.03.17.
  */
class ApplicationController @Inject()(eventBus: EventBus) extends Controller {

  def hello = Action { _ =>
    eventBus.publish(Hello("hi"))
    Ok(views.html.index("Hello"))
  }
}
