package utils

import ch.wavein.sms_partners.models.SmsReceived
import ch.wavein.sms_partners.viewmodels.responses.SmsSendResponse
import org.joda.time.DateTime

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
    to = "to",
    created = DateTime.now.withMillisOfSecond(0)
  )

  val smsSendResponseSuccess = SmsSendResponse("queued")
}
