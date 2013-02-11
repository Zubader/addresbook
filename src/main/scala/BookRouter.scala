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

      b.title := param("t")
      b.address := param("a")
      b.phoneNumber := param("p")
      b.birthDate := param("d")
      b.comments := param("c")

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