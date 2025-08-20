File.metaClass.div = { String path -> new File(delegate, path) }
def scriptDir = new File(getClass().protectionDomain.codeSource.location.path).parentFile
def fooDir = scriptDir / 'Foo'
println "Foo is here: $fooDir"