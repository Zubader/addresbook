package net.whiteants

import pro.savant.circumflex._, core._, orm._



class Contact
    extends Record[Long, Contact]
    with IdentityGenerator[Long, Contact] {

  def PRIMARY_KEY = id
  def relation = Contact

  val id = "id".BIGINT.NOT_NULL.AUTO_INCREMENT  //автоопределение id
  val title = "title".TEXT.NOT_NULL
  val birthDay = "birthday".TIMESTAMP
  val address = "address".TEXT.NOT_NULL
  val phoneNumber = "phone_number".TEXT.NOT_NULL
  val comments = "comments".TEXT
  val user = "user_id".BIGINT.NOT_NULL
      .REFERENCES(User)                 //отношение
      .ON_DELETE(CASCADE)
      .ON_UPDATE(CASCADE)
}

object Contact
    extends Contact
    with Table[Long, Contact] {

  val pnUnique = UNIQUE(phoneNumber)

  validation
      .unique(_.phoneNumber)
      .notEmpty(_.title)
      .notEmpty(_.address)
      .notEmpty(_.phoneNumber)
      .pattern(_.phoneNumber, "\\d{1,3}-\\d{3}-\\d{3}-\\d{4}".r.pattern)

  private val c = Contact AS "c"

  def sort =
    SELECT(c.*)
        .FROM(c)
        .ORDER_BY(c.title ASC)            //сортировка
        .list()

  def count = {                     //сколько контактов относиться к пользователю
  val u = User AS "u"
    SELECT(u.* -> COUNT(c.*))
        .FROM(c LEFT_JOIN u)
        .GROUP_BY(u.id)
        .list()
  }

  def findByUser(user: User) = {
    SELECT(c.*)
        .FROM(c)
        .add(c.user IS  user)
        .list()
  }
}

