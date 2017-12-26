# Jenkins, Shmenkins

A tiny library to run your Jenkinsfile through the commandline with 100% pass through ( it will execute everything locally not in any container ).

To run simply, copy the shmenkins.groovy to your project directory, import it into your Jenkinsfile with

```java

import static shmenkins.fileLoader
import static shmenkins.*

```

and run the command with `groovy Jenkinsfile` , which will execute the stages.

## What is it

Shmenkins tries to stub out most of the internal Jenkinsfile functions , if it doesnt please let me know and I'll add it.  Plugins like fileLoader above are additional static imports.

### Plugins Supported

[https://github.com/jenkinsci/workflow-remote-loader-plugin](fileLoader) - returns a text copy of the file from the directory its run in, so currently you have to link all your loaded files to the project root