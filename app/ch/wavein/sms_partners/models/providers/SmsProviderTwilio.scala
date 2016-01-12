package ch.wavein.sms_partners.models.providers

import javax.inject.Inject

import ch.wavein.sms_partners.models.Sms
import ch.wavein.sms_partners.viewmodels.responses.SmsSendResponse
import com.twilio.sdk.TwilioRestClient
import org.apache.http.NameValuePair
import org.apache.http.message.BasicNameValuePair
import play.api.Configuration
import scala.collection.JavaConversions._
import play.api.libs.concurrent.Execution.Implicits._

import scala.concurrent.Future
import scala.util.{Failure, Success, Try}

/**
  * Created by mattia on 11/01/16.
  */
class SmsProviderTwilio @Inject()(
                                  configuration: Configuration
                                  ) extends SmsProvider {
  val sid = configuration.getString("twilio.sid")
    .getOrElse(throw new Exception("twilio.sid " + "not found"))
  val token = configuration.getString("twilio.token")
    .getOrElse(throw new Exception("twilio.token " + "not found"))

  override def send(sms: Sms): Future[SmsSendResponse] = Future {
    val client = new TwilioRestClient(sid, token)

    // Build the parameters
    val params = Seq(
      new BasicNameValuePair("To", sms.to),
      new BasicNameValuePair("From", sms.from),
      new BasicNameValuePair("Body", sms.body)
    )

    val messageFactory = client.getAccount().getMessageFactory
    Try(messageFactory.create(params)) match {
      case Success(message) => SmsSendResponse(message.getStatus)
      case Failure(ex: Exception) => SmsSendResponse(ex.toString)
    }

  }
}
