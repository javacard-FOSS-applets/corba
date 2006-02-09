#!/bin/bash
LD_LIBRARY_PATH=./lib
CLASSPATH=./classes:./lib/base-core.jar:./lib/base-opt.jar:./lib/cryptix-jce-api.jar:./lib/cryptix-jce-provider.jar:./lib/pcsc-wrapper-2.0.jar
export LD_LIBRARY_PATH CLASSPATH
java opencard.cflex.app.LoadCFlexAccess32 $*
