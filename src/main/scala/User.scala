package net.whiteants

import pro.savant.circumflex._, core._, orm._

class User
    extends Record[Long, User]
    with IdentityGenerator[Long,User] {       //Создание строки User с полем Name

  def PRIMARY_KEY = id
  def relation = User

  val id = "id".BIGINT.NOT_NULL.AUTO_INCREMENT
  val fullName = "full_name".TEXT
  val email = "email".TEXT.NOT_NULL.UNIQUE
  val passwordSha256 = "password".TEXT.NOT_NULL

  def contacts = inverseMany(Contact.User)           //обратная связь
}

object User
    extends User
    with Table[Long, User] {

  val emailUnique = UNIQUE(email)

  validation
      .unique(_.email)
      .pattern(_.email, "([-0-9a-zA-Z.@)")

  val eu = User AS(email)


}

class Contact
    extends Record[Long, Contact]
    with IdentityGenerator[Long, Contact] {

  def PRIMARY_KEY = id
  def relation = Contact

  val id = "id".BIGINT.NOT_NULL.AUTO_INCREMENT  //автоопределение id
  val title = "title".TEXT.NOT_NULL
  val birthday = "birthday".DATE
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

  validation
      .unique(_.phoneNumber)
      .notEmpty(_.title)
      .notEmpty(_.address)
      .pattern(_.phoneNumber, "(\d{2}-(\d{4})-\d{5})")

}
