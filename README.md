# Jenkins, Shmenkins

A tiny library to run your Jenkinsfile through the commandline with 100% pass through ( it will execute everything locally not in any container ).

To run , simply copy the shmenkins.groovy to your project directory, import it into your Jenkinsfile with

```java

import static shmenkins.*
import static shmenkins.fileLoader

```

and run the command with `groovy Jenkinsfile` , which will execute the stages.  This stubs out any checkout logic, it assumes a Jenkinsfile in the project root.

## What is it

Shmenkins tries to stub out most of the internal Jenkinsfile functions.  Plugins like fileLoader above are additional static imports.

### Plugins Supported

[fileLoader](https://github.com/jenkinsci/workflow-remote-loader-plugin) - returns a text copy of the file from the directory its run in, so currently you have to link all your loaded files to the project root
[configFileProvider](https://wiki.jenkins.io/display/JENKINS/Config+File+Provider+Plugin) - Currently does nothing, need a smarter way to default to sane values like ~/.m2/settings.xml