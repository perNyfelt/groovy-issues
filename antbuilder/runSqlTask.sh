#!/usr/bin/env bash
gl="${GROOVY_HOME}/lib"
cp="$gl/groovy-4.0.27.jar:$gl/ant-1.10.15.jar:$gl/ant-launcher-1.10.15.jar:$gl/groovy-ant-4.0.27.jar:$gl/ivy-2.5.3.jar"

java -DGROOVY_HOME=${GROOVY_HOME} \
-cp "$cp" \
SqlTask.java