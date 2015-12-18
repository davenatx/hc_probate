import Dependencies._ 

import scalariform.formatter.preferences._

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

ScalariformKeys.preferences := ScalariformKeys.preferences.value
  .setPreference(AlignParameters, true)
  .setPreference(AlignSingleLineCaseStatements, true)
  .setPreference(CompactControlReadability, true)
  .setPreference(DoubleIndentClassDeclaration, true)
  .setPreference(IndentLocalDefs, true)
  .setPreference(PreserveDanglingCloseParenthesis, true)
  .setPreference(RewriteArrowSymbols, true)


resolvers += "Github Repo" at "http://davenatx.github.io/maven"