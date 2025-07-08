import groovy.lang.GroovyShell;
import groovy.lang.Binding;
import groovy.lang.GroovyClassLoader;
import org.codehaus.groovy.control.CompilerConfiguration;
import java.io.File;
import java.net.URLClassLoader;

public class ShellWithGrabSupport {
  public static void main(String[] args) throws Exception {
    CompilerConfiguration config = new CompilerConfiguration();

    // Use system classloader or a shared classloader
    GroovyClassLoader groovyClassLoader = new GroovyClassLoader(ClassLoader.getSystemClassLoader(), config);

    Binding binding = new Binding();
    GroovyShell shell = new GroovyShell(groovyClassLoader, binding, config);

    File script = new File("sqltaskExample.groovy");

    shell.evaluate(script);
  }
}
