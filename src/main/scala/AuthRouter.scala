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
    if (user.isEmpty) {
      //не удачно
      flash.update("msg", msg.fmt("user.login.failed") )
      sendRedirect("/auth/login")
    } else {
      //удачно
      flash.update("msg", msq.fmt("user.login.managed"))
      request.session.update("principal")
      sendRedirect("/")
    }
  }
  //блок выхода
  get("/logout") = {
    request.session.remove("principal")
    sendRedirect("/")
  }

  //зарегистрироваться
  get("/signup") = ftl("/auth/signup.ftl")

  post("signup") = {
    try {
      val u = new User

      u.email := param("email")
      u.name := param("name")
      u.passwordSha256 := pro.savant.core.sha256("passwordSha256")

      u.save()
    } catch {
      //условия исключения
      case e: ValidationException =>
        flash.update("errors", e.errors)
        sendRedirect("/auth/signup.ftl")
    }
    //новый пользователь добавлен
    flash.update("msg", msg.fmt("user.new.added"))
    sendRedirect("/")
  }
}
