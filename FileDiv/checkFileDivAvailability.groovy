import org.codehaus.groovy.runtime.m12n.ExtensionModuleRegistry
import groovy.lang.MetaClassRegistry

// Force-load all modules from the service loader
def registry = ExtensionModuleRegistry.getRegistry()
registry.findModule('groovy-nio', '4.0.28')?.enable()

// Test File /
def scriptDir = new File(getClass().protectionDomain.codeSource.location.path).parentFile
def fooDir = scriptDir / 'Foo'
println "Foo is here: $fooDir"