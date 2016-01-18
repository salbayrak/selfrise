package models

import play.api.libs.json._

case class Person(id: Long, name: String, surname: String, role: String, age: Int)

object Person {
  
  implicit val personFormat = Json.format[Person]
}