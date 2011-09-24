package code
package snippet

import lib.{Auth, Gravatar}

import scala.xml.NodeSeq

import net.liftweb._
import common._
import http.{DispatchSnippet, S}
import util._
import Helpers._

object AvatarImage {
  def render = {
    val rating = S.attr("rating").openOr(Gravatar.defaultRating.vend)
    val size = S.attr("size").map(_.toInt).openOr(Gravatar.defaultSize.vend)
    val default = S.attr("default").openOr(Gravatar.defaultImage.vend)

    Auth.currentUser match {
      case Full(user) => "img [href]" #> Gravatar.imageUrl(user.email.is, size, rating, default)
      case _ if (default.length > 0) => "img [href]" #> default
      case _ => NodeSeq.Empty
    }
  }
}