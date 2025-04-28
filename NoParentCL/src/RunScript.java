import groovy.lang.GroovyClassLoader;
import groovy.lang.GroovyShell;

import javax.script.ScriptException;
import java.io.File;
import java.io.IOException;

class RunScript {

  public static void main(String[] args) throws ScriptException, IOException {

    GroovyClassLoader classLoader;
    System.out.println("Running script using classloader with parent");
    classLoader = new GroovyClassLoader();
    runScript(classLoader);
    System.out.println("Running script using classloader without parent");
    classLoader = new GroovyClassLoader((ClassLoader) null);
    runScript(classLoader);
  }

  static void runScript(GroovyClassLoader cl) throws ScriptException, IOException {
    GroovyShell shell = new GroovyShell(cl);
    addClasspath(cl,"groovy-4.0.26.jar");
    addClasspath(cl,"groovy-ginq-4.0.26.jar");
    addClasspath(cl,"groovy-macro-4.0.26.jar");

    String script = """
        def numbers = [0, 1, 2]
        println "Numbers are $numbers"
        var result = GQ {
            from n in numbers
            where n > 0
            select n
        }
        println "Ginq result is $result"
        """;
    shell.evaluate(script);
  }
  static void addClasspath(GroovyClassLoader classLoader, String path) throws IOException {
    String projectDir = System.getProperty("projectDir", "NoParentCL");
    File libDir = new File(projectDir, "lib/");
    File jar = new File(libDir, path);
    if (!jar.exists()) {
      throw new IOException("Jar file does not exist: " + jar.getCanonicalPath());
    }
    //System.out.println("Adding jar " + jar.getCanonicalPath());
    classLoader.addClasspath(jar.getCanonicalPath());
  }
}