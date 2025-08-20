import groovy.transform.Field
import groovy.transform.SourceURI

@SourceURI
@Field
URI sourceUri

File.metaClass.div = { String path -> new File(delegate, path) }

def scriptDir = new File(sourceUri).parentFile
def fooDir = scriptDir / 'Foo'
println "Foo is here: $fooDir"