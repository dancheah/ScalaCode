organization := "com.dancheah"

version := "0.0.1"

name := "justplayin"

version := "0.0.1-SNAPSHOT"

scalaVersion := "2.9.1"

retrieveManaged := true

libraryDependencies ++= Seq(
   "net.databinder" %% "unfiltered-filter" % "0.5.0",
   "net.databinder" %% "unfiltered-jetty" % "0.5.0",
   "net.databinder" %% "unfiltered-json" % "0.5.0",
   "net.databinder" %% "unfiltered-spec" % "0.5.0",
   "org.clapper" %% "avsl" % "0.3.6"
)

resolvers ++= Seq(
  "java m2" at "http://download.java.net/maven/2"
)

// vim: sts=2 sw=2 ts=2 et ft=scala
