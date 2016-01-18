package models

import play.api.libs.json._

case class Goal(id: Long, description: String, score: Int)

object Goal {

  implicit val goalFormat = Json.format[Goal]
}