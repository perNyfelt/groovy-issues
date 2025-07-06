@GrabConfig(systemClassLoader=true)
@Grab('org.apache.ant:ant-jsch:1.10.15')
@Grab('com.github.mwiede:jsch:2.27.2')
import groovy.ant.AntBuilder

def project = new AntBuilder()

project.with {
  taskdef(name: 'sshexec', classname: 'org.apache.tools.ant.taskdefs.optional.ssh.SSHExec')
  sshexec(
    host: 'anjing',
    username: 'per',
    keyfile: "${System.getProperty('user.home')}/.ssh/id_rsa",
    port: "22022",
    verbose:    true,
    trust:      true,
    command: 'ls',
    outputproperty: 'remote.ls'
  )
  echo(message: "Remote ls: ${project.properties['remote.ls']}")
}
