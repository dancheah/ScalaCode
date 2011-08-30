//package com.dancheah.collectiveintel

import scala.collection.mutable.HashSet
import net.liftweb.json._
import scala.math.sqrt
import scala.math.pow
import scala.collection.mutable.HashMap

/**
 * User: dc
 * Date: 8/27/11
 * Time: 11:35 PM
 */

object Recommendations {
  val raw_json = """
{
    "Jack Matthews": {
        "Lady in the Water": 3.0,
        "Snakes on a Plane": 4.0,
        "You, Me and Dupree": 3.5,
        "Superman Returns": 5.0,
        "The Night Listener": 3.0
    },
    "Mick LaSalle": {
        "Lady in the Water": 3.0,
        "Snakes on a Plane": 4.0,
        "Just My Luck": 2.0,
        "Superman Returns": 3.0,
        "You, Me and Dupree": 2.0,
        "The Night Listener": 3.0
    },
    "Claudia Puig": {
        "Snakes on a Plane": 3.5,
        "Just My Luck": 3.0,
        "You, Me and Dupree": 2.5,
        "Superman Returns": 4.0,
        "The Night Listener": 4.5
    },
    "Lisa Rose": {
        "Lady in the Water": 2.5,
        "Snakes on a Plane": 3.5,
        "Just My Luck": 3.0,
        "Superman Returns": 3.5,
        "The Night Listener": 3.0,
        "You, Me and Dupree": 2.5
    },
    "Toby": {
        "Snakes on a Plane": 4.5,
        "Superman Returns": 4.0,
        "You, Me and Dupree": 1.0
    },
    "Gene Seymour": {
        "Lady in the Water": 3.0,
        "Snakes on a Plane": 3.5,
        "Just My Luck": 1.5,
        "Superman Returns": 5.0,
        "You, Me and Dupree": 3.5,
        "The Night Listener": 3.0
    },
    "Michael Phillips": {
        "Lady in the Water": 2.5,
        "Snakes on a Plane": 3.0,
        "Superman Returns": 3.5,
        "The Night Listener": 4.0
    }
}
    """

  // Useful examples on using lift-json can be found here. It's different from the python api
  // https://github.com/lift/lift/tree/master/framework/lift-base/lift-json/src/test/scala/net/liftweb/json
  val critics = JsonParser.parse(raw_json)

  def sim_distance(prefs:JValue, person1:String, person2:String) = {
    // Get the list of shared items
    val l1 = for {
      JObject(list) <- prefs \ person1
      JField(key, JDouble(_)) <- list
    } yield key

    val l2 = for {
      JObject(list) <- prefs \ person2
      JField(key, JDouble(_)) <- list
    } yield key

    val l3 = l1 intersect l2
    
    val ret = if (l3.length == 0) {
      0
    } else {    
      val l = for {
          item <- l3
          JDouble(score1) <- prefs \ person1 \ item
          JDouble(score2) <- prefs \ person2 \ item
      } yield score1 - score2
    
      val sum_of_squares = l.map(pow(_, 2)).fold(0.0) {  _ + _ }
    
      1 / (1 + sum_of_squares)
    }
    
    ret
  }

  // Running in the repl
  /*
  scala> Recommendations.critics \ "Lisa Rose" \ "Lady in the Water"
res4: net.liftweb.json.JsonAST.JValue = JDouble(2.5)

scala> Recommendations.critics \ "Toby" \ "Snakes on a Plane"
res5: net.liftweb.json.JsonAST.JValue = JDouble(4.5)

scala> Recommendations.critics \ "Toby"
res6: net.liftweb.json.JsonAST.JValue = JObject(List(JField(Snakes on a Plane,JDouble(4.5)), JField(Superman Returns,JDouble(4.0)), JField(You, Me and Dupree,JDouble(1.0))))

scala> Recommendations.critics \ "Toby" \ "Star Wards"
res7: net.liftweb.json.JsonAST.JValue = JNothing
    */
}