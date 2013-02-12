package net.whiteants

import pro.savant.circumflex._, core._, web._, freemarker._
import java.util.GregorianCalendar


class BookRouter extends Router {
  def userOption = {
    session.get("principal") match {       //принадлежность к класу
      case Some(u: User) => Some(u)
      case _ => None
    }
  }

  val currentUser = userOption.get
  //новая книга
  get("/?") = {
    'contact := Book.findByUser(currentUser)
    ftl("/addressbook/list.ftl")
  }
  //создание книги
  post("/?") = {
    try {
      val b = new Book

      b.title := param("t")
      b.address := param("a")
      b.phoneNumber := param("p")
      b.comments := param("c")
      b.birthDate := new GregorianCalendar(param("y").toInt,param("m").toInt,param("d").toInt).getTime

      b.user := currentUser
      b.save()
    } catch {
      //условие исключеия
      case e: ValidationException =>
        flash.update("error", e.errors)
        sendRedirect("/book/~new.ftl")
    }
    //новая адресная книга добавлена
    flash.update("msg", msg.fmt("user.add.book"))
    sendRedirect("/book")
  }

  get("/~new") = ftl("/addressbook/new.ftl")
  get("/error") = ftl("/error/error.ftl")
  sub("/:id") = {                                                   //передача прификса слудующим за ним get/post
  val contact =
    Book.get(param("id").toLong).getOrElse(sendError(404))
    'contact := contact

    if (contact.user() != currentUser)
      sendError(404)                                                //нувозможно найти данные, согласно запросу

    //просмотр
    get("/?") = ftl("/addressbook/view.ftl")
    //редактирование
    get("/~edit") = ftl("/addressbook/edit.ftl")

    post("/~edit") = {
      try {

        contact.title := param("t")
        contact.address := param("a")
        contact.phoneNumber := param("p")
        contact.comments := param("c")
        contact.birthDate := new GregorianCalendar(param("y").toInt,param("m").toInt,param("d").toInt).getTime

        contact.save()
      } catch {
        case e: ValidationException =>
          flash.update("error", e.errors)
          sendRedirect("/book/~edit")
      }
      flash.update("msg", msg.fmt("user.book.edit"))
      sendRedirect("/book")
    }
    //удаление
    get("/~delete").and(request.isXHR) = ftl("/addressbook/delete.ftl")

    post("/~delete") = {
      contact.DELETE_!()
      flash.update("msg", msg.fmt("user.book.delete"))
      sendRedirect("/book")
    }
  }
}
