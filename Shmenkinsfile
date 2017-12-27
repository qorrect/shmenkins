
GroovyShell shell = new GroovyShell()
def Jenkinsfile = shell.parse(new File("Jenkinsfile"))

// Custom environment
Jenkinsfile.MAVEN_SETTINGS = "~/.m2/settings.xml"
Jenkinsfile.env = [ BRANCH : "dev" ]
Jenkinsfile.params = [ DEPLOY_TARGET : "Do not deploy" ]
// End Custom environment 

def sh ( String s) {
  print("Executing ${s}")
  def p = ['/bin/bash','-c',s].execute()
  p.waitFor()
  def ret = p.text
  println ret
  return ret 
}
def sh (Map m) {
  return sh(m['script'] )
}

Jenkinsfile.scm = ""
Jenkinsfile.currentBuild = [ number : 1 , rawBuild: null ]
Jenkinsfile.containerTemplate =  { Map m -> } 
Jenkinsfile.hostPathVolume =  { o -> } 
Jenkinsfile.podTemplate = { Map options, Closure body -> 
  body()
}
Jenkinsfile.choice  = { Map o -> }
Jenkinsfile.step  = { Map o -> }
Jenkinsfile.parameters  = { List l -> }
Jenkinsfile.git  = { Map o -> }
Jenkinsfile.checkout =   { String s -> }
Jenkinsfile.cron  = { String s -> }
Jenkinsfile.pipelineTriggers  = { List l -> }
Jenkinsfile.properties  = { List l -> }
Jenkinsfile.disableConcurrentBuilds = {}
Jenkinsfile.sh = this.&sh

Jenkinsfile.readFile =  { String s ->
  return new File(s).text
}
Jenkinsfile.node =  { String n = "",Closure body -> 
  body()
}
Jenkinsfile.container=  { String s, Closure body -> 
  println("Container ${s}")
  body()
}
Jenkinsfile.stage=  {String s, Closure body -> 
  println("Stage ${s}")
  body()
}

Jenkinsfile.withCredentials= { List l, Closure body -> 
  println("WithCredentials ${l}")
  body()
}
Jenkinsfile.echo = { String s -> 
  println(s)
}

//Plugins
Jenkinsfile.configFile = { Map o }
Jenkinsfile.configFileProvider  = { List l,Closure body -> 
  body()
}  

class fileLoader {
  static def fromGit(String libPath, String repository, String branch, String credentailsId, String labelExpression) {
    GroovyShell shell = new GroovyShell()
    def script = shell.evaluate(new File(libPath))

    script.sh = { def s ->
      if ((s instanceof Map)) {
	s = s["script"]
      }
      print("Executing ${s}\n")
      def p = ['/bin/bash','-c',s].execute()
      p.waitFor()
      def ret = p.text
      println ret
      return ret 
    }

    // def script = shell.parse(new File(libPath))
    return script
    
  }
}

Jenkinsfile.fileLoader = fileLoader
Jenkinsfile.run()
