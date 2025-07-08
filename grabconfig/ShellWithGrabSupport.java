import groovy.lang.Binding;
import groovy.util.GroovyScriptEngine;

public class ShellWithGrabSupport {
  public static void main(String[] args) throws Exception {
    GroovyScriptEngine gse = new GroovyScriptEngine(".");
    gse.run("sqltaskExample.groovy", new Binding());
  }
}
