/**

   To use these functions in your own Jenkinsfile, simply

   def util = fileLoader.fromGit('contrib/buildUtils.groovy','https://github.com/qorrect/shmenkins', 'master' )
   util.DEFAULT_SLACK_URL  = 'https://hooks.slack.com/services/xxx/yyy/zzz'
   util.DEFAULT_DOCKER_REGISTRY = 'registry.hub.docker.com'
   util.DEFAULT_REGION =  "us-east-1"
   util.parseJson("")

 **/

import groovy.json.JsonOutput

def DEEFAULT_SLACK_URL = ""
def DEFAULT_DOCKER_REGISTRY = ""
def DEFAULT_REGION =  "us-east-1"

class DeployChoices { 
  public static String NONE = "Do not deploy";
  public static String DEV = "Deploy to dev";
  public static String QA = "Deploy to qa";
  public static String FEATURE_BRANCH = "Deploy feature branch to new container";
}
// All functions need to be wrapped in a node() {} block

def getDeployChoices() { return new DeployChoices() }

def execAndParseJson(String s)
{
  return parseJson(sh(script:"${s}",returnStdout: true))
}

def parseJson(def json) {
  new groovy.json.JsonSlurperClassic().parseText(json)
}

def notifySlack(def text, def channel, def slackURL) {
  def payload = JsonOutput.toJson([text      : text, channel   : channel, username  : "jenkins", icon_emoji: ":+1:"])
  sh "curl -X POST --data-urlencode 'payload=${payload}' '${slackURL}'"
}

def isTimeTriggeredBuild(def build) {
  return build.rawBuild?.getCauses().toString().toLowerCase().contains("timertriggercause")
}

def dockerBuildAndPush(def build_label /*="my-project:latest"*/, def tag = "blank" , def docker_file = "Dockerfile", def docker_registry = DEFAULT_DOCKER_REGISTRY )
{
  sh "docker build -f $docker_file -t $tag ."
  sh "docker tag $tag $docker_registry/$build_label"
  sh "docker push $docker_registry/$build_label"
}

def awsGetInstancesForCluster(def cluster, def region = DEFAULT_REGION )
{
  def ret = ""
  def result = execAndParseJson("aws ecs list-attributes --region ${region} --cluster ${cluster} --target-type container-instance --attribute-name ecs.instance-type")
  for ( def attr : result["attributes"] )
  {
    ret += attr["targetId"] + " "
  }

  return ret[0..-2]
}

def awsStartTaskForCluster(def task, def cluster , def region = DEFAULT_REGION )
{
  def containers = awsGetInstancesForCluster(cluster)
  return execAndParseJson("aws ecs start-task --task-definition ${task} --region ${region} --cluster ${cluster} --container-instances $containers" )
}

def awsRestartTasksForCluster(def task, def cluster , def region = DEFAULT_REGION )
{
  // Stop all tasks related to the cluster, the cluster will then relaunch the task with the :latest tag
  def result = execAndParseJson("aws ecs list-tasks --region ${region} --service ${cluster} --cluster ${cluster}")
  def ret = [ failures : [] ]
  // If tasks are running, stop them 
  if (result["taskArns"].size() ) {
    for ( String taskId : result["taskArns"]  ) {
      ret = execAndParseJson("aws ecs stop-task --task ${taskId} --region ${region} --cluster ${cluster}")
    }
  }
  // Otherwise start a new task 
  else {
    ret = awsStartTaskForCluster(task, cluster)
  }
  return ret
}

return this

//awsStartTaskForCluster("xui-with-mockapi:2","x-ui-dev")


