package com.dancheah.db

import org.squeryl.KeyedEntity
import org.squeryl.Schema
import org.squeryl.PrimitiveTypeMode._

/**
 * User: dc
 * Date: 8/14/11
 * Time: 6:09 PM
 */

class Author(val id: Long,
	           val firstName: String,
	           val lastName: String,
	           val email: Option[String]) extends KeyedEntity[Long] {
  def this() = this(0, "", "", Some(""))
}

class Book(val id: Long,
           val title: String,
           val authorId: Long,
           val coAuthorId: Option[Long]) extends KeyedEntity[Long] {
  def this() = this(0, "", 0, Some(0L))
}

object SquerylTestSchema extends Schema {
  val authors = table[Author]

  val books = table[Book]

  def populateTestData() {
    // Create a sample table
    inTransaction {
      drop
      create

      authors.insert(new Author(1, "JRR", "Tolkien", None))
      authors.insert(new Author(2, "Jane", "Austen", None))
      authors.insert(new Author(3, "Philip", "Pullman", None))
      authors.insert(new Author(4, "Test", "Author", None))

      books.insert(new Book(1, "The Lord of the Rings", 1, None))
      books.insert(new Book(2, "Pride and Prejudice", 2, None))
      books.insert(new Book(3, "His Dark Materials", 3, None))
      books.insert(new Book(4, "Test Book", 4, None))
    }
  }
}