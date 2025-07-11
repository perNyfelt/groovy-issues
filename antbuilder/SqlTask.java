import groovy.lang.GroovyShell;
import java.io.File;
import groovy.lang.GroovyClassLoader;
import org.codehaus.groovy.tools.GroovyStarter;
import java.io.IOException;

public class SqlTask {

  public static void main(String[] args) throws Exception {

    File file = new File("sqltask.groovy");
    runInShell(file);
    launch(file, System.getProperty("GROOVY_HOME"));
  }

  /**
   * Does not work!
   * Exception in thread "main" : Class Not Found: JDBC driver org.h2.Driver could not be loaded
   *         at org.apache.tools.ant.taskdefs.JDBCTask.getDriver(JDBCTask.java:434)
   *         at org.apache.tools.ant.taskdefs.JDBCTask.getConnection(JDBCTask.java:365)
   *         at org.apache.tools.ant.taskdefs.SQLExec.getConnection(SQLExec.java:953)
   *         at org.apache.tools.ant.taskdefs.SQLExec.execute(SQLExec.java:649)
   *         at org.apache.tools.ant.UnknownElement.execute(UnknownElement.java:299)
   *         at java.base/jdk.internal.reflect.DirectMethodHandleAccessor.invoke(DirectMethodHandleAccessor.java:103)
   *         at java.base/java.lang.reflect.Method.invoke(Method.java:580)
   *         at org.apache.tools.ant.dispatch.DispatchUtils.execute(DispatchUtils.java:99)
   *         at groovy.ant.AntBuilder.performTask(AntBuilder.java:347)
   *         at groovy.ant.AntBuilder.nodeCompleted(AntBuilder.java:286)
   *         at groovy.util.BuilderSupport.doInvokeMethod(BuilderSupport.java:161)
   *         at groovy.ant.AntBuilder.doInvokeMethod(AntBuilder.java:219)
   *         at groovy.util.BuilderSupport.invokeMethod(BuilderSupport.java:75)
   *         at org.codehaus.groovy.runtime.InvokerHelper.invokePogoMethod(InvokerHelper.java:651)
   *         at org.codehaus.groovy.runtime.InvokerHelper.invokeMethod(InvokerHelper.java:628)
   *         at org.codehaus.groovy.runtime.metaclass.ClosureMetaClass.invokeOnDelegationObjects(ClosureMetaClass.java:392)
   *         at org.codehaus.groovy.runtime.metaclass.ClosureMetaClass.invokeMethod(ClosureMetaClass.java:331)
   *         at groovy.lang.MetaClassImpl.invokeMethod(MetaClassImpl.java:1007)
   *         at org.codehaus.groovy.vmplugin.v8.IndyInterface.fromCache(IndyInterface.java:321)
   *         at sqltask$_run_closure1.doCall(sqltask.groovy:9)
   *         at java.base/jdk.internal.reflect.DirectMethodHandleAccessor.invoke(DirectMethodHandleAccessor.java:103)
   *         at java.base/java.lang.reflect.Method.invoke(Method.java:580)
   *         at org.codehaus.groovy.reflection.CachedMethod.invoke(CachedMethod.java:343)
   *         at groovy.lang.MetaMethod.doMethodInvoke(MetaMethod.java:328)
   *         at org.codehaus.groovy.runtime.metaclass.ClosureMetaClass.invokeMethod(ClosureMetaClass.java:280)
   *         at groovy.lang.MetaClassImpl.invokeMethod(MetaClassImpl.java:1007)
   *         at groovy.lang.Closure.call(Closure.java:433)
   *         at groovy.lang.Closure.call(Closure.java:422)
   *         at org.codehaus.groovy.runtime.DefaultGroovyMethods.with(DefaultGroovyMethods.java:368)
   *         at org.codehaus.groovy.runtime.DefaultGroovyMethods.with(DefaultGroovyMethods.java:313)
   *         at org.codehaus.groovy.runtime.dgm$914.doMethodInvoke(Unknown Source)
   *         at org.codehaus.groovy.vmplugin.v8.IndyInterface.fromCache(IndyInterface.java:321)
   *         at sqltask.run(sqltask.groovy:7)
   *         at groovy.lang.GroovyShell.evaluate(GroovyShell.java:460)
   *         at groovy.lang.GroovyShell.evaluate(GroovyShell.java:504)
   *         at SqlTask.runInShell(SqlTask.java:18)
   *         at SqlTask.main(SqlTask.java:12)
   */
  static void runInShell(File groovyScript) {
    System.out.println();
    System.out.println("***********************");
    System.out.println("Running in Groovy Shell");
    System.out.println("***********************");
    try {
      GroovyShell groovyShell = new GroovyShell();
      groovyShell.evaluate(groovyScript);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
  /**
   * Does NOT work:
   * Caught: : Class Not Found: JDBC driver org.h2.Driver could not be loaded
   * : Class Not Found: JDBC driver org.h2.Driver could not be loaded
   *         at org.apache.tools.ant.taskdefs.JDBCTask.getDriver(JDBCTask.java:434)
   *         at org.apache.tools.ant.taskdefs.JDBCTask.getConnection(JDBCTask.java:365)
   *         at org.apache.tools.ant.taskdefs.SQLExec.getConnection(SQLExec.java:953)
   *         at org.apache.tools.ant.taskdefs.SQLExec.execute(SQLExec.java:649)
   *         at org.apache.tools.ant.UnknownElement.execute(UnknownElement.java:299)
   *         at org.apache.tools.ant.dispatch.DispatchUtils.execute(DispatchUtils.java:99)
   *         at sqltask$_run_closure1.doCall(sqltask.groovy:9)
   *         at sqltask.run(sqltask.groovy:7)
   *         at SqlTask.launch(SqlTask.java:27)
   *         at SqlTask.main(SqlTask.java:12)
   * Caused by: java.lang.ClassNotFoundException: org.h2.Driver
   *         at org.apache.tools.ant.taskdefs.JDBCTask.getDriver(JDBCTask.java:427)
   */
  static void launch(File groovyScript, String groovyHome) {
    System.out.println();
    System.out.println("***********************");
    System.out.println("Running in GroovyStater");
    System.out.println("***********************");
    System.setProperty("groovy.home", groovyHome); // missing in my groovy-starter.conf because sdkman can change it
    String confFile   = groovyHome + "/conf/groovy-starter.conf";
    String cp         = System.getProperty("java.class.path")
        + ":" + groovyHome + "/lib/groovy-all.jar";
    // Build the starter arguments
    String[] starterArgs = new String[] {
        "--conf",     confFile,
        "--classpath", cp,
        "--main",     "groovy.ui.GroovyMain",
        groovyScript.getAbsolutePath()
    };
    try {
      GroovyStarter.main(starterArgs);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}