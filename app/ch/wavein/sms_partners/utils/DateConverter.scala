package ch.wavein.sms_partners.utils

import java.sql.Timestamp

import org.joda.time.DateTime

/**
  * Created by mattia on 12/01/16.
  */
object DateConverter {
  def toSqlTimestamp(d: DateTime): Timestamp = new Timestamp(d.getMillis)
  def toDateTime(d: Timestamp) = new DateTime(d.getTime)
}
