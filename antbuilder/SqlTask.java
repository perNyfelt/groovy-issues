import groovy.lang.GroovyShell;
import java.io.File;
import groovy.lang.GroovyClassLoader;

public class SqlTask {

  public static void main(String[] args) throws Exception {
    GroovyShell groovyShell = new GroovyShell();
    File file = new File("sqltask.groovy");
    groovyShell.evaluate(file);
  }
}