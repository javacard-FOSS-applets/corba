clear
echo =========================C O M P I L A T I O N=========================
./compile.sh src/`python -c "import sys
packages =  sys.argv[1].split(\".\")
print \"/\".join(packages)" $4`/$2.java 				
./converter.sh 0xa0:0x0:0x0:0x0:0x$3:0x$1 $4.$2 0xa0:0x0:0x0:0x0:0x$3 $4 
echo =============================T E R M I N E=============================
				
				
