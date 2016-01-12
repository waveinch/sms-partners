package ch.wavein.sms_partners.models.providers

import javax.inject.Inject

import ch.wavein.sms_partners.models.SmsReceived
import ch.wavein.sms_partners.models.schema.Tables
import ch.wavein.sms_partners.models.schema.Tables.SmsLogsRow
import ch.wavein.sms_partners.utils.{DateConverter, DateFormatter}
import ch.wavein.sms_partners.viewmodels.filters.SmsLogFilter
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
        smsReceived.body,
        DateConverter.toSqlTimestamp(smsReceived.created)
      )
    }.map(newId => smsReceived.copy(id = Some(newId)))
  }

  override def get(id: Int): Future[Option[SmsReceived]] = dbConfig.db.run {
    Tables.SmsLogs
      .filter(_.id === id)
      .result
      .headOption
  }.map(_.map(smsRow2SmsReceived))

  override def find(filter: SmsLogFilter): Future[Seq[SmsReceived]] = dbConfig.db.run {
    Tables.SmsLogs
      .filter(filterFactory(filter))
      .to[Seq]
      .result
  }.map(_.map(smsRow2SmsReceived))

  override def rm(filter: SmsLogFilter): Future[Int] = {

    val action = Tables.SmsLogs
      .filter(filterFactory(filter))
      .delete

    dbConfig.db.run(action)
  }

  private def filterFactory(filter: SmsLogFilter)(t: Tables.SmsLogs) = {
    Seq(
      filter.id.map(id => t.id === id),
      filter.accountSid.map(a => t.accountSid === a),
      filter.fromPhone.map(f => t.fromPhone === f)
    ).collect({ case Some(criteria) => criteria}).reduceLeftOption(_ && _).getOrElse(true: Rep[Boolean])
  }

  private def smsRow2SmsReceived(s: SmsLogsRow): SmsReceived =
    SmsReceived(
      id = Some(s.id),
      messageSid = s.messageSid,
      accountSid = s.accountSid,
      messagingServiceSid = s.messagingServiceSid,
      from = s.fromPhone,
      to = s.toPhone,
      body = s.body,
      created = DateConverter.toDateTime(s.created)
    )


}
