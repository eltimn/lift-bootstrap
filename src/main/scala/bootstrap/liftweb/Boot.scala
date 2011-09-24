package bootstrap.liftweb

import net.liftweb._
import common._
import http._
import sitemap._
import sitemap.Loc._
import util._
import util.Helpers._

import code.lib.Gravatar
import code.model._
import code.snippet.BootstrapAlerts

import com.eltimn.auth.mongo._

import shiro.Shiro
import shiro.sitemap.Locs._

/**
 * A class that's instantiated early and run.  It allows the application
 * to modify lift's environment
 */
class Boot extends Loggable {
  def boot {
    logger.info("Run Mode: "+Props.mode.toString)

    // init mongodb
    MongoConfig.init()

    // init auth-mongo
    AuthRules.authUserMeta.default.set(User)

    // init shiro
    Shiro.init()

    // gravatar settings
    Gravatar.defaultImage.default.set("retro")


    // where to search snippet
    LiftRules.addToPackages("code")

    // set the default htmlProperties
    LiftRules.htmlProperties.default.set((r: Req) => new Html5Properties(r.userAgent))

    // Build SiteMap
    val TopbarGroup = LocGroup("topbar")
    LiftRules.setSiteMap(SiteMap(List(
      Menu.i("Home") / "index" >> TopbarGroup,
      Menu.i("About") / "about" >> TopbarGroup,
      Menu.i("Contact") / "contact" >> TopbarGroup,
      Menu.i("MsgTest") / "msgtest" >> TopbarGroup,
      Menu.i("Login") / "login" >> RequireNoAuthentication
    ) ::: Shiro.menus: _*))

    //Show the spinny image when an Ajax call starts
    LiftRules.ajaxStart =
      Full(() => LiftRules.jsArtifacts.show("ajax-loader").cmd)

    // Make the spinny image go away when it ends
    LiftRules.ajaxEnd =
      Full(() => LiftRules.jsArtifacts.hide("ajax-loader").cmd)

    // Force the request to be UTF-8
    LiftRules.early.append(_.setCharacterEncoding("UTF-8"))
    
    // add titles to bootstrap alerts
    BootstrapAlerts.errorTitle.default.set(Full("Oh snap!"))
    BootstrapAlerts.warningTitle.default.set(Full("Holy guacamole!"))
    BootstrapAlerts.noticeTitle.default.set(Full("Heads up!"))
  }
}

object BootRules extends Factory {
  val userMeta = new FactoryMaker[ProtoAuthUserMeta[_, _]](SimpleUser){}
}