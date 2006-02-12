echo ====== Load applet on javacard ======
./run.sh -load classes/fr/umlv/ir3/corba/calculator/applet/javacard/applet.ijc -package_id A000000002 -applet_id A00000000201
echo ====== install applet on javacard ======
./run.sh -install 500 -package_id A000000002 -applet_id A00000000201
echo ====== Finish ======
