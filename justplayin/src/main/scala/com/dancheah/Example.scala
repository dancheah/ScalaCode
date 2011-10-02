package com.dancheah

import unfiltered.request._
import unfiltered.response._

import net.liftweb.json.JsonDSL._
import net.liftweb.json.JsonParser._

import org.clapper.avsl.Logger

/** unfiltered plan */
class App extends unfiltered.filter.Plan {
  //import QParams.{lookup,int,required}
  import QParams._

  val logger = Logger(classOf[App])

  def intent = {
    case GET(Path("/form")) =>
      logger.debug("GET /form")
      Ok ~> view(Map.empty)(<p> What say you brown cow? </p>)
    case POST(Path("/form") & Params(params)) =>
      logger.debug("POST /form")
      val vw = view(params)_
      val expected = for {
        int <- lookup("int") is
          int { _ + " is not an integer" } is
          required("missing int")
        word <- lookup("palindrome") is
          trimmed is
          nonempty("Palindrome is empty") is
          pred(palindrome, { _ + " is not a palindrome" }) is
          required("missing palindrome")
      } yield vw(<p>Yup. { int.get } is an integer and { word.get } is a palindrome. </p>)
      expected(params) orFail { fails =>
        vw(<ul> { fails.map { f => <li>{f.error} </li> } } </ul>)
      }
    case GET(Path("/json")) =>
      Json("this is a json string")
    case _ =>
      ResponseString("Hello Default Case")
  }
  def palindrome(s: String) = s.toLowerCase.reverse == s.toLowerCase
  def view(params: Map[String, Seq[String]])(body: scala.xml.NodeSeq) = {
    def p(k: String) = params.get(k).flatMap { _.headOption } getOrElse("")
    Html(
     <html>
      <head>
        <title>uf example</title>
        <link rel="stylesheet" type="text/css" href="/assets/css/app.css"/>
      </head>
      <body>
       <div id="container">
       { body }
       <form method="POST">
         <div>Integer <input type="text" name="int" value={ p("int") } /></div>
         <div>Palindrome <input type="text" name="palindrome" value={ p("palindrome") } /></div>
         <input type="submit" />
       </form>
       </div>
     </body>
    </html>
   )
  }
}

/** embedded server */
object Server {
  val logger = Logger(Server.getClass)
  def main(args: Array[String]) {
    val http = unfiltered.jetty.Http.anylocal // this will not be necessary in 0.4.0
    http.context("/assets") { _.resources(new java.net.URL(getClass().getResource("/www/css"), ".")) }
      .filter(new App).run({ svr =>
        unfiltered.util.Browser.open(http.url)
      }, { svr =>
        logger.info("shutting down server")
      })
  }
}
