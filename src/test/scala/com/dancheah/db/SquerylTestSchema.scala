package com.dancheah.db

import org.squeryl.KeyedEntity
import org.squeryl.Schema
import org.squeryl.PrimitiveTypeMode._

/**
 * User: dc
 * Date: 8/14/11
 * Time: 6:09 PM
 */

// psql doesn't like when I use upper case characters
// in the table name. Switching scala class to be
// all lower case because of this.
class author(val id: Long,
	           val firstName: String,
	           val lastName: String,
	           val email: Option[String]) extends KeyedEntity[Long] {
  def this() = this(0, "", "", Some(""))
}

class book(val id: Long,
           val title: String,
           val authorId: Long,
           val coAuthorId: Option[Long]) extends KeyedEntity[Long] {
  def this() = this(0, "", 0, Some(0L))
}

object SquerylTestSchema extends Schema {
  val authors = table[author]

  val books = table[book]

  def populateTestData() {
    // Create a sample table
    inTransaction {
      drop
      create

      authors.insert(new author(1, "JRR", "Tolkien", None))
      authors.insert(new author(2, "Jane", "Austen", None))
      authors.insert(new author(3, "Philip", "Pullman", None))
      authors.insert(new author(4, "Test", "Author", None))

      books.insert(new book(1, "The Lord of the Rings", 1, None))
      books.insert(new book(2, "Pride and Prejudice", 2, None))
      books.insert(new book(3, "His Dark Materials", 3, None))
      books.insert(new book(4, "Test Book", 4, None))
    }
  }
}