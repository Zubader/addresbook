package net.whiteants

import pro.savant.circumflex._, core._, web._, freemarker._
import java.util.GregorianCalendar


class ContactsRouter extends Router {
  def userOption = {
    session.get("principal") match {       //принадлежность к класу
      case Some(u: User) => Some(u)
      case _ => None
    }
  }

  val currentUser = userOption.get
  //новая книга
  get("/?") = {
    'contact := Contacts.findByUser(currentUser)
    ftl("/addressbook/list.ftl")
  }
  //создание книги
  post("/?") = {
    try {
      val cs = new Contacts

      cs.title := param("t")
      cs.address := param("a")
      cs.phoneNumber := param("p")
      cs.comments := param("c")
      cs.birthDate := new GregorianCalendar(param("y").toInt,param("m").toInt,param("d").toInt).getTime

      cs.user := currentUser
      cs.save()
    } catch {
      //условие исключеия
      case e: ValidationException =>
        flash.update("error", e.errors)
        sendRedirect("/contacts/~new")
    }
    //новая адресная книга добавлена
    flash.update("msg", msg.fmt("user.add.addressbook"))
    sendRedirect("/contacts")
  }

  get("/~new") = ftl("/addressbook/new.ftl")

  get("/error") = ftl("/error/error.ftl")

  sub("/:id") = {                                                   //передача прификса слудующим за ним get/post
    val contact = Contacts.get(param("id").toLong).getOrElse(sendError(404))
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
          sendRedirect("/contacts/~edit")
      }
      flash.update("msg", msg.fmt("user.contact.edit"))
      sendRedirect("/contacts")
    }
    //удаление
    get("/~delete").and(request.isXHR) = ftl("/addressbook/delete.ftl")

    post("/~delete") = {
      contact.DELETE_!()
      flash.update("msg", msg.fmt("user.contact.delete"))
      sendRedirect("/contacts")
    }
  }
}
