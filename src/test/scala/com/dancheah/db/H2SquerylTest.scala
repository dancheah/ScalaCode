package com.dancheah.db

import org.scalatest.junit.JUnitSuite
import org.squeryl.SessionFactory
import org.squeryl.Session
import org.squeryl.adapters.H2Adapter
import org.squeryl.PrimitiveTypeMode._
import org.junit.Test
import org.junit.Before

/**
 * This example from the ones provided on the squeryl.org website.
 * http://squeryl.org/schema-definition.html
 *
 * User: dc
 * Date: 8/14/11
 * Time: 6:06 PM
 */

class H2SquerylTest extends JUnitSuite {
  @Before def initialize() {
    Class.forName("org.h2.Driver");

    // Have to create a real physical file on disk. The
    // in memory db only seems to work when we
    // do things in the same scope.

    SessionFactory.concreteFactory = Some(() =>
      Session.create(
	      java.sql.DriverManager.getConnection("jdbc:h2:test"),
	      new H2Adapter))

    SquerylTestSchema.populateTestData()
  }

  /**
   * Test a simple select for all authors
   */
  @Test def testSimpleSelect() {
    inTransaction {
      import com.dancheah.db.SquerylTestSchema._

      def allAuthors = from(authors) (s => select(s))

      assert(allAuthors.size == 4)
    }
  }
}