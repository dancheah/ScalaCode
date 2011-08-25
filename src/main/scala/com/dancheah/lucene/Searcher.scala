package com.dancheah.lucene

import org.apache.lucene.store.FSDirectory
import java.io.File
import org.apache.lucene.search.IndexSearcher
import org.apache.lucene.queryParser.QueryParser
import org.apache.lucene.util.Version
import org.apache.lucene.analysis.standard.StandardAnalyzer

/**
 * User: dc
 * Date: 8/24/11
 * Time: 9:59 PM
 */

object SearcherMain {
  def main(args: Array[String]) {
    if (args.length != 2) {
      throw new IllegalArgumentException("Usage: searcher <index dir> <query>")
    }

    val indexDir = args(0)
    val q = args(1);

    search(indexDir, q)
  }

  def search(indexDir: String, q: String) {
    val dir = FSDirectory.open(new File(indexDir));
    val is = new IndexSearcher(dir);
    val parser = new QueryParser(Version.LUCENE_30,
                                 "contents",
                                 new StandardAnalyzer(Version.LUCENE_30))
    val query = parser.parse(q)
    val start = System.currentTimeMillis;
    val hits = is.search(query, 10);
    val end = System.currentTimeMillis;
    println("Found " + hits.totalHits + " document(s) (in " + (end - start) +
            " milliseconds) that matched query '" + q + "':");

    for(scoreDoc <- hits.scoreDocs)
      println(is.doc(scoreDoc.doc).get("fullpath"));

    is.close
  }
}