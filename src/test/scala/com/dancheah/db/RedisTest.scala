package com.dancheah.db

import org.scalatest.junit.JUnitSuite
import org.junit.Test
import redis.clients.jedis.Jedis

/**
 * User: dc
 * Date: 8/14/11
 * Time: 4:33 PM
 */

class RedisTest extends JUnitSuite {
  @Test def testRedisConnect() {
    val jedis = new Jedis("localhost")
    jedis.disconnect()
  }
}