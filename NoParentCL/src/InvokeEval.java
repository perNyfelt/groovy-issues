import groovy.lang.GroovyClassLoader;
import java.io.File;
import java.io.IOException;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;

public class InvokeEval {

  static MethodHandles.Lookup lookup;
  static Class<?> engineClass;

  public static void main(String[] args) throws Throwable {
    var classLoader = new GroovyClassLoader();
    addClasspath(classLoader, "groovy-jsr223-4.0.26.jar");
    lookup = MethodHandles.publicLookup();
    engineClass = classLoader.loadClass("org.codehaus.groovy.jsr223.GroovyScriptEngineImpl");
    var engine = engineClass.getDeclaredConstructor().newInstance();

    MethodHandle eval = getHandle(Object.class, "eval", String.class);
    System.out.println(eval);
    eval.invoke(engine, "println 'Hello'");
  }

  static MethodHandle getHandle(Class<?> returnType, String method, Class<?>... params) {
    try {
      var methodType = MethodType.methodType(returnType, params);
      return lookup.findVirtual(engineClass, "eval", methodType);
    } catch (NoSuchMethodException | IllegalAccessException e) {
      throw new RuntimeException("Failed to invoke '" + method + "' method on engine", e);
    }
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
