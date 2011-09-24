package code
package lib

import scala.xml._
import net.liftweb._
import common._

trait SnippetHelpers {

  def handleError(msg: String): NodeSeq = Comment("ERROR: %s".format(msg))
  def handleError(e: Throwable): NodeSeq = handleError(e.getMessage)

  /*
  * Allows for the following to be used when building snippets and it will handle 
  * errors according to handleError:
  *
  *   for {
  *     user <- User.currentUser ?~ "You must be logged in to edit your profile."
  *   } yield ({
  *   ...
  *   }): NodeSeq
  */
  implicit def boxToNodeSeq(in: Box[NodeSeq]): NodeSeq = in match {
    case Full(ns) => ns
    case Failure(msg, _, _) => handleError(msg)
    case Empty => handleError("Invalid Request")
  }
}
