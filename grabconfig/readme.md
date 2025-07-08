# GrabConfig from GroovyScriptEngine

The `sqltaskExample.groovy` works fine using `groovy sqltaskExample.groovy` on the command line.

However, when using the GroovyScriptEngine, it fails with a `General error during conversion: No suitable ClassLoader found for grab`.
This is due to using @GrabConfig(systemClassLoader=true) in the groovy script.
Since groovy and ant are part of the system classpath, we must use GrabConfig to make the jdbc driver
visible to the ant task. The question is how to make that work... 