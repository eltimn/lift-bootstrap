package code
package snippet

import lib.{Auth, Gravatar}

import scala.xml.NodeSeq

import org.apache.shiro.authc.UsernamePasswordToken

import net.liftweb._
import common._
import http.{S, SHtml}
import util._
import Helpers._

object UserTopbar {
  def render = {
    Auth.currentUser match {
      case Full(user) =>
        //<img src={Gravatar.imageUrl(user.email.is, 13)}></img>
        //<span class="username">{user.username.is}</span>
        <ul class="nav secondary-nav"> 
          <li class="dropdown" data-dropdown="dropdown">
            <a href="#" class="dropdown-toggle">
              <!--<img src={Gravatar.imageUrl(user.email.is, 13)}></img>-->
              <span class="username">{user.username.is}</span>
            </a> 
            <ul class="dropdown-menu"> 
              <li><span lift="Menu.item?name=Settings;donthide=true;linktoself=true">Settings</span></li> 
              <li><span lift="Menu.item?name=About;donthide=true;linktoself=true">Help</span></li> 
              <li class="divider"></li> 
              <li><a href="/logout">Log Out</a></li> 
            </ul> 
          </li> 
        </ul>
      case _ if (S.request.flatMap(_.location).map(_.name) != "Login") =>
        <form action="/login" style="float: right">
          <button class="btn">Sign In</button>
        </form>
      case _ => NodeSeq.Empty
    }
  }
}

object UserLogin extends shiro.SubjectLifeCycle {
  def render = {
    var username = ""
    var password = ""
    var remember = false

    "name=username" #> SHtml.text(username, username = _) &
    "name=password" #> SHtml.password(password, password = _) &
    "name=remember" #> SHtml.checkbox(remember, remember = _) &
    "type=submit" #> SHtml.submit("Sign In", () => 
      login(new UsernamePasswordToken(username, password, remember)))
  }
}