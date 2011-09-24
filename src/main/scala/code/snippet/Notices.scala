package code
package snippet

import scala.xml.{NodeSeq, Text}

import net.liftweb._
import common._
import http.{Factory, NoticeType, S, SHtml}
import util.Helpers._

/*
 * Emit non-id notices to jquery.notices plugin.
 
 jQuery.noticeAdd({
27
            text: 'This is an error notification!',
28
            stay: false,
29
            type: 'error'
30
        });
        
 */
object JQueryNotices extends Factory {
  /*
   * Config
   */
  val errorTitle = new FactoryMaker[Box[String]](Empty){}
  val warningTitle = new FactoryMaker[Box[String]](Empty){}
  val noticeTitle = new FactoryMaker[Box[String]](Empty){}

  def render = {
    val notices = S.noIdMessages _
    // Compute the formatted set of messages for a given input
    def computeMessageDiv(args: (List[(NodeSeq, Box[String])], NoticeType.Value)): NodeSeq = args match {
      case (messages, noticeType) =>
        // Compute the resulting div
        notices(messages).toList match {
          case msgs if (msgs.length == 0) => Nil
          case msgs if (msgs.length == 1) =>
            <div id={noticeType.id} class={"alert-message %s".format(lowerCaseTitle(noticeType))} data-alert="">
              <a class="close" href="#">&times;</a>
              <p>{noticeTitle(noticeType).map(t => <strong>{t}</strong>).openOr(Text(""))} {msgs(0)}</p>
            </div>
          case msgs =>
            <div id={noticeType.id} class={"alert-message %s".format(lowerCaseTitle(noticeType))} data-alert="">
              {noticeTitle(noticeType).map(t => <strong>{t}</strong>).openOr(Text(""))}
              <a class="close" href="#">&times;</a>
              { msgs.flatMap(e => { <p>{e}</p> }) }
            </div>
        }
    }

    // Render all three types together
    List((S.errors, NoticeType.Error),
         (S.warnings, NoticeType.Warning),
         (S.notices, NoticeType.Notice)).flatMap(computeMessageDiv)
  }
  
  def lowerCaseTitle(noticeType: NoticeType.Value): String = noticeType match {
    case NoticeType.Notice => "info"
    case _ => noticeType.lowerCaseTitle
  }

  def noticeTitle(noticeType: NoticeType.Value): Box[String] = noticeType match {
    case NoticeType.Notice => noticeTitle.vend
    case NoticeType.Error => errorTitle.vend
    case NoticeType.Warning => warningTitle.vend
  }
}

/*
 * Emit non-id notices to Bootstrap's Alerts system.
 */

/*
<!-- tmpl -->
<div class="alert-message">
  <a class="close" href="#">&times;</a>
  <p><strong><span id="id_title"></span></strong> <span id="id_text"></span></p>
</div>

add class (warning, error, info (success)) to outer div
replace id_title span with Text of title
replace id_text span with notice content

<div class="alert-message warning">
  <a class="close" href="#">&times;</a>
  <p><strong>Holy guacamole!</strong> Best check yo self, you’re not looking too good.</p>
</div>
<div class="alert-message error">
  <a class="close" href="#">&times;</a>
  <p><strong>Oh snap!</strong> Change this and that and try again.</p>
</div>
<div class="alert-message success">
  <a class="close" href="#">&times;</a>
  <p><strong>Well done!</strong> You successfully read this alert message.</p>
</div>
<div class="alert-message info">
  <a class="close" href="#">&times;</a>
  <p><strong>Heads up!</strong> This is an alert that needs your attention, but it’s not a huge priority just yet.</p>
</div>
<div class="alert-message block-message warning"> 
  <a class="close" href="#">&times;</a> 
  <p><strong>Holy guacamole! This is a warning!</strong> Best check yo self, you’re not looking too good. Nulla vitae elit libero, a pharetra augue. Praesent commodo cursus magna, vel scelerisque nisl consectetur et.</p> 
  <div class="alert-actions"> 
    <a class="btn small" href="#">Take this action</a> <a class="btn small" href="#">Or do this</a> 
  </div> 
</div> 
*/
object BootstrapAlerts extends Factory {
  /*
   * Config
   */
  val errorTitle = new FactoryMaker[Box[String]](Empty){}
  val warningTitle = new FactoryMaker[Box[String]](Empty){}
  val noticeTitle = new FactoryMaker[Box[String]](Empty){}

  def render = {
    val notices = S.noIdMessages _
    // Compute the formatted set of messages for a given input
    def computeMessageDiv(args: (List[(NodeSeq, Box[String])], NoticeType.Value)): NodeSeq = args match {
      case (messages, noticeType) =>
        // Compute the resulting div
        notices(messages).toList match {
          case msgs if (msgs.length == 0) => Nil
          case msgs if (msgs.length == 1) =>
            <div id={noticeType.id} class={"alert-message %s".format(lowerCaseTitle(noticeType))} data-alert="">
              <a class="close" href="#">&times;</a>
              <p>{noticeTitle(noticeType).map(t => <strong>{t}</strong>).openOr(Text(""))} {msgs(0)}</p>
            </div>
          case msgs =>
            <div id={noticeType.id} class={"alert-message %s".format(lowerCaseTitle(noticeType))} data-alert="">
              {noticeTitle(noticeType).map(t => <strong>{t}</strong>).openOr(Text(""))}
              <a class="close" href="#">&times;</a>
              { msgs.flatMap(e => { <p>{e}</p> }) }
            </div>
        }
    }

    // Render all three types together
    List((S.errors, NoticeType.Error),
         (S.warnings, NoticeType.Warning),
         (S.notices, NoticeType.Notice)).flatMap(computeMessageDiv)
  }

  def lowerCaseTitle(noticeType: NoticeType.Value): String = noticeType match {
    case NoticeType.Notice => "info"
    case _ => noticeType.lowerCaseTitle
  }

  def noticeTitle(noticeType: NoticeType.Value): Box[String] = noticeType match {
    case NoticeType.Notice => noticeTitle.vend
    case NoticeType.Error => errorTitle.vend
    case NoticeType.Warning => warningTitle.vend
  }
}
