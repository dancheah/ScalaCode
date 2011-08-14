package com.dancheah.db

import org.scalatest.junit.JUnitSuite
import org.junit.Test
import java.sql.DriverManager

/**
 * User: dc
 * Date: 8/14/11
 * Time: 4:34 PM
 */

class PgsqlTest extends JUnitSuite {
  @Test def testPostgresClassLoad() {
    Class.forName("org.postgresql.Driver");
  }

  @Test def testPostgresConnect() {
     Class.forName("org.postgresql.Driver")
     val c = DriverManager.getConnection("jdbc:postgresql://localhost/test")
     c.close()
   }
}