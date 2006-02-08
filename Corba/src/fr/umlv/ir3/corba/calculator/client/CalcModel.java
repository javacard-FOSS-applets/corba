package fr.umlv.ir3.corba.calculator.client;

import java.util.Properties;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

import org.omg.CORBA.ORB;
import org.omg.CORBA.ORBPackage.InvalidName;
import org.omg.CosNaming.NameComponent;
import org.omg.CosNaming.NamingContextExt;
import org.omg.CosNaming.NamingContextExtHelper;
import org.omg.CosNaming.NamingContextPackage.CannotProceed;
import org.omg.CosNaming.NamingContextPackage.NotFound;

import fr.umlv.ir3.corba.calculator.AppletCalculator;
import fr.umlv.ir3.corba.calculator.AppletCalculatorHelper;
import fr.umlv.ir3.corba.calculator.InvalidOperator;
import fr.umlv.ir3.corba.calculator.StackOverFlow;

public class CalcModel {
	private AppletCalculator calculator;
	
	/** Constructor 
	 * @throws InvalidName 
	 * @throws InvalidName 
	 * @throws org.omg.CosNaming.NamingContextPackage.InvalidName 
	 * @throws org.omg.CosNaming.NamingContextPackage.InvalidName 
	 * @throws CannotProceed 
	 * @throws NotFound 
	 * @throws CannotProceed 
	 * @throws NotFound */
	public CalcModel() throws InvalidName, org.omg.CosNaming.NamingContextPackage.InvalidName, NotFound, CannotProceed  {
		ResourceBundle config = PropertyResourceBundle.getBundle("Config");
		String host = config.getString("host");
		String port = config.getString("port");
		String nameObject = config.getString("id");
		
		Properties props = new Properties();
		props.put("org.omg.CORBA.ORBInitialHost",host);
		props.put("org.omg.CORBA.ORBInitialPort",port);
		ORB orb = ORB.init(new String[1],props);

		org.omg.CORBA.Object o;
		o = orb.resolve_initial_references("NameService");
		NamingContextExt context = NamingContextExtHelper.narrow(o);
		NameComponent [] name = context.to_name(nameObject);
		calculator =AppletCalculatorHelper.narrow(context.resolve(name));
		System.out.println("CLIENT: " + orb.object_to_string(calculator));

	}
	
	public short getResult(char operator) throws InvalidOperator {
		return calculator.result(operator);
	}
	public void addOperand(short operand) throws StackOverFlow{
		calculator.addNumber(operand);
	}
	public void clear(){
		calculator.clear();
	}
	
}