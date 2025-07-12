import groovy.lang.GroovyShell;
import java.io.File;
import groovy.lang.GroovyClassLoader;
import groovy.util.GroovyScriptEngine;
import org.codehaus.groovy.tools.GroovyStarter;
import org.codehaus.groovy.tools.RootLoader;

import java.io.IOException;

public class SqlTask {

  public static void main(String[] args) throws Exception {

    File file = new File("sqltask.groovy");
    runInShell(file);
    runInScriptEngine(file);
    launch(file, System.getProperty("GROOVY_HOME"));
  }

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

  static void runInScriptEngine(File groovyScript) {
    System.out.println();
    System.out.println("******************************");
    System.out.println("Running in Groovy ScriptEngine");
    System.out.println("******************************");
    try {
      GroovyScriptEngine gse = new GroovyScriptEngine(".");
      gse.run(groovyScript.getAbsolutePath(), "");
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

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