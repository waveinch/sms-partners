package ch.wavein.sms_partners.models.providers

import javax.inject.Inject

import ch.wavein.sms_partners.models.SmsReceived
import ch.wavein.sms_partners.models.schema.Tables
import ch.wavein.sms_partners.models.schema.Tables.SmsLogsRow
import play.api.db.slick.DatabaseConfigProvider
import slick.driver.JdbcProfile
import play.api.libs.concurrent.Execution.Implicits._
import slick.lifted.BooleanColumnExtensionMethods


import scala.concurrent.Future

/**
  * Created by mattia on 11/01/16.
  */
class SmsLogProviderSql @Inject()(dbConfigProvider: DatabaseConfigProvider) extends SmsLogProvider {
  val dbConfig = dbConfigProvider.get[JdbcProfile]
  import dbConfig.driver.api._

  override def add(smsReceived: SmsReceived): Future[SmsReceived] = {
    dbConfig.db.run {
      (Tables.SmsLogs returning Tables.SmsLogs.map(_.id)) += SmsLogsRow(
        -1, //AutoInc
        smsReceived.messageSid,
        smsReceived.accountSid,
        smsReceived.messagingServiceSid,
        smsReceived.from,
        smsReceived.to,
        smsReceived.body)
    }.map(newId => smsReceived.copy(id = Some(newId)))
  }

  override def get(id: Int): Future[Option[SmsReceived]] = dbConfig.db.run {
    Tables.SmsLogs
      .filter(_.id === id)
      .result
      .headOption
  }.map(_.map(smsRow2SmsReceived))

  override def find(accountSid: String): Future[Seq[SmsReceived]] = dbConfig.db.run {
    Tables.SmsLogs
      .filter(_.accountSid === accountSid)
      .to[Seq]
      .result
  }.map(_.map(smsRow2SmsReceived))

  override def rm(id: Option[Int]): Future[Int] = {
    val filters = (s: Tables.SmsLogs) => Seq(
      id.map(idd => s.id === idd)
    ).collect({ case Some(criteria) => criteria}).reduceLeftOption(_ && _).getOrElse(true: Rep[Boolean])

    val action = Tables.SmsLogs
      .filter(filters)
      .delete

    dbConfig.db.run(action)

  }

  private def smsRow2SmsReceived(s: SmsLogsRow): SmsReceived =
    SmsReceived(
      id = Some(s.id),
      messageSid = s.messageSid,
      accountSid = s.accountSid,
      messagingServiceSid = s.messagingServiceSid,
      from = s.fromPhone,
      to = s.toPhone,
      body = s.body
    )


}
