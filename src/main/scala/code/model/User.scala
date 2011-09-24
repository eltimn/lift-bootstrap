package code
package model

import org.bson.types.ObjectId

import net.liftweb._
import common._
import mongodb.record.field._

import com.eltimn.auth.mongo.{ProtoAuthUser, ProtoAuthUserMeta}

class User private () extends ProtoAuthUser[User] with ObjectIdPk[User] {
  def meta = User

  def isRegistered: Boolean = !id.is.isNew
}

object User extends User with ProtoAuthUserMeta[User, ObjectId] {
  import mongodb.BsonDSL._

  override def collectionName = "user.users"

  ensureIndex((email.name -> 1), true)
  ensureIndex((username.name -> 1), true)

  def findPasswordForUser(login: String): Box[(ObjectId, String)] =
    findPasswordForUser(login, email.name, password.name) or findPasswordForUser(login, username.name, password.name)
  
  def createUser(username: String, email: String, password: String, permissions: List[String]): Box[User] = {
    val newUser = createRecord
      .username(username)
      .email(email)
      .password(password, true)
      .permissions(permissions)
      .save

    Full(newUser)
  }
}