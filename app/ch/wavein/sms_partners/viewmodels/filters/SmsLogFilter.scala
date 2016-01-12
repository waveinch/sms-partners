package ch.wavein.sms_partners.viewmodels.filters

/**
  * Created by mattia on 12/01/16.
  */
case class SmsLogFilter(
                       id: Option[Int] = None,
                       accountSid: Option[String] = None,
                       fromPhone: Option[String] = None
                       )
