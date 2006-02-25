#!/bin/bash
LD_LIBRARY_PATH=./lib
CLASSPATH=./classes:./lib/base-core.jar:./lib/base-opt.jar:./lib/cryptix-jce-api.jar:./lib/cryptix-jce-provider.jar:./lib/pcsc-wrapper-2.0.jar
export LD_LIBRARY_PATH CLASSPATH
javac -d classes -classpath ./classes:./lib/base-core.jar:./lib/base-opt.jar:./lib/cryptix-jce-api.jar:./lib/cryptix-jce-provider.jar:./lib/pcsc-wrapper-2.0.jar ./src/fr/umlv/ir3/corba/client/RPNClient.java
java -cp ./classes:./lib/base-core.jar:./lib/base-opt.jar:./lib/cryptix-jce-api.jar:./lib/cryptix-jce-provider.jar:./lib/pcsc-wrapper-2.0.jar fr.umlv.ir3.corba.calculator.client.RPNClient $*
