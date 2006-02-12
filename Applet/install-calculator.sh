./run.sh -delete A00000000201
./run.sh -delete A000000002
./run.sh -load classes/fr/umlv/ir3/corba/calculator/applet/javacard/applet.ijc -package_id A000000002 -applet_id A00000000201
./run.sh -install 500 -package_id A000000002 -applet_id A00000000201
./run.sh -status
./run.sh -status 40

