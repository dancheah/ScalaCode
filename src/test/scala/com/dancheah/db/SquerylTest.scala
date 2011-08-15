package com.dancheah.db

import org.scalatest.junit.JUnitSuite
import org.squeryl.SessionFactory
import org.squeryl.Session
import org.squeryl.PrimitiveTypeMode._
import org.junit.Test
import org.junit.Before
import org.squeryl.adapters.{PostgreSqlAdapter, H2Adapter}

/**
 * This example from the ones provided on the squeryl.org website.
 * http://squeryl.org/schema-definition.html
 *
 * User: dc
 * Date: 8/14/11
 * Time: 6:06 PM
 */

class SquerylTest extends JUnitSuite {
  @Before
  def initialize() {
    Class.forName("org.h2.Driver");

    // Can only test 1 type of database at a time.
    // Just use h2 as the default.
    val pgsql = false;

    if (!pgsql) {
      // Have to create a real physical file on disk. The
      // in memory db only seems to work when we
      // do things in the same scope.
      SessionFactory.concreteFactory = Some(() =>
        Session.create(
          java.sql.DriverManager.getConnection("jdbc:h2:test"),
          new H2Adapter))
    } else {
      // Assume that we can connect to localhost
      // to drop and create tables.
      SessionFactory.concreteFactory = Some(()=>
	      Session.create(
	        java.sql.DriverManager.getConnection("jdbc:postgresql://localhost/test"),
	        new PostgreSqlAdapter))
    }

    SquerylTestSchema.populateTestData()
  }

  // Prevent test from erroring out if we don't have a test
  @Test
  def testTrue() {
  }

  /**
   * Test a simple select for all authors
   */
  @Test
  def testSimpleSelect() {
    inTransaction {
      import com.dancheah.db.SquerylTestSchema._

      def allAuthors = from(authors) (s => select(s))

      assert(allAuthors.size == 4)
    }
  }
}