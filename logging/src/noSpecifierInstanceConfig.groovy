#!/usr/bin/env groovy
@Grab(group='org.apache.logging.log4j', module='log4j-api', version='2.20.0')
@Grab(group='org.apache.logging.log4j', module='log4j-core', version='2.20.0')
@Grab(group='org.apache.logging.log4j', module='log4j-slf4j-impl', version='2.20.0')

import org.apache.logging.log4j.Logger
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Level

final Logger log = LogManager.getLogger()
log.setLevel(Level.INFO)
println "Running script $this.class"
log.info('Hello world')
// will print:
// Running script class noSpecifierInstanceConfig
// 10:12:18.256 [main] INFO  org.codehaus.groovy.vmplugin.v8.IndyInterface - Hello world

