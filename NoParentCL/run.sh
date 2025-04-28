#!/usr/bin/env bash
SCRIPT_DIR="$(cd -- "$(dirname -- "${BASH_SOURCE[0]}")" &>/dev/null && pwd)"
java -DprojectDir=$SCRIPT_DIR -cp $SCRIPT_DIR/lib/groovy-4.0.26.jar $SCRIPT_DIR/src/RunScript.java