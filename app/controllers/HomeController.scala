package controllers

import javax.inject.Inject

import play.api.mvc._
import play.api.i18n._

/**
  * Created by salbayrak on 18/01/16.
  */
class HomeController @Inject() (val messagesApi: MessagesApi) extends Controller with I18nSupport{

  def index = Action {
    Ok(views.html.home())
  }

  def leaderHome = Action {
    Ok(views.html.leaderhome())
  }

  def memberHome = Action {
    Ok(views.html.memberhome())
  }

}
