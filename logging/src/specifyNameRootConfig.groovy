#!/usr/bin/env groovy
@Grab(group='org.apache.logging.log4j', module='log4j-api', version='2.20.0')
@Grab(group='org.apache.logging.log4j', module='log4j-core', version='2.20.0')
@Grab(group='org.apache.logging.log4j', module='log4j-slf4j-impl', version='2.20.0')

import groovy.transform.Field
import org.apache.logging.log4j.Logger
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Level
import org.apache.logging.log4j.core.config.Configurator

Configurator.setRootLevel(Level.INFO)

@Field
final Logger log = LogManager.getLogger(this.class.name)
println "Running script $this.class"
log.info('Hello world')
// will print
// 10:06:37.605 [main] INFO  specifyNameRootConfig - Hello world
logInMethod()

void logInMethod() {
  log.info('Hello world from a method')
  // will print
  // 10:06:37.607 [main] INFO  specifyNameRootConfig - Hello world from a method
}