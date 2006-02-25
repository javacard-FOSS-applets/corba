#!/bin/bash

echo javac -d classes -classpath java_card_kit-2_1_2/lib/api21.jar -target 1.1 -source 1.2 -g -encoding ISO8859-1 $*
javac -d classes -classpath java_card_kit-2_1_2/lib/api21.jar -target 1.1 -source 1.2 -g -encoding ISO8859-1 $*
echo Done


