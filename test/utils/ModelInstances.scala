package utils

import ch.wavein.sms_partners.models.SmsReceived

/**
  * Created by mattia on 12/01/16.
  */
object ModelInstances {
  val smsReceived = SmsReceived(
    Some(99),
    messageSid = "messageSid",
    accountSid = "accountSid",
    messagingServiceSid = "messagingServiceSid",
    from = "from",
    body = "body",
    to = "to"
  )
}
