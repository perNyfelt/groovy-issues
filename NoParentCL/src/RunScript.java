import groovy.lang.GroovyClassLoader;
import groovy.lang.GroovyShell;

import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import javax.script.ScriptEngine;
import javax.script.ScriptException;
import java.io.File;
import java.io.IOException;

class RunScript {

  public static void main(String[] args) {
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

    System.out.println("Running script using classloader with parent");
    runScriptInShell(new GroovyClassLoader(), script);

    System.out.println("Running script using classloader without parent");
    runScriptInShell(new GroovyClassLoader((ClassLoader) null), script);

    System.out.println("-------------------------------------");
    System.out.println("Running script in jsr223 using classloader with parent");
    runScriptJsr223(new GroovyClassLoader(), script);

    System.out.println("Running script in jsr223 using classloader with bootstrap");
    ClassLoader bootstrapLoader = ClassLoader.getSystemClassLoader().getParent();
    checkThatClassloaderHasNoGroovy(getBootStrapClassloader());
    runScriptJsr223(new GroovyClassLoader(bootstrapLoader), script);
    
    //System.out.println("Running script in jsr223 using classloader without parent");
    //runScriptJsr223(new GroovyClassLoader((ClassLoader) null), script);
  }

  static ClassLoader getBootStrapClassloader() {
     ClassLoader bootstrapLoader = ClassLoader.getSystemClassLoader();
     while (bootstrapLoader.getParent() != null) {
       bootstrapLoader = bootstrapLoader.getParent();
     }
     return bootstrapLoader;
  }

  private static void checkThatClassloaderHasNoGroovy(ClassLoader cl) {
    try {
      cl.loadClass("groovy.lang.GroovyShell");
      throw new RuntimeException("Classloader has groovy, NOT what we expected");
    } catch (ClassNotFoundException e) {
      System.out.println("Classloader has no groovy");
    }
  }

  static void runScriptJsr223(GroovyClassLoader cl, String script) {
    try {
      addClasspath(cl, "groovy-4.0.26.jar");
      addClasspath(cl, "groovy-ginq-4.0.26.jar");
      addClasspath(cl, "groovy-macro-4.0.26.jar");
      addClasspath(cl, "groovy-jsr223-4.0.26.jar");
      // This is the problem, we cannot instantiate the GroovyShell in the parent classloader unless
      // the classloader includes the parent classloader.
      //GroovyShell shell = new GroovyShell(cl);
      evalInScriptEngine(cl, script);
      //shell.evaluate(script);
      System.out.println("Script ran successfully");
    } catch (Exception e) {
      System.err.println("Failed to run script: " + e);
    }
  }

  static void runScriptInShell(GroovyClassLoader cl, String script) {
    try {
      addClasspath(cl, "groovy-4.0.26.jar");
      addClasspath(cl, "groovy-ginq-4.0.26.jar");
      addClasspath(cl, "groovy-macro-4.0.26.jar");
      // This is the problem, we cannot instantiate the GroovyShell in the parent classloader unless
      // the classloader includes the parent classloader.
      //GroovyShell shell = new GroovyShell(cl);
      //shell.evaluate(script);
      // instead we use reflection
      evalInGroovyShell(cl, script);
      System.out.println("Script ran successfully");
    } catch (Exception e) {
      System.err.println("Failed to run script: " + e);
    }
  }

  static void evalInGroovyShell(GroovyClassLoader cl, String script)
      throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException,
      InstantiationException, IllegalAccessException {
    var c = cl.loadClass("groovy.lang.GroovyShell");
    var shell = c.getDeclaredConstructor().newInstance();
    c.getMethod("evaluate", String.class).invoke(shell, script);
  }

  static void evalInScriptEngine(GroovyClassLoader cl, String script)
      throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException,
      IllegalAccessException, InstantiationException, ScriptException {
    var c = cl.loadClass("org.codehaus.groovy.jsr223.GroovyScriptEngineImpl");
    var engine = c.getDeclaredConstructor().newInstance();
    c.getMethod("eval", String.class).invoke(engine, script);
  }

  static void addClasspath(GroovyClassLoader classLoader, String path) throws IOException {
    //System.out.println("Adding jar " + jar.getCanonicalPath());
    classLoader.addClasspath(getFile(path).getCanonicalPath());
  }

  static File getFile(String path) throws IOException {
    String projectDir = System.getProperty("projectDir", "NoParentCL");
    File libDir = new File(projectDir, "lib/");
    File jar = new File(libDir, path);
    if (!jar.exists()) {
      throw new IOException("Jar file does not exist: " + jar.getCanonicalPath());
    }
    return jar;
  }
}