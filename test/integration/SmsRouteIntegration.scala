package integration

import ch.wavein.sms_partners.auth.BasicAuthAction
import ch.wavein.sms_partners.models.SmsReceived
import ch.wavein.sms_partners.models.providers.{SmsLogProvider, SmsProvider}
import ch.wavein.sms_partners.viewmodels.responses.SmsSendResponse
import com.google.common.base.Charsets
import com.typesafe.config.ConfigFactory
import mocks.SmsProviderMock
import org.scalatest.concurrent.ScalaFutures
import org.scalatest.time.SpanSugar
import org.scalatest.{FlatSpec, Matchers}
import org.scalatestplus.play.OneAppPerSuite
import play.api.inject.bind
import play.api.inject.guice.GuiceApplicationBuilder
import play.api.libs.json.Json
import play.api.test.Helpers._
import play.api.test._
import play.api.{Application, Configuration, Mode}
import utils.ModelInstances

/**
  * Created by mattia on 12/01/16.
  */
class SmsRouteIntegration extends FlatSpec with Matchers with ScalaFutures with SpanSugar with OneAppPerSuite {
  implicit override lazy val app: Application = new GuiceApplicationBuilder()
    .in(Mode.Test)
    .loadConfig(Configuration(ConfigFactory.load("application.test.conf")))
    .overrides(bind[SmsProvider].to[SmsProviderMock])
    .build()

  val configuration = app.injector.instanceOf[Configuration]

  implicit val p = PatienceConfig(timeout = 10 seconds)

  implicit val srFormatter = Json.format[SmsReceived]


  it should "send an sms correctly" in {
    implicit val ssrFormatter = SmsSendResponse.formatter

    val fakeRequest = FakeRequest(POST, "/v1/sms/send")
      .withJsonBody(Json.obj(
        "from" -> "+123872193123",
        "to" -> Seq("+123872193123"),
        "body" -> "SMS Test"
      ))
      .withHeaders(
        authHeader()
      )


    val result = route(fakeRequest).get

    contentAsJson(result).validate[Seq[SmsSendResponse]].map(_.foreach(_.status shouldBe "queued"))

  }

  it should "receive an sms correctly" in {
    val fakeRequest = FakeRequest(POST, "/v1/sms/receive")
      .withFormUrlEncodedBody(
        "From" -> "From",
        "To" -> "To",
        "Body" -> "Body"
      )

    val result = route(fakeRequest).get

    contentType(result) shouldEqual Some("application/xml")
    contentAsString(result) shouldEqual "<Response></Response>"
  }

  it should "fetch sms correctly" in {
    val smsLogProvider = app.injector.instanceOf[SmsLogProvider]
    val fromPhone = "+391234567"

    whenReady(smsLogProvider.add(ModelInstances.smsReceived.copy(from = fromPhone))) { smsSaved =>
      val fakeRequest = FakeRequest(GET, s"/v1/sms/$fromPhone")
        .withHeaders(authHeader())

      val result = route(fakeRequest).get

      contentAsJson(result)
        .validate[Seq[SmsReceived]]
        .map { smsList =>
          smsList.forall(_.from == fromPhone) shouldBe true
          smsList.contains(smsSaved) shouldBe true

        }
    }

  }

  private def authHeader(): (String, String) = {
    val base64AuthString = new sun.misc.BASE64Encoder()
      .encode((configuration.getString(BasicAuthAction.usernameConfig).get + ":" + configuration.getString(BasicAuthAction.passwordConfig).get).getBytes(Charsets.UTF_8))

    "Authorization" -> ("Basic " + base64AuthString)
  }

}
