package mocks

import ch.wavein.sms_partners.models.Sms
import ch.wavein.sms_partners.models.providers.SmsProvider
import ch.wavein.sms_partners.viewmodels.responses.SmsSendResponse
import utils.ModelInstances

import scala.concurrent.Future

/**
  * Created by mattia on 12/01/16.
  */
class SmsProviderMock extends SmsProvider {
  override def send(sms: Sms): Future[SmsSendResponse] = Future.successful(ModelInstances.smsSendResponseSuccess)
}
