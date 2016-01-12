package ch.wavein.sms_partners.models

import play.api.libs.json.Json

/**
  * Created by mattia on 11/01/16.
  */
case class SmsReceived(
                      id: Option[Int],
                      messageSid: String,
                      accountSid: String,
                      messagingServiceSid: String,
                      from: String,
                      to: String,
                      body: String
                      )

object SmsReceived {
  val formatter = Json.format[SmsReceived]

  def fromHttpForm(form: Map[String, Seq[String]]): SmsReceived =
    SmsReceived(
      id = None,
      messageSid = getString("MessageSid", form),
      accountSid = getString("AccountSid", form),
      messagingServiceSid = getString("MessagingServiceSid", form),
      from = getString("From", form),
      to = getString("To", form),
      body = getString("Body", form)
    )

  private def getString(name: String, map: Map[String, Seq[String]]) =
    map.get(name).flatMap(_.headOption).getOrElse("")

}
