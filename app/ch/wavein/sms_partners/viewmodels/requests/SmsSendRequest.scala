package ch.wavein.sms_partners.viewmodels.requests

import play.api.libs.json.Json

/**
  * Created by mattia on 11/01/16.
  */
case class SmsSendRequest(
                         from: String,
                         to: Seq[String],
                         body: String
                         )

object SmsSendRequest {
  val formatter = Json.format[SmsSendRequest]
}
