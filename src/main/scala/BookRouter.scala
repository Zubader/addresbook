package net.whiteants

import pro.savant.circumflex._, core._, web._, freemarker._


class BookRouter extends Router {
  //новая книга
  get("/") = {
    val contacts = Book.findByUser(User)
    ftl("/addressbook/list.ftl")
  }
  //создание книги
  post("/") = {
    try {
      val b = new Book

      b.title := param("t")
      b.address := param("a")
      b.phoneNumber := param("p")
      b.comments := param("c")

      b.user := User
      b.save()
    } catch {
      //условие исключения
      case e: ValidationException =>
        flash.update("error", e.errors)
        sendRedirect("/book/~new.ftl")
    }
    //новая адресная книга создана
    flash.update("msg", msg.fmt("user.addressbook"))
    sendRedirect("/book")
  }
}