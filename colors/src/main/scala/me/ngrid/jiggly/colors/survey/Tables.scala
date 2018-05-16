package me.ngrid.jiggly.colors.survey
// AUTO-GENERATED Slick data model
/** Stand-alone Slick data model for immediate use */
object Tables extends {
  val profile = slick.jdbc.SQLiteProfile
} with Tables

/** Slick data model trait for extension, choice of backend or usage in the cake pattern. (Make sure to initialize this late.) */
trait Tables {
  val profile: slick.jdbc.JdbcProfile
  import profile.api._
  import slick.model.ForeignKeyAction
  // NOTE: GetResult mappers for plain SQL are only generated for tables where Slick knows how to map the types of all columns.
  import slick.jdbc.{GetResult => GR}

  /** DDL for all tables. Call .create to execute. */
  lazy val schema: profile.SchemaDescription = Answers.schema ++ Names.schema ++ Users.schema
  @deprecated("Use .schema instead of .ddl", "3.0")
  def ddl = schema

  /** Entity class storing rows of table Answers
   *  @param id Database column id SqlType(INTEGER), PrimaryKey
   *  @param userId Database column user_id SqlType(TEXT)
   *  @param datestamp Database column datestamp SqlType(TEXT)
   *  @param r Database column r SqlType(TEXT)
   *  @param g Database column g SqlType(TEXT)
   *  @param b Database column b SqlType(TEXT)
   *  @param colorname Database column colorname SqlType(TEXT) */
  case class AnswersRow(id: Option[Int], userId: Option[String], datestamp: Option[String], r: Option[String], g: Option[String], b: Option[String], colorname: Option[String])
  /** GetResult implicit for fetching AnswersRow objects using plain SQL queries */
  implicit def GetResultAnswersRow(implicit e0: GR[Option[Int]], e1: GR[Option[String]]): GR[AnswersRow] = GR{
    prs => import prs._
    AnswersRow.tupled((<<?[Int], <<?[String], <<?[String], <<?[String], <<?[String], <<?[String], <<?[String]))
  }
  /** Table description of table answers. Objects of this class serve as prototypes for rows in queries. */
  class Answers(_tableTag: Tag) extends profile.api.Table[AnswersRow](_tableTag, "answers") {
    def * = (id, userId, datestamp, r, g, b, colorname) <> (AnswersRow.tupled, AnswersRow.unapply)

    /** Database column id SqlType(INTEGER), PrimaryKey */
    val id: Rep[Option[Int]] = column[Option[Int]]("id", O.PrimaryKey)
    /** Database column user_id SqlType(TEXT) */
    val userId: Rep[Option[String]] = column[Option[String]]("user_id")
    /** Database column datestamp SqlType(TEXT) */
    val datestamp: Rep[Option[String]] = column[Option[String]]("datestamp")
    /** Database column r SqlType(TEXT) */
    val r: Rep[Option[String]] = column[Option[String]]("r")
    /** Database column g SqlType(TEXT) */
    val g: Rep[Option[String]] = column[Option[String]]("g")
    /** Database column b SqlType(TEXT) */
    val b: Rep[Option[String]] = column[Option[String]]("b")
    /** Database column colorname SqlType(TEXT) */
    val colorname: Rep[Option[String]] = column[Option[String]]("colorname")

    /** Index over (b) (database name bval) */
    val index1 = index("bval", b)
    /** Index over (colorname) (database name colorname) */
    val index2 = index("colorname", colorname)
    /** Index over (g) (database name gval) */
    val index3 = index("gval", g)
    /** Index over (r) (database name rval) */
    val index4 = index("rval", r)
    /** Index over (userId) (database name whichuser) */
    val index5 = index("whichuser", userId)
  }
  /** Collection-like TableQuery object for table Answers */
  lazy val Answers = new TableQuery(tag => new Answers(tag))

  /** Entity class storing rows of table Names
   *  @param id Database column id SqlType(INTEGER), PrimaryKey
   *  @param colorname Database column colorname SqlType(TEXT)
   *  @param numusers Database column numusers SqlType(TEXT)
   *  @param numinstances Database column numinstances SqlType(TEXT) */
  case class NamesRow(id: Option[Int], colorname: Option[String], numusers: Option[String], numinstances: Option[String])
  /** GetResult implicit for fetching NamesRow objects using plain SQL queries */
  implicit def GetResultNamesRow(implicit e0: GR[Option[Int]], e1: GR[Option[String]]): GR[NamesRow] = GR{
    prs => import prs._
    NamesRow.tupled((<<?[Int], <<?[String], <<?[String], <<?[String]))
  }
  /** Table description of table names. Objects of this class serve as prototypes for rows in queries. */
  class Names(_tableTag: Tag) extends profile.api.Table[NamesRow](_tableTag, "names") {
    def * = (id, colorname, numusers, numinstances) <> (NamesRow.tupled, NamesRow.unapply)

    /** Database column id SqlType(INTEGER), PrimaryKey */
    val id: Rep[Option[Int]] = column[Option[Int]]("id", O.PrimaryKey)
    /** Database column colorname SqlType(TEXT) */
    val colorname: Rep[Option[String]] = column[Option[String]]("colorname")
    /** Database column numusers SqlType(TEXT) */
    val numusers: Rep[Option[String]] = column[Option[String]]("numusers")
    /** Database column numinstances SqlType(TEXT) */
    val numinstances: Rep[Option[String]] = column[Option[String]]("numinstances")

    /** Index over (colorname) (database name names_colorname) */
    val index1 = index("names_colorname", colorname)
    /** Index over (numusers) (database name names_numusers) */
    val index2 = index("names_numusers", numusers)
  }
  /** Collection-like TableQuery object for table Names */
  lazy val Names = new TableQuery(tag => new Names(tag))

  /** Entity class storing rows of table Users
   *  @param id Database column id SqlType(INTEGER), PrimaryKey
   *  @param userKey Database column user_key SqlType(TEXT)
   *  @param datestamp Database column datestamp SqlType(TEXT)
   *  @param ip Database column ip SqlType(TEXT)
   *  @param language Database column language SqlType(TEXT)
   *  @param monitor Database column monitor SqlType(TEXT)
   *  @param temperature Database column temperature SqlType(TEXT)
   *  @param gamma Database column gamma SqlType(TEXT)
   *  @param colorblind Database column colorblind SqlType(TEXT)
   *  @param ychrom Database column ychrom SqlType(TEXT)
   *  @param samplecolors Database column samplecolors SqlType(TEXT)
   *  @param spamprob Database column spamprob SqlType(TEXT) */
  case class UsersRow(id: Option[Int], userKey: Option[String], datestamp: Option[String], ip: Option[String], language: Option[String], monitor: Option[String], temperature: Option[String], gamma: Option[String], colorblind: Option[String], ychrom: Option[String], samplecolors: Option[String], spamprob: Option[String])
  /** GetResult implicit for fetching UsersRow objects using plain SQL queries */
  implicit def GetResultUsersRow(implicit e0: GR[Option[Int]], e1: GR[Option[String]]): GR[UsersRow] = GR{
    prs => import prs._
    UsersRow.tupled((<<?[Int], <<?[String], <<?[String], <<?[String], <<?[String], <<?[String], <<?[String], <<?[String], <<?[String], <<?[String], <<?[String], <<?[String]))
  }
  /** Table description of table users. Objects of this class serve as prototypes for rows in queries. */
  class Users(_tableTag: Tag) extends profile.api.Table[UsersRow](_tableTag, "users") {
    def * = (id, userKey, datestamp, ip, language, monitor, temperature, gamma, colorblind, ychrom, samplecolors, spamprob) <> (UsersRow.tupled, UsersRow.unapply)

    /** Database column id SqlType(INTEGER), PrimaryKey */
    val id: Rep[Option[Int]] = column[Option[Int]]("id", O.PrimaryKey)
    /** Database column user_key SqlType(TEXT) */
    val userKey: Rep[Option[String]] = column[Option[String]]("user_key")
    /** Database column datestamp SqlType(TEXT) */
    val datestamp: Rep[Option[String]] = column[Option[String]]("datestamp")
    /** Database column ip SqlType(TEXT) */
    val ip: Rep[Option[String]] = column[Option[String]]("ip")
    /** Database column language SqlType(TEXT) */
    val language: Rep[Option[String]] = column[Option[String]]("language")
    /** Database column monitor SqlType(TEXT) */
    val monitor: Rep[Option[String]] = column[Option[String]]("monitor")
    /** Database column temperature SqlType(TEXT) */
    val temperature: Rep[Option[String]] = column[Option[String]]("temperature")
    /** Database column gamma SqlType(TEXT) */
    val gamma: Rep[Option[String]] = column[Option[String]]("gamma")
    /** Database column colorblind SqlType(TEXT) */
    val colorblind: Rep[Option[String]] = column[Option[String]]("colorblind")
    /** Database column ychrom SqlType(TEXT) */
    val ychrom: Rep[Option[String]] = column[Option[String]]("ychrom")
    /** Database column samplecolors SqlType(TEXT) */
    val samplecolors: Rep[Option[String]] = column[Option[String]]("samplecolors")
    /** Database column spamprob SqlType(TEXT) */
    val spamprob: Rep[Option[String]] = column[Option[String]]("spamprob")
  }
  /** Collection-like TableQuery object for table Users */
  lazy val Users = new TableQuery(tag => new Users(tag))
}
