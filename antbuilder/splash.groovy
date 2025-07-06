//@GrabConfig(systemClassLoader=true)
//@Grab('org.apache.ant:ant:1.10.15')
import groovy.ant.AntBuilder
import org.apache.tools.ant.Project

def ant = new AntBuilder()
ant.with {
  taskdef(name: 'splash', classname: 'org.apache.tools.ant.taskdefs.optional.splash.SplashTask')
  splash(
    imageurl: 'https://jakarta.apache.org/images/jakarta-logo.gif',
    showduration: 2000
  )
  buildnumber(file: "custom_build_count.txt")
  def customBuild = project.getProperty("build.number") // Still uses 'build.number' property
  echo(message: "Current Custom Build Number: $customBuild")
}