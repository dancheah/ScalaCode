package com.dancheah.db

import org.scalatest.junit.JUnitSuite
import org.junit.Test
import java.sql.DriverManager
/**
 * User: dc
 * Date: 8/13/11
 * Time: 11:02 PM
 */

class H2DbTest extends JUnitSuite {
  @Test def testH2ClassLoad() {
    Class.forName("org.h2.Driver")
  }

  @Test def testH2Connect() {
    Class.forName("org.h2.Driver")
    val c = DriverManager.getConnection("jdbc:h2:mem:")
    c.close()
  }
}