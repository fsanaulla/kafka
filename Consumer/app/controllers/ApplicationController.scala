package controllers

import play.api.mvc.{Action, Controller}

/**
  * Created by faiaz on 11.03.17.
  */
class ApplicationController extends Controller {

  def hi = Action {
    Ok(views.html.index("Consumer"))
  }

}
