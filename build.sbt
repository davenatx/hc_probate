import Dependencies._ 

name := "hc_probate"

version := "0.1"

organization := "com.austindata"

scalaVersion := "2.11.7"

scalacOptions ++= Seq("-optimize", "-deprecation", "-feature")

libraryDependencies ++= Dependencies.HCProbate

git.baseVersion := "1.0"

//versionWithGit

showCurrentGitBranch

scalariformSettings

resolvers += "Github Repo" at "http://davenatx.github.io/maven"