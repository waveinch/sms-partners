package ch.wavein.sms_partners.viewmodels.responses

import play.api.libs.json.Json

/**
  * Created by mattia on 11/01/16.
  */
case class SmsSendResponse(
                          status: String
                          )

object SmsSendResponse {
  val formatter = Json.format[SmsSendResponse]
}
