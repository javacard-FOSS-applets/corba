#!/bin/bash
if [[ $# != 4 ]]
then
    echo converter.sh '<applet_id> <applet_class> <pkg_id> <pkg_name>'
    exit 1
fi

echo converter -nobanner -exportpath ./export/ -out EXP CAP -applet $1 $2 $4 $3 1.0
JC21_HOME=./java_card_kit-2_1_2
JC_PATH=$JC21_HOME/lib/apdutool.jar:$JC21_HOME/lib/apduio.jar:$JC21_HOME/lib/converter.jar:$JC21_HOME/lib/jcwde.jar:$JC21_HOME/lib/scriptgen.jar:$JC21_HOME/lib/offcardverifier.jar:$JC21_HOME/lib/api21.jar:$JC21_HOME/lib/capdump.jar:$CLASSPATH
JFLAGS="-classpath $JC_PATH"
java $JFLAGS com.sun.javacard.converter.Converter  -classdir ./classes -nobanner -exportpath java_card_kit-2_1_2/api21_export_files -out EXP CAP -applet $1 $2 $4 $3 1.0

echo

APPLET=$(echo $4 | sed 's/\./\//g')
echo ${APPLET}
CAP_DIR=${APPLET}/javacard
echo ${CAP_DIR}
CAP_PATH=${CAP_DIR}/$(basename ${APPLET})
echo ${CAP_PATH}
echo java -jar lib/captransf.jar $(find java_card_kit-2_1_2/api21_export_files -follow -name '*.exp') classes/${CAP_PATH}.cap 
java -jar lib/captransf.jar $(find java_card_kit-2_1_2/api21_export_files -follow -name '*.exp') classes/${CAP_PATH}.cap
echo
echo jar xf classes/${CAP_PATH}.cap.transf
jar xf classes/${CAP_PATH}.cap.transf
echo
echo rm classes/${CAP_PATH}.cap.transf
rm classes/${CAP_PATH}.cap.transf
echo 
echo cat ${CAP_DIR}/Header.cap ${CAP_DIR}/Directory.cap ${CAP_DIR}/Import.cap ${CAP_DIR}/Applet.cap ${CAP_DIR}/Class.cap ${CAP_DIR}/Method.cap ${CAP_DIR}/StaticField.cap ${CAP_DIR}/ConstantPool.cap ${CAP_DIR}/RefLocation.cap ${CAP_DIR}/Descriptor.cap' > classes/'$CAP_PATH.ijc

cat ${CAP_DIR}/Header.cap ${CAP_DIR}/Directory.cap ${CAP_DIR}/Import.cap ${CAP_DIR}/Applet.cap ${CAP_DIR}/Class.cap ${CAP_DIR}/Method.cap ${CAP_DIR}/StaticField.cap ${CAP_DIR}/ConstantPool.cap ${CAP_DIR}/RefLocation.cap ${CAP_DIR}/Descriptor.cap > classes/$CAP_PATH.ijc

echo
echo rm -rf ${CAP_DIR}
rm -rf ${CAP_DIR}
echo
echo Done

