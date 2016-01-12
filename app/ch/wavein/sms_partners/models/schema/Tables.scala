package ch.wavein.sms_partners.models.schema
// AUTO-GENERATED Slick data model
/** Stand-alone Slick data model for immediate use */
object Tables extends {
  val profile = slick.driver.MySQLDriver
} with Tables

/** Slick data model trait for extension, choice of backend or usage in the cake pattern. (Make sure to initialize this late.) */
trait Tables {
  val profile: slick.driver.JdbcProfile
  import profile.api._
  import slick.model.ForeignKeyAction
  // NOTE: GetResult mappers for plain SQL are only generated for tables where Slick knows how to map the types of all columns.
  import slick.jdbc.{GetResult => GR}

  /** DDL for all tables. Call .create to execute. */
  lazy val schema: profile.SchemaDescription = SmsLogs.schema
  @deprecated("Use .schema instead of .ddl", "3.0")
  def ddl = schema

  /** Entity class storing rows of table SmsLogs
    *  @param id Database column id SqlType(INT UNSIGNED), AutoInc, PrimaryKey
    *  @param messageSid Database column message_sid SqlType(VARCHAR), Length(34,true)
    *  @param accountSid Database column account_sid SqlType(VARCHAR), Length(34,true)
    *  @param messagingServiceSid Database column messaging_service_sid SqlType(VARCHAR), Length(34,true)
    *  @param fromPhone Database column from_phone SqlType(VARCHAR), Length(255,true)
    *  @param toPhone Database column to_phone SqlType(VARCHAR), Length(255,true)
    *  @param body Database column body SqlType(TEXT)
    *  @param created Database column created SqlType(DATETIME) */
  case class SmsLogsRow(id: Int, messageSid: String, accountSid: String, messagingServiceSid: String, fromPhone: String, toPhone: String, body: String, created: java.sql.Timestamp)
  /** GetResult implicit for fetching SmsLogsRow objects using plain SQL queries */
  implicit def GetResultSmsLogsRow(implicit e0: GR[Int], e1: GR[String], e2: GR[java.sql.Timestamp]): GR[SmsLogsRow] = GR{
    prs => import prs._
      SmsLogsRow.tupled((<<[Int], <<[String], <<[String], <<[String], <<[String], <<[String], <<[String], <<[java.sql.Timestamp]))
  }
  /** Table description of table sms_logs. Objects of this class serve as prototypes for rows in queries. */
  class SmsLogs(_tableTag: Tag) extends Table[SmsLogsRow](_tableTag, "sms_logs") {
    def * = (id, messageSid, accountSid, messagingServiceSid, fromPhone, toPhone, body, created) <> (SmsLogsRow.tupled, SmsLogsRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = (Rep.Some(id), Rep.Some(messageSid), Rep.Some(accountSid), Rep.Some(messagingServiceSid), Rep.Some(fromPhone), Rep.Some(toPhone), Rep.Some(body), Rep.Some(created)).shaped.<>({r=>import r._; _1.map(_=> SmsLogsRow.tupled((_1.get, _2.get, _3.get, _4.get, _5.get, _6.get, _7.get, _8.get)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))

    /** Database column id SqlType(INT UNSIGNED), AutoInc, PrimaryKey */
    val id: Rep[Int] = column[Int]("id", O.AutoInc, O.PrimaryKey)
    /** Database column message_sid SqlType(VARCHAR), Length(34,true) */
    val messageSid: Rep[String] = column[String]("message_sid", O.Length(34,varying=true))
    /** Database column account_sid SqlType(VARCHAR), Length(34,true) */
    val accountSid: Rep[String] = column[String]("account_sid", O.Length(34,varying=true))
    /** Database column messaging_service_sid SqlType(VARCHAR), Length(34,true) */
    val messagingServiceSid: Rep[String] = column[String]("messaging_service_sid", O.Length(34,varying=true))
    /** Database column from_phone SqlType(VARCHAR), Length(255,true) */
    val fromPhone: Rep[String] = column[String]("from_phone", O.Length(255,varying=true))
    /** Database column to_phone SqlType(VARCHAR), Length(255,true) */
    val toPhone: Rep[String] = column[String]("to_phone", O.Length(255,varying=true))
    /** Database column body SqlType(TEXT) */
    val body: Rep[String] = column[String]("body")
    /** Database column created SqlType(DATETIME) */
    val created: Rep[java.sql.Timestamp] = column[java.sql.Timestamp]("created")
  }
  /** Collection-like TableQuery object for table SmsLogs */
  lazy val SmsLogs = new TableQuery(tag => new SmsLogs(tag))
}
