#!/usr/bin/env bash

gl="$GROOVY_HOME/lib"
v="4.0.27"
cp="$gl/groovy-$v.jar:$gl/groovy-ant-$v.jar:$gl/groovy-ant-$v.jar:$gl/ant-1.10.15.jar:\
$gl/ant-launcher-1.10.15.jar:$gl/ivy-2.5.3.jar"
java -cp "$cp" ShellWithGrabSupport.java