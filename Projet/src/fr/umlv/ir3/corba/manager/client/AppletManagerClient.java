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
import fr.umlv.ir3.corba.calculator.CardException;
import fr.umlv.ir3.corba.calculator.InitializationException;
import fr.umlv.ir3.corba.calculator.InvalidOperator;
import fr.umlv.ir3.corba.calculator.StackOverFlow;

public class AppletManagerClient {
	private AppletManager applet;
	
	/** Constructor 
	 * @throws InvalidName 
	 * @throws InvalidName 
	 * @throws org.omg.CosNaming.NamingContextPackage.InvalidName 
	 * @throws org.omg.CosNaming.NamingContextPackage.InvalidName 
	 * @throws CannotProceed 
	 * @throws NotFound 
	 * @throws CannotProceed 
	 * @throws NotFound */
	public AppletManagerClient() throws InvalidName, org.omg.CosNaming.NamingContextPackage.InvalidName, NotFound, CannotProceed  {
		ResourceBundle config = PropertyResourceBundle.getBundle("config");
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
		applet=AppletManagerHelper.narrow(context.resolve(name));
	}
	
	public void clear() throws CardException, InitializationException{
		applet.clear();
	}
	
	public static void main(String[] args) {
		try {
			
		} catch (InvalidName e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (org.omg.CosNaming.NamingContextPackage.InvalidName e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NotFound e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (CannotProceed e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (StackOverFlow e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (CardException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InitializationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidOperator e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}