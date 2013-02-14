package net.whiteants

import pro.savant.circumflex._, core._, web._, freemarker._


class AuthRouter extends Router {
  //блок входа
  get("/login") = ftl("/auth/login.ftl")

  post("/login") = {
    val email = param("e")
    val password = sha256(param("p"))
    val user = User.findByEmail(email, password).getOrElse {
      //не удачно
      flash.update("msg", msg.fmt("user.login.failed") )
      sendRedirect("/auth/login")
    }
    //удачно
    flash.update("msg", msg.fmt("user.login.managed"))
    session.update("principal", user)
    sendRedirect("/")
  }

  //блок выхода
  get("/logout") = {
    session.remove("principal")
    sendRedirect("/")
  }

  //зарегистрироваться
  get("/signup") = ftl("/auth/signup.ftl")

  post("/signup") = {
    try {
      val u = new User

      u.email := param("e")
      u.name := param("n")
      u.passwordSha256 := sha256(param("p"))

      u.save()
    } catch {
      //условия исключения
      case e: ValidationException =>
        flash.update("errors", e.errors)
        sendRedirect("/auth/signup")
    }
    //новый пользователь добавлен
    flash.update("msg", msg.fmt("user.registered"))
    sendRedirect("/")
  }
}
