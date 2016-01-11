package ch.wavein.sms_partners.controllers

import javax.inject.Inject

import ch.wavein.sms_partners.models.Sms
import ch.wavein.sms_partners.models.providers.SmsProvider
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
                              smsProvider: SmsProvider
                              ) extends Controller {
  implicit val ssrsFormatter = SmsSendResponse.formatter
  implicit val ssrqFormatter = SmsSendRequest.formatter

  def sendSms() = Action.async(parse.json[SmsSendRequest]) { implicit request =>
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
    val messageSid = request.body.get("MessageSid")
    val from = request.body.get("From")
    val body = request.body.get("Body")
    Logger.info(messageSid.map(_.toString()).getOrElse("NADA"))
    Logger.info(from.map(_.toString()).getOrElse("NADA"))
    Logger.info(body.map(_.toString()).getOrElse("NADA"))

    // TODO
    Future.successful(Ok("<Response>"))
  }

}
