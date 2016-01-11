package ch.wavein.sms_partners.models.providers

import ch.wavein.sms_partners.models.SmsReceived

import scala.concurrent.Future

/**
  * Created by mattia on 11/01/16.
  */
trait SmsLogProvider {
  def log(smsReceived: SmsReceived): Future[SmsReceived]
}
