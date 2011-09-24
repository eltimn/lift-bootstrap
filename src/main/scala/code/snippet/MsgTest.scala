package code
package snippet

import net.liftweb._
import http.{S, SHtml}
import util.Helpers._

object MsgTest {
  def render = {
    var error = "this is an error"
    var error2 = "this is error 2"
    var warning = "this is a warning"
    var info = "This is some info"

    "name=error" #> SHtml.text(error, error = _) &
    "name=error2" #> SHtml.text(error2, error2 = _) &
    "name=warning" #> SHtml.text(warning, warning = _) &
    "name=info" #> SHtml.text(info, info = _) &
    "type=submit" #> SHtml.submit("Submit", () => {
      if (error.length > 0)
        S.error(error)
      if (error2.length > 0)
        S.error(error2)
      if (warning.length > 0)
        S.warning(warning)
      if (info.length > 0)
        S.notice(info)
        
      //S.redirectTo("/")
    })
  }
}
