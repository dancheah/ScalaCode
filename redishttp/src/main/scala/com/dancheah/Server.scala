package com.dancheah

/** embedded server */
object Server {
  val logger = org.clapper.avsl.Logger(Server.getClass)
  val http = new dispatch.nio.Http

  def main(args: Array[String]) {
    unfiltered.netty.Http(8080)
      .handler(RedisHttp)
      .run { s =>
        logger.info("starting unfiltered app at localhost on port %s"
                    .format(s.port))
      }
    http.shutdown()
  }
}
