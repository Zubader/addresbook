package net.whiteants

import pro.savant.circumflex._, core._, orm._


class Book
    extends Record[Long, Book]
    with IdentityGenerator[Long, Book] {

  def PRIMARY_KEY = id
  def relation = Book

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

object Book
    extends Book
    with Table[Long, Book] {

  val pnUnique = UNIQUE(phoneNumber)

  validation
      .unique(_.phoneNumber)
      .notEmpty(_.title)
      .notEmpty(_.address)
      .notEmpty(_.phoneNumber)
      .pattern(_.phoneNumber, "\\d{1,3}-\\d{3}-\\d{3}-\\d{4}".r.pattern)

  private val b = Book AS "b"

  def sort =
    SELECT(c.*)
        .FROM(b)
        .ORDER_BY(b.title ASC)            //сортировка
        .list()

  def count = {                     //сколько контактов относиться к пользователю
  val u = User AS "u"
    SELECT(u.* -> COUNT(b.*))
        .FROM(b LEFT_JOIN u)
        .GROUP_BY(u.id)
        .list()
  }

  def findByUser(user: User) = {
    SELECT(b.*)
        .FROM(b)
        .add(b.user IS  user)
        .list()
  }
}

