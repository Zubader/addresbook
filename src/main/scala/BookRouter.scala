package net.whiteants

import pro.savant.circumflex._, core._, web._, freemarker._


class BookRouter extends Router {
  //новая книга
  get("/") = {
    'contacts = Book.findByUser(currentUser)
    ftl("/addressbook/list.ftl")
  }
  //создание книги
  post("/") = {
    try {
      val b = new Book

      b.title := param("title")
      b.address := param("address")
      b.phoneNumber := param("phone_number")
      b.birthDay := param("birth_day")
      b.comments := param("comments")

      b.user := currentUser
      b.save()
    } catch {
      //условие исключения
      case e: ValidationException =>
        flash.update("error", e.error)
        sendRedirect("/book/~new.ftl")
    }
    //новая адресная книга создана
    flash.update("msg", msg.fmt("user.addressbook"))
    sendRedirect("/book")
  }
  get ("/error") = ftl("/error/error.ftl")

}