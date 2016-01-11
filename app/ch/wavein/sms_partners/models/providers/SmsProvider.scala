package ch.wavein.sms_partners.models.providers

import ch.wavein.sms_partners.models.Sms
import ch.wavein.sms_partners.viewmodels.responses.SmsSendResponse

import scala.concurrent.Future

/**
  * Created by mattia on 11/01/16.
  */
trait SmsProvider {
  def send(sms: Sms): Future[SmsSendResponse]
}
