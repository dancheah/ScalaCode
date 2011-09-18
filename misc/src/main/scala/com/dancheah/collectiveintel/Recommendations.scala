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
  
  def intersect_list(prefs:JValue, person1:String, person2:String) = 
    (for {
      JObject(list) <- prefs \ person1
      JField(key, JDouble(_)) <- list
    } yield key)
    .intersect(for {
      JObject(list) <- prefs \ person2
      JField(key, JDouble(_)) <- list
    } yield key) 

  def sim_distance(prefs:JValue, person1:String, person2:String) = {
    // Get the list of shared items between the two people
    val l = intersect_list(prefs, person1, person2)
    
    if (l.length == 0) {
      0
    } else {
      // Calculate the sum of squares
      val sum_of_squares = (for {
          item <- l
          JDouble(score1) <- prefs \ person1 \ item
          JDouble(score2) <- prefs \ person2 \ item
      } yield score1 - score2)
      .map(pow(_, 2)).sum
    
      1 / (1 + sum_of_squares)
    }
  }
  
  def sim_pearson(prefs:JValue, person1:String, person2:String) = {
    val l = intersect_list(prefs, person1, person2)
    val n = l.length
    
    if (n == 0) {
      0
    } else {
      val l1 = for { it <- l
                   JDouble(x) <- prefs \ person1 \ it          
                 } yield x
                 
      val l2 = for { it <- l
                   JDouble(x) <- prefs \ person2 \ it
                 } yield x
                 
      val sum1 = l1.sum
      val sum2 = l2.sum
      val sum1sq = l1.map(pow(_, 2)).sum
      val sum2sq = l2.map(pow(_, 2)).sum    
      val psum = l1.zip(l2).map({ x => x._1 * x._2}).sum
    
      val num = psum - (sum1 * sum2 / n)     
      val den = sqrt((sum1sq - pow(sum1, 2)/n) * (sum2sq - pow(sum2, 2)/n))
      
      if (den == 0) {
        0
      } else {
        num / den
      }
    }
  }
  
  def topMatches(prefs:JValue, person:String, 
                 n:Integer = 5, 
                 similarity: (JValue, String, String) => Double = sim_pearson) = { 
        prefs.children
            .filterNot(_ match {
                    case JField(name, _) => name == person
                    case _ => throw new IllegalStateException("impossible")
            }) 
            .map(_ match {
                case JField(name, _) => (name, similarity(prefs, person, name))
                case _ => throw new IllegalStateException("impossible")
            })
            .sort((x,y) => x._2 < y._2)
            .reverse
            .take(n)
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

scala> Recommendations.sim_distance(Recommendations.critics, "Lisa Rose", "Gene Seymour")
res16: Double = 0.14814814814814814    
    */
}