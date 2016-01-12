package models.providers

import ch.wavein.sms_partners.models.providers.SmsLogProviderSql
import ch.wavein.sms_partners.viewmodels.filters.SmsLogFilter
import com.typesafe.config.ConfigFactory
import org.scalatest.{FlatSpec, Matchers}
import org.scalatest.concurrent.ScalaFutures
import org.scalatest.time.SpanSugar
import play.api.inject.guice.GuiceApplicationBuilder
import play.api.{Configuration, Mode}
import utils.ModelInstances
import org.scalatest.concurrent.ScalaFutures

/**
  * Created by mattia on 12/01/16.
  */
class SmsLogProviderSqlSpec extends FlatSpec with Matchers with ScalaFutures with SpanSugar {
  val application = new GuiceApplicationBuilder()
    .in(Mode.Test)
    .loadConfig(Configuration(ConfigFactory.load("application.test.conf")))
    .build()

  val configuration = application.injector.instanceOf[Configuration]
  val smsLogProvider = application.injector.instanceOf[SmsLogProviderSql]

  implicit val p = PatienceConfig(timeout = 10 seconds)

  it should "add, retrieve and delete an SMS log" in {
    val sms = ModelInstances.smsReceived
    whenReady(smsLogProvider.add(sms)) { smsSaved =>
      smsSaved shouldBe sms.copy(id = smsSaved.id)

      whenReady(smsLogProvider.get(smsSaved.id.get)) { smsRetrieved =>
        smsSaved shouldBe smsRetrieved.get

        whenReady(smsLogProvider.rm(SmsLogFilter(smsSaved.id))) { affectedRows =>
          affectedRows shouldBe 1
        }
      }
    }
  }

}
