package com.dancheah.db

import org.scalatest.junit.JUnitSuite
import org.squeryl.{Session, SessionFactory}
import org.squeryl.adapters.PostgreSqlAdapter
import org.junit.{Test, Before}

/**
 * User: dc
 * Date: 8/14/11
 * Time: 6:59 PM
 */

class PgsqlSquerylTest extends JUnitSuite {
  // Commented out because it clashes with the
  // H2 version of the test.
  // @Before
  def initialize() {
    Class.forName("org.postgresql.Driver");

    SessionFactory.concreteFactory = Some(()=>
	    Session.create(
	      java.sql.DriverManager.getConnection("jdbc:postgresql://localhost/test"),
	      new PostgreSqlAdapter))

    SquerylTestSchema.populateTestData()
  }

  @Test def testSimple() {
  }
}