package ch.wavein.sms_partners.models.providers

import ch.wavein.sms_partners.models.SmsReceived
import ch.wavein.sms_partners.viewmodels.filters.SmsLogFilter

import scala.concurrent.Future

/**
  * Created by mattia on 11/01/16.
  */
trait SmsLogProvider {
  def add(smsReceived: SmsReceived): Future[SmsReceived]
  def get(id: Int): Future[Option[SmsReceived]]
  def find(filter: SmsLogFilter): Future[Seq[SmsReceived]]
  def rm(filter: SmsLogFilter): Future[Int]
}
