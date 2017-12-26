

public class shmenkins {
  // Custom environment
  static def MAVEN_SETTINGS = "~/.m2/settings.xml"
  // End Custom environment 
  static def env = [ BRANCH : "dev" ]
  
  //Plugins
  static def configFile (Map o){}
  static def configFileProvider (List l,Closure body){
    body()
  }  
  

  static def scm = ""
  static def currentBuild = [ number : 1 , rawBuild: null ]
  static def params = [ DEPLOY_TARGET : "Do not deploy" ]
  static def containerTemplate(Map options ) {}
  static def hostPathVolume(Map options ) {} 
  static def disableConcurrentBuilds() {}
  static def choice (Map o){}
  static def step (Map o){}
  static def parameters (List l){}
  static def git (Map o){}
  static def checkout(String s) {}
  static def readFile(String s) {
    return new File(s).text
  }
  static def cron (String s){}
  static def pipelineTriggers (List l){}
  static def properties (List l){}
  static def node (String n = "",Closure body)  {
    body()
  }
  static def podTemplate(Map options, Closure body) {
    body()
  }
  static def container(String s, Closure body){
    println("Container ${s}")
    body()
  }
  static def stage(String s, Closure body){
    println("Stage ${s}")
    body()
  }
  static def withCredentials(List l, Closure body) {
    println("WithCredentials ${l}")
    body()
  }
  static def echo (String s) {
    println(s)
  }
  static def sh(Map m) { return sh(m['script'] ) }
  static def sh(String s) {
    print("Executing ${s}")
    def p = ['/bin/bash','-c',s].execute()
    p.waitFor()
    def ret = p.text
    println ret
    return ret 
  
  }
  // END FAKING ENVIRONMENT
}

class fileLoader {
  static def fromGit(String libPath, String repository, String branch, String credentailsId, String labelExpression) {
    GroovyShell shell = new GroovyShell()
    def script = shell.evaluate(new File(libPath))
    //static def script = shell.parse(new File(libPath))
    return script
    
  }
}
