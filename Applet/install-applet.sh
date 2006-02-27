clear
echo =========================L O A D  A P P L E T=========================
./run.sh -load classes/`python -c "import sys
packages =  sys.argv[1].split(\".\")
print \"/\".join(packages)" $4`/javacard/applet.ijc -package_id A0000000$3 -applet_id A0000000$3$1
echo =====================I N S T A L L  A P P L E T=======================
./run.sh -install 800 -package_id A0000000$3 -applet_id A0000000$3$1
echo ========================== T E R M I N E=======================

