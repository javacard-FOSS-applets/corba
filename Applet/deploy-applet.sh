if [ $# != 4 ]
then
echo "usage : <id applet> <class applet> <id package> <class package>"
echo "exemple : 01 CalculatorRPNApplet 02 fr.umlv.ir3.corba.calculator.applet"
exit
fi

./compile-and-convert-applet.sh $1 $2 $3 $4 
echo press enter to continue > /dev/stderr
read key

./delete-applet.sh $1 $2 $3 $4
echo press enter to continue > /dev/stderr
read key

./install-applet.sh $1 $2 $3 $4
echo press enter to continue > /dev/stderr
read key