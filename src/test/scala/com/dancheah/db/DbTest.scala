package com.dancheah.db

import org.scalatest.junit.JUnitSuite
import org.junit.Test
import java.sql.DriverManager
import io.BytePickle.Def

/**
 * User: dc
 * Date: 8/13/11
 * Time: 11:02 PM
 */

class DbTest extends JUnitSuite {
  @Test def testPostgresClassLoad() {
    Class.forName("org.postgresql.Driver");
  }

  @Test def testH2ClassLoad() {
    Class.forName("org.h2.Driver")
  }

  @Test def testHsqldbClassLoad() {
    Class.forName("org.hsqldb.jdbc.JDBCDriver" )
  }

  @Test def testPostgresConnect() {
    Class.forName("org.postgresql.Driver")
    val c = DriverManager.getConnection("jdbc:postgresql://localhost/test")
    c.close()
  }

  @Test def testH2Connect() {
    Class.forName("org.h2.Driver")
    val c = DriverManager.getConnection("jdbc:h2:mem:")
    c.close()
  }

  @Test def testHsqldbConnect() {
    Class.forName("org.hsqldb.jdbc.JDBCDriver")
    val c = DriverManager.getConnection("jdbc:hsqldb:mem:testdb")
    c.close()
  }


}