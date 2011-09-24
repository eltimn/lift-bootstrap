package code
package snippet

import lib.SnippetHelpers
import model.User

import scala.xml.NodeSeq

import net.liftweb._
import common._
import sitemap.SiteMap
import util.Helpers._

class UserDebug {
  def render = {
    SiteMap.findAndTestLoc("Login")
    <div>
      User.currentUserId: {User.currentUserId.toString}<br />
      User.currentUser: {User.currentUser.toString}<br />
    </div>
  }
}