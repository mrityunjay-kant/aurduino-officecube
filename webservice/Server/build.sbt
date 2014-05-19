name := "cubeserver"

version := "1.0"

organization := "com.majikal"

scalaVersion := "2.9.1"

seq(webSettings: _*)

//seq(net.virtualvoid.sbt.graph.Plugin.graphSettings: _*)

resolvers ++= Seq(
    "Sonatype Maven Repo" at "https://oss.sonatype.org/content/groups/scala-tools/",
	"Sonatype OSS Snapshots" at "https://oss.sonatype.org/content/repositories/snapshots/",
    "Sonatype OSS Releases" at "https://oss.sonatype.org/content/repositories/releases",
    "Novus Salat Snapshots" at "http://repo.novus.com/snapshots",
	"Novus Salat Releases" at "http://repo.novus.com/releases/",
	"Spring Repo" at "http://maven.springframework.org/milestone",
	"Buzz Media" at "http://maven.thebuzzmedia.com",
	"Codahale" at "http://repo.codahale.com"
	)

resolvers += "sbt-idea-repo" at "http://mpeltonen.github.com/maven/"


libraryDependencies ++= Seq(
  "org.slf4j" % "slf4j-api" % "1.6.4",
  "commons-io" % "commons-io" % "2.0.1",
  "net.liftweb" % "lift-common_2.9.1" % "2.5-M4",
  "net.liftweb" % "lift-json_2.9.1" % "2.5-M4",
  "net.liftweb" % "lift-json-ext_2.9.1" % "2.5-M4",
  "net.liftweb" % "lift-webkit_2.9.1" % "2.5-M4",
  "net.liftweb" % "lift-scalate_2.9.1" % "2.4",
  "com.recursivity" %% "recursivity-commons" % "0.6",
  "org.eclipse.jetty" % "jetty-webapp" % "7.4.5.v20110725" % "container",
  "javax.servlet" % "javax.servlet-api" % "3.0.1" % "provided",
  "junit" % "junit" % "4.8" % "test->default", // For JUnit 4 testing
  "org.apache.commons" % "commons-email" % "1.2",
  "javax.mail" % "mail" % "1.4.5",
  "ch.qos.logback" % "logback-core" % "1.0.4",
  "ch.qos.logback" % "logback-classic" % "1.0.4"
  )

// exclude("javax.servlet") --for commons fileupload

// append several options to the list of options passed to the Java compiler
//javacOptions ++= Seq("-source", "1.6", "-target", "1.6")

// append -deprecation to the options passed to the Scala compiler
scalacOptions += "-deprecation"

//webappClasspath := super.webappClasspath +++ buildCompilerJar

//unmanagedClasspath in Runtime <+= (baseDirectory) map { bd => Attributed.blank(bd / "config") }

//Tomcat requires war to be named ROOT.war in order to be deployed as default webapp!
webSettings ++ inConfig(Compile)(
     artifact in packageWar <<= moduleName(n => Artifact("ROOT", "war", "war"))
)
