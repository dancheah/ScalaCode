package com.dancheah.lucene
import org.apache.lucene.index.IndexWriter
import org.apache.lucene.store.FSDirectory
import org.apache.lucene.analysis.standard.StandardAnalyzer
import org.apache.lucene.util.Version
import org.apache.lucene.document.{Document, Field}
import java.io.{FileReader, File, FileFilter}

/**
 * Indexer from chapter 1 of Lucene in Action. Rewritten
 * in Scala. Getting comfortable learning Lucene
 * and Scala.
 */
object IndexerMain {
    def main(args: Array[String]) {
      if (args.length != 2) {
        throw new IllegalArgumentException("Usage: indexer <index dir> <data dir>")        
      }
      
      val indexDir = args(0)
      val dataDir  = args(1)
      
      println("indexDir: " + indexDir)
      println("dataDir: " + dataDir)
      
      val start = System.currentTimeMillis
      val indexer = new Indexer(indexDir)

      val numIndexed = try {
        indexer.index(dataDir, Some(TextFileFilter))
      } finally {
        indexer.close
      }
      
      val end = System.currentTimeMillis
      println("Indexed files: " + numIndexed)
      println((end - start) + " ms")      
    }
}

object TextFileFilter extends FileFilter {
  def accept(path: File) = {
    path.getName.toLowerCase.endsWith(".txt")
  }
}

class Indexer(indexDir: String) {
  val directory = FSDirectory.open(new File(indexDir))
  val writer = new IndexWriter(directory,
                               new StandardAnalyzer(Version.LUCENE_30),
                               true,
                               IndexWriter.MaxFieldLength.UNLIMITED)     

  def close() {
    writer.close()
  }
  
  def index(dataDir: String, filter: Option[FileFilter]) = {
      val files = new File(dataDir).listFiles
      for (file <- files 
           if !file.isDirectory ; 
           if !file.isHidden ; 
           if file.canRead ; 
           if file.exists ;
           if (!filter.isDefined || filter.get.accept(file))
      ) indexFile(file)
        
      writer.numDocs()  
  }

  def getDocument(file: File) = {
    val doc = new Document
    doc.add(new Field("contents", new FileReader(file)))
    doc.add(new Field("filename", file.getName(),
              Field.Store.YES, Field.Index.NOT_ANALYZED))
    doc.add(new Field("fullpath", file.getCanonicalPath(),
              Field.Store.YES, Field.Index.NOT_ANALYZED))
    doc
  }

  def indexFile(file: File) = {
      println("Indexing " + file.getCanonicalPath());
      val doc = getDocument(file)
      writer.addDocument(doc);
  }
}