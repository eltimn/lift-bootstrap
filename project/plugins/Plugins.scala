import sbt._
class Plugins(info: ProjectInfo) extends PluginDefinition(info) {
  lazy val untypedRepo = "Untyped Repo" at "http://repo.untyped.com"
  lazy val closureCompiler = "untyped" % "sbt-closure" % "0.5-SNAPSHOT"
  lazy val lessCompiler = "untyped" % "sbt-less" % "0.1-SNAPSHOT"
}
