lazy val appSettings = Seq(
  organization := "org.x4444",
  name := "onsbt",
  version := "1.0-SNAPSHOT"
)

// Those settings should be the same as in alchemy!
lazy val scalaVersion_ = "2.10.5"
lazy val javaVersion = "1.7"
lazy val sparkVersion = "1.6.1"

lazy val scalaCheckVersion = "1.13.0"
lazy val scalaTestVersion = "2.2.6"

scalaVersion in Global := scalaVersion_

scalacOptions in Global ++= Seq(
  "-deprecation",
  "-feature",
  "-target:jvm-" + javaVersion,
  "-Xlint"
)

javaOptions in Global ++= Seq(
)

javacOptions in Global ++= Seq(
  "-encoding", "UTF-8",
  "-source", javaVersion,
  "-target", javaVersion
)

lazy val mainLib = Seq(
  "org.apache.spark" %% "spark-core" % sparkVersion % Provided withSources() withJavadoc(),
  // apache common
  "commons-codec" % "commons-codec" % "1.10" % Provided withSources() withJavadoc(),
  "org.apache.commons" % "commons-lang3" % "3.3.2" % Provided withSources() withJavadoc(),
  // AWS
  "com.amazonaws" % "aws-java-sdk-s3" % "1.11.7" % Provided withSources() withJavadoc(),
  // parsing
  "net.sf.supercsv" % "super-csv" % "2.2.0" % Provided withSources() withJavadoc(),
  // utils
  "org.slf4j" % "slf4j-api" % "1.7.10" % Provided withSources() withJavadoc()
)

lazy val testLib = Seq(
  "org.scalatest" %% "scalatest" % scalaTestVersion % Test withSources() withJavadoc(),
  "org.scalacheck" %% "scalacheck" % scalaCheckVersion % Test withSources() withJavadoc()
)

resolvers in Global ++= Seq(
  Resolver.sonatypeRepo("snapshots")
)

//removes _2.10 auto suffix in artifact name
crossPaths in Global := false

publishTo in Global := {
  val nexus = "http://nexus.acme.com/nexus/content/repositories/"
  val repository = if (isSnapshot.value) "snapshots" else "releases"
  Some(s"Acme ${repository.capitalize}" at nexus + s"$repository")
}

credentials in Global += Credentials(Path.userHome / ".ivy2" / ".credentials")

publishMavenStyle in Global := true

lazy val root = (project in file("."))
  .settings(appSettings: _*)
  .settings(
    libraryDependencies ++= mainLib,
    libraryDependencies ++= testLib,
    dependencyOverrides ++= Set(
      "com.fasterxml.jackson.core" % "jackson-annotations" % "2.4.4" % Provided, // to avoid mix of jackson jars versions
      "com.fasterxml.jackson.core" % "jackson-core" % "2.4.4" % Provided,
      "com.fasterxml.jackson.core" % "jackson-databind" % "2.4.4" % Provided,
      "org.apache.httpcomponents" % "httpcore" % "4.3.3" % Provided,
      "org.apache.httpcomponents" % "httpclient" % "4.3.6" % Provided,
      "commons-beanutils" % "commons-beanutils" % "1.8.3" % Provided,
      "commons-codec" % "commons-codec" % "1.10" % Provided,
      "commons-lang" % "commons-lang" % "2.6" % Provided,
      "com.google.guava"  % "guava" % "16.0.1",
      "joda-time" % "joda-time" % "2.9")
  )
  .settings(addArtifact(artifact in (Compile, assembly), assembly).settings: _*)

fork in Global := true

// before importing project to IDEA on OS X run
// launchctl setenv HADOOP_CONF_DIR /etc/hadoop/conf
lazy val hadoopConfDir: File = new File(sys.env("HADOOP_CONF_DIR"))

unmanagedClasspath in Runtime <<= (unmanagedClasspath in Runtime, baseDirectory) map { (cp, bd) => cp :+ Attributed.blank(hadoopConfDir) }

unmanagedClasspath in Test <<= (unmanagedClasspath in Test, baseDirectory) map { (cp, bd) => cp :+ Attributed.blank(hadoopConfDir) }
