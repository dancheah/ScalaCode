package com.dancheah

import unfiltered.request._
import unfiltered.response._

import unfiltered.netty._
import com.redis._

object RedisHttp extends cycle.Plan 
  with cycle.SynchronousExecution with ServerErrorResponse {
  import QParams._

  val logger = org.clapper.avsl.Logger(getClass)
  val redis  = new RedisClient("localhost", 6379)

  def intent = {
    case req @ Path(Seg("redis" :: id :: Nil)) => req match {
      case GET(_) => 
        logger.debug("GET /redis/" + id)
        val result = redis.get(id)
        result match {
          case Some(value) => ResponseString(value)
          case None => ResponseString("Not found")
        }
      case POST(_) =>
        logger.debug("POST /redis/" + id)
        val payload = Body.bytes(req)  
        val result = redis.set(id, payload)
        result match {
          case true => ResponseString("OK")
          case false => ResponseString("Not OK")
        }
    }
    case _ =>
      logger.debug("Didn't recognize")
      ResponseString("Didn't recognize")
  }
}

// vim: sts=2 sw=2 ts=2 et ft=scala
