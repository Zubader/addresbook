package net.whiteants

import pro.savant.circumflex._, core._, web._, freemarker._
import java.util.Date


class Main extends Router {
  val log = new Logger("net.whiteants")

  'currentDate := new Date

  get("/?") = ftl("/index.ftl")

  sub("/auth") = new AuthRouter

  rewrite("/") = prefix + "/contacts"             //вложеие моршрута в друг друга
  sub("/contacts") = new ContactsRouter

}

