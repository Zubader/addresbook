package net.whiteants

/**
 * Created with IntelliJ IDEA.
 * User: zubader
 * Date: 2/11/13
 * Time: 9:08 AM
 * To change this template use File | Settings | File Templates.
 */

import pro.savant.circumflex._, core._, web._

class AuthRouter extends Router {
  //блок входа
  get("/login") = ftl("/auth/login.ftl")

  post("/login") = {
    val email = param("email")
    val password = param("passwordSha246")
    val user = User.findByEmail(email, password)
    if (User.isEmpty) {
      //не удачно
      flash.update("msg", msg.fmt(user.login.failed) )
      sendRedirect("/auth/login")
    } else {
      //удачно
      flash.update("msg", msq.fmt(user.login.managed))
      request.session.update("principal")
      sendRedirect("/")
    }
  }
  //блок выхода
  get("logout") = {
    request.session.remove("principal")
    sendRedirect("/")
  }
}
