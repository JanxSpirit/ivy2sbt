import xml._

object Ivy2Sbt {
  def main(args: Array[String]) {
    if (args.size != 1) println("usage: Ivy2Sbt <ivy-file-name>")
    val ivy = XML.loadFile(args(0))
    val deps = ivy \\ "dependency"
    println("libraryDependencies ++= Seq(")
    deps.foreach(outputSbtDepLine)
    println(")")
  }

  def outputSbtDepLine(dep: Node) = {
    println("  \"%s\" %s \"%s\" %s \"%s\"%s"
      .format(dep.attribute("org").getOrElse(""), "%", dep.attribute("name").getOrElse(""), "%", dep.attribute("rev").getOrElse(""), 
        (dep.attribute("conf") match {
	  case None => ","
	  case Some(conf) => " % \""+conf.head.toString.replace("&gt;",">").replace("&gt;",">")+"\","
	})))
  }

}
