package ch.wavein.sms_partners.models.providers

import ch.wavein.sms_partners.models.SmsReceived

import scala.concurrent.Future

/**
  * Created by mattia on 11/01/16.
  */
trait SmsLogProvider {
  def add(smsReceived: SmsReceived): Future[SmsReceived]
  def get(id: Int): Future[Option[SmsReceived]]
  def find(accountSid: String): Future[Seq[SmsReceived]]
  def rm(id: Option[Int]): Future[Int]
}
