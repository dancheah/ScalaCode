import AssemblyKeys._

name := "My Project"

version := "1.0"

organization := "com.dancheah"

scalaVersion := "2.9.1"

// disable updating dynamic revisions (including -SNAPSHOT versions)
// offline := true

// fork a new JVM for 'run' and 'test:run'
fork := true

// Copy all managed dependencies to <build-root>/lib_managed/
//   This is essentially a project-local cache and is different
//   from the lib_managed/ in sbt 0.7.x.  There is only one
//   lib_managed/ in the build root (not per-project).
retrieveManaged := true

// Include Assembly Commands
seq(assemblySettings: _*)

// Include Web Commands
seq(webSettings :_*)

// Include JRebel Commands
seq(jrebelSettings: _*)

// Repositories to Include
resolvers ++= Seq(
  "Nexus Scala Tools Repository" at "http://nexus.scala-tools.org/content/repositories/releases/",
  "Scala Tools Snapshots" at "http://scala-tools.org/repo-snapshots/",
  "Typesafe Repository" at "http://repo.typesafe.com/typesafe/releases",
  "Sonatype OSS Snapshots" at "http://oss.sonatype.org/content/repositories/snapshots/",
  "java m2" at "http://download.java.net/maven/2"
)

// Testing Libraries
libraryDependencies ++= Seq(
  "junit" % "junit" % "4.8" % "test",
  "org.scalatest" %% "scalatest" % "1.6.1" % "test",
  "org.scala-tools.testing" %% "scalacheck" % "1.9" % "test",
  "org.specs2" %% "specs2" % "1.6.1" % "test"
)

// Databases, ORM
libraryDependencies ++= Seq(
  "org.hsqldb" % "hsqldb" % "2.2.4",
  "com.h2database" % "h2" % "1.3.159",
  "postgresql" % "postgresql" % "9.0-801.jdbc4",
  "redis.clients" % "jedis" % "2.0.0" % "compile",
  "org.squeryl" % "squeryl_2.9.0-1" % "0.9.4",
  "org.neo4j" % "neo4j" % "1.4.1",
  "net.debasishg" %% "redisclient" % "2.4.0"
)

// Scalatra
libraryDependencies ++= Seq(
  "org.scalatra" %% "scalatra" % "2.0.0",
  "org.scalatra" %% "scalatra-scalate" % "2.0.0",
  "javax.servlet" % "servlet-api" % "2.5" % "provided"
)

// Jetty 
libraryDependencies ++= Seq(
  //"org.eclipse.jetty" % "jetty-webapp" % "7.4.5.v20110725" % "jetty"
  "org.eclipse.jetty" % "jetty-webapp" % "8.0.1.v20110908"
)

// Cloud Libraries
libraryDependencies ++= Seq(
  "org.jclouds" % "jclouds-allcompute" % "1.1.1",
  "org.jclouds" % "jclouds-allblobstore" % "1.1.1"
)

// Unfiltered
libraryDependencies ++= Seq(
   "net.databinder" %% "unfiltered-filter" % "0.4.1",
   "net.databinder" %% "unfiltered-jetty" % "0.4.1",
   "org.clapper" %% "avsl" % "0.3.6"
)

// Misc Scala Libs
libraryDependencies ++= Seq(
  "org.clapper" %% "grizzled-scala" % "1.0.8",
  "net.databinder" %% "dispatch-http" % "0.8.5",
  "se.scalablesolutions.akka" % "akka-actor" % "1.1.3",
  "org.scalaz" %% "scalaz-core" % "6.0.3",
  "net.liftweb" %% "lift-json" % "2.4-SNAPSHOT",
  "com.github.jsuereth.scala-arm" %% "scala-arm" % "0.3"
)

// Misc Java Libs
libraryDependencies ++= Seq(
  "org.apache.lucene" % "lucene-core" % "3.3.0",
  "org.apache.mahout" % "mahout-core" % "0.5",
  "commons-logging" % "commons-logging" % "1.1.1" % "compile"
)

// vim: sts=4 sw=4 ts=4 et ft=scala
