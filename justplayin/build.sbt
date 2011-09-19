organization := "com.dancheah"

name := "justplayin"

version := "0.1.0-SNAPSHOT"

scalaVersion := "2.9.1"

libraryDependencies ++= Seq(
   "net.databinder" %% "unfiltered-filter" % "0.5.0",
   "net.databinder" %% "unfiltered-jetty" % "0.5.0",
   "org.clapper" %% "avsl" % "0.3.6"
)

resolvers ++= Seq(
  "java m2" at "http://download.java.net/maven/2"
)
