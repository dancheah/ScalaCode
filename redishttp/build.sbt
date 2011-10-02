organization := "com.dancheah"

name := "redishttp"

version := "0.1.0-SNAPSHOT"

libraryDependencies ++= Seq(
   "net.databinder" %% "unfiltered-netty-server" % "0.5.0",
   "net.databinder" %% "dispatch-nio" % "0.8.5",
   "net.databinder" %% "unfiltered-json" % "0.5.0",
   "net.debasishg" %% "redisclient" % "2.4.0",
   "org.clapper" %% "avsl" % "0.3.6"
)

resolvers ++= Seq(
  "jboss repo" at "http://repository.jboss.org/nexus/content/groups/public-jboss/"
)

// vim: sts=4 sw=4 ts=4 et ft=scala
