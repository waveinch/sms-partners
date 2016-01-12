package ch.wavein.sms_partners.models.auth

import play.api.libs.json.Json

/**
  * Created by mattia on 12/01/16.
  */
case class Auth(username: String, password: String)

object Auth {
  val formatter = Json.format[Auth]
}