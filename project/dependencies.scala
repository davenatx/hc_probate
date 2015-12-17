import sbt._

object Version {
  val jt400        = "7.10"
  val scalaLogging = "3.1.0"
  val logback      = "1.1.3"
  val config       = "1.3.0"
}

object Library {
  val jt400        = "com.github.davenatx"        % "jt400"               % Version.jt400
  val scalaLogging = "com.typesafe.scala-logging" %% "scala-logging"      % Version.scalaLogging
  val logback      = "ch.qos.logback"             % "logback-classic"     % Version.logback
  val config       = "com.typesafe"               % "config"              % Version.config
}

object Dependencies {
 
  import Library._

  val HCProbate = List(
    jt400,
    scalaLogging,
    logback,
    config
  )
}
