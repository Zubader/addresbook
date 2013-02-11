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
  get("/login") = ftl("/auth/login.ftl")


}
