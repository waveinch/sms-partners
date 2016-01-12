package ch.wavein.sms_partners.modules

import ch.wavein.sms_partners.auth.BasicAuthAction
import ch.wavein.sms_partners.models.providers.{SmsLogProviderSql, SmsLogProvider, SmsProvider, SmsProviderTwilio}
import com.google.inject.AbstractModule
import net.codingwell.scalaguice.ScalaModule
import play.api.libs.concurrent.AkkaGuiceSupport

/**
  * Created by mattia on 11/01/16.
  */
class AppModule  extends AbstractModule with ScalaModule with AkkaGuiceSupport {
  override def configure(): Unit = {
    bind[SmsProvider].to[SmsProviderTwilio]
    bind[SmsLogProvider].to[SmsLogProviderSql]
    //bind[BasicAuthAction].to[BasicAuthAction]
  }
}
