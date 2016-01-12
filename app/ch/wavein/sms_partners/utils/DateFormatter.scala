package ch.wavein.sms_partners.utils

import java.sql.Timestamp
import java.text.SimpleDateFormat
import java.util.Locale

import org.joda.time.DateTime

/**
  * Created by mattia on 12/01/16.
  */
object DateFormatter {
  lazy val timeFormatter = new SimpleDateFormat("HH:mm")
  lazy val timeExportFormatter = new SimpleDateFormat("HH.mm")
  lazy val dateFormatter = new SimpleDateFormat("dd.MM.yyyy")
  lazy val dateFormatterSQL = new SimpleDateFormat("yyyy-MM-dd")
  lazy val timestampFormatterSQL = new SimpleDateFormat("yyyy-MM-dd HH:mm")
  lazy val dayFormatter = new SimpleDateFormat("d")

  def timeExport(t: Timestamp): String = timeExportFormatter.format(t)
  def timeExport(t: DateTime): String = timeExportFormatter.format(t.toDate)

  def time(t: Timestamp): String = timeFormatter.format(t)

  def time(t: DateTime): String = timeFormatter.format(t.toDate)

  def date(d: java.util.Date): String = dateFormatter.format(d)

  def date(d: DateTime): String = dateFormatter.format(d.toDate)

  def dateText(d: java.util.Date, locale: Locale): String = new SimpleDateFormat("d MMMM", locale).format(d)

  def dateTextMonth(d: java.util.Date, locale: Locale): String = new SimpleDateFormat("d. MMMM", locale).format(d)

  def dateTextDay(d: java.util.Date, locale: Locale): String = new SimpleDateFormat("d EEEE", locale).format(d)

  def dateTextDayComplete(d: java.util.Date, locale: Locale): String = new SimpleDateFormat("EEEE, d. MMMM", locale).format(d)

  def day(d: java.util.Date): String = dayFormatter.format(d)

  def timestamp(d: java.util.Date): String = timestampFormatterSQL.format(d)

  def timestamp(d: DateTime): String = timestampFormatterSQL.format(d.toDate)

  def dateSQL(d: String) = new DateTime(dateFormatterSQL.parse(d))

  def timestampSQL(d: String) = new DateTime(timestampFormatterSQL.parse(d))

}
