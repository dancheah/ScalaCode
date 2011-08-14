package com.dancheah.db

import org.scalatest.junit.JUnitSuite
import org.junit.Test
import java.sql.DriverManager
/**
 * User: dc
 * Date: 8/13/11
 * Time: 11:02 PM
 */

class HSqlDbTest extends JUnitSuite {
  @Test def testHsqldbClassLoad() {
    Class.forName("org.hsqldb.jdbc.JDBCDriver" )
  }

  @Test def testH2Connect() {
    Class.forName("org.h2.Driver")
    val c = DriverManager.getConnection("jdbc:h2:mem:")
    c.close()
  }
}