package fr.umlv.ir3.corba.manager.client;

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

import fr.umlv.ir3.corba.manager.AppletManagerHelper;
import fr.umlv.ir3.corba.manager.AppletManagerOperations;

public class AppletManagerClient {
	private AppletManagerOperations applet;
	
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
		applet=(AppletManagerOperations) AppletManagerHelper.narrow(context.resolve(name));
	}
	
	
	public static void main(String[] args) {
	}
}