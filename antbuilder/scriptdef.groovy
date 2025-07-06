@GrabConfig(systemClassLoader=true)
@Grab('org.openjdk.nashorn:nashorn-core:15.6')     
@Grab('ant:ant-optional:1.5.3-1')
import groovy.ant.AntBuilder
import org.apache.tools.ant.DefaultLogger
import org.apache.tools.ant.Project


println Class.forName('org.apache.tools.ant.taskdefs.optional.script.ScriptDef')
def ant = new AntBuilder()
def log = new DefaultLogger()
log.setErrorPrintStream(System.err)
log.setOutputPrintStream(System.out)
log.setMessageOutputLevel(Project.MSG_INFO)
ant.project.addBuildListener(log)

ant.with {
  taskdef(name: 'scriptdef', classname: 'org.apache.tools.ant.taskdefs.optional.script.ScriptDef')
  scriptdef(name:"scripttest", language:"javascript") {
    attribute(name:"attr1")
    element(name:"fileset", type:"fileset")
    element(name:"path", type:"path")
    '''
      self.log("Hello from script");
      self.log("Attribute attr1 = " + attributes.get("attr1"));
      self.log("First fileset basedir = "
        + elements.get("fileset").get(0).getDir(project));
    '''
  }
  
  scripttest(attr1:"test") {
    path {
      pathelement(location:"src")
    }
   fileset(dir:"src")
   fileset(dir:"main")
  }
}
