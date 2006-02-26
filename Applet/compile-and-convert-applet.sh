clear
echo =========================C O M P I L A T I O N=========================
./compile.sh src/`python -c "import sys
packages =  \"/\".join(sys.argv[4].split(\".\"))
print packages + \"/\" + sys.argv[2] + \".java\"" $*` src/`python -c "import sys
packages =  \"/\".join(sys.argv[4].split(\".\"))
for clazz in sys.argv[5:] :
	print packages + \"/\" + clazz + \".java\"" $*`
./converter.sh 0xa0:0x0:0x0:0x0:0x$3:0x$1 $4.$2 0xa0:0x0:0x0:0x0:0x$3 $4 
echo =============================T E R M I N E=============================
				
				
