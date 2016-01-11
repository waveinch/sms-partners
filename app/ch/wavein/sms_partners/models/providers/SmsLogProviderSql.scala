package ch.wavein.sms_partners.models.providers

import javax.inject.Inject

import ch.wavein.sms_partners.models.SmsReceived
import ch.wavein.sms_partners.models.schema.Tables
import ch.wavein.sms_partners.models.schema.Tables.SmsLogsRow
import play.api.db.slick.DatabaseConfigProvider
import slick.driver.JdbcProfile
import play.api.libs.concurrent.Execution.Implicits._


import scala.concurrent.Future

/**
  * Created by mattia on 11/01/16.
  */
class SmsLogProviderSql @Inject()(dbConfigProvider: DatabaseConfigProvider) extends SmsLogProvider {
  val dbConfig = dbConfigProvider.get[JdbcProfile]
  import dbConfig.driver.api._

  override def log(smsReceived: SmsReceived): Future[SmsReceived] = {
    dbConfig.db.run {
      Tables.SmsLogs += SmsLogsRow(
        -1, //AutoInc
        smsReceived.messageSid,
        smsReceived.accountSid,
        smsReceived.messagingServiceSid,
        smsReceived.from,
        smsReceived.to,
        smsReceived.body)
    }.map(_ => smsReceived)
  }
}
