package code
package lib

import model.User

import org.bson.types.ObjectId

import net.liftweb.common._

/* Interface for handling authentication and authorization */
object Auth {
  def currentUserId: Box[ObjectId] = User.currentUserId
  def currentUser: Box[User] = User.currentUser
}