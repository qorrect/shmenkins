# Jenkins, Shmenkins

A tiny library to run your Jenkinsfile through the commandline with 100% pass through ( it will execute everything locally not in any container or remotely ).

To run , simply copy the shmenkins.groovy to the project you have your Jenkinsfile in , and run 

```

groovy shemnkins.groovy 

```

This will attempt to run all the stages locally.

## What is it

Shmenkins tries to stub out most of the internal Jenkinsfile functions.  Because of the myriad of plugins, it likely wont work out of the box, please create an issue for any plugins not currently supported.

### Plugins Supported

[fileLoader](https://github.com/jenkinsci/workflow-remote-loader-plugin) - returns a text copy of the file from the directory its run in, so currently you have to link all your loaded files to the project root
[configFileProvider](https://wiki.jenkins.io/display/JENKINS/Config+File+Provider+Plugin) - Currently does nothing, need a smarter way to default to sane values like ~/.m2/settings.xml