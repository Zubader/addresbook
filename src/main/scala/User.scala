package net.whiteants

import pro.savant.circumflex._, core._, orm._
import java.util.Date


class User
    extends Record[Long, User]
    with IdentityGenerator[Long,User] {

  def PRIMARY_KEY = id
  def relation = User

  val id = "id".BIGINT.NOT_NULL.AUTO_INCREMENT
  val name = "name".TEXT
  val email = "email".TEXT.NOT_NULL
  val passwordSha256 = "passwordSha256".TEXT.NOT_NULL
  val registrationDate = "registration_date".TIMESTAMP.NOT_NULL(new Date)

  def contact = inverseMany(Contact.user)       //обратная связь
}

object User
    extends User
    with Table[Long, User] {

  val emailUnique = UNIQUE(email)

  validation
      .unique(_.email)
      .pattern(_.email, "\\w+@\\w+\\.\\w+".r.pattern)


  private val u = User AS "u"

  def sort =
    SELECT(u.*)
        .FROM(u)
        .ORDER_BY(u.email ASC)
        .list()


  def findByEmail(email: String, passwordSha256: String) = {
    SELECT(u.*)
        .FROM(u)
        .add(u.email EQ email)
        .add(u.passwordSha256 EQ passwordSha256)
        .unique()
  }
}
