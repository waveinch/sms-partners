package ch.wavein.sms_partners.controllers

import javax.inject.Inject

import ch.wavein.sms_partners.auth.BasicAuthAction
import ch.wavein.sms_partners.models.{SmsReceived, Sms}
import ch.wavein.sms_partners.models.providers.{SmsLogProvider, SmsProvider}
import ch.wavein.sms_partners.viewmodels.filters.SmsLogFilter
import ch.wavein.sms_partners.viewmodels.requests.SmsSendRequest
import ch.wavein.sms_partners.viewmodels.responses.SmsSendResponse
import play.api.Logger
import play.api.libs.json
import play.api.libs.json.Json
import play.api.mvc.{Action, Controller}
import play.api.libs.concurrent.Execution.Implicits._

import scala.concurrent.Future


/**
  * Created by mattia on 11/01/16.
  */
class SmsController @Inject() (
                              smsProvider: SmsProvider,
                              smsLogProvider: SmsLogProvider,
                              basicAuthAction: BasicAuthAction
                              ) extends Controller {
  implicit val ssrsFormatter = SmsSendResponse.formatter
  implicit val ssrqFormatter = SmsSendRequest.formatter
  implicit val srFormatter = SmsReceived.formatter

  def sendSms() = basicAuthAction.async(parse.json[SmsSendRequest]) { implicit request =>
    val smsRequest = request.body

    val requestsFut = smsRequest.to map { destination =>
      val sms = Sms(
        to = destination,
        from = smsRequest.from,
        body = smsRequest.body
      )

      smsProvider.send(sms)
    }

    Future.sequence(requestsFut) map { requests =>
      val response = for {
        request <- requests
      } yield SmsSendResponse(request.status)

      Ok(Json.toJson(response))
    }
  }

  def receiveSms() = Action.async(parse.urlFormEncoded) { implicit request =>
    for {
      _ <- smsLogProvider.add(SmsReceived.fromHttpForm(request.body))
    } yield Ok("<Response>") // TODO: Add XML response type
  }

  def querySms(accountSid: String) = basicAuthAction.async { implicit request =>
    for {
      smss <- smsLogProvider.find(SmsLogFilter(accountSid = Some(accountSid)))
    } yield Ok(Json.toJson(smss))
  }

}
