package net.whiteants

import pro.savant.circumflex._, orm._


class Contacts
    extends Record[Long, Contacts]
    with IdentityGenerator[Long, Contacts] {

  def PRIMARY_KEY = id
  def relation = Contacts

  val id = "id".BIGINT.NOT_NULL.AUTO_INCREMENT  //автоопределение id
  val title = "title".TEXT.NOT_NULL
  val birthDate = "birth_date".TIMESTAMP
  val address = "address".TEXT.NOT_NULL
  val phoneNumber = "phone_number".TEXT.NOT_NULL
  val comments = "comments".TEXT
  val user = "user_id".BIGINT.NOT_NULL
      .REFERENCES(User)                 //отношение
      .ON_DELETE(CASCADE)
      .ON_UPDATE(CASCADE)
}

object Contacts
    extends Contacts
    with Table[Long, Contacts] {

  val pnUnique = UNIQUE(phoneNumber)

  validation
      .unique(_.phoneNumber)
      .notEmpty(_.title)
      .notEmpty(_.address)
      .notEmpty(_.phoneNumber)
      .pattern(_.phoneNumber, "\\d{1,3}-\\d{3}-\\d{3}-\\d{4}".r.pattern)

  private val cs = Contacts AS "cs"

  def sort =
    SELECT(cs.*)
        .FROM(cs)
        .ORDER_BY(cs.title ASC)            //сортировка
        .list()

  def count = {                     //сколько контактов относиться к пользователю
  val u = User AS "u"
    SELECT(u.* -> COUNT(cs.*))
        .FROM(cs LEFT_JOIN u)
        .GROUP_BY(u.id)
        .list()
  }

  def findByUser(user: User) = {
    SELECT(cs.*)
        .FROM(cs)
        .add(cs.user IS  user)
        .list()
  }
}

