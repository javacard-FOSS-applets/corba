${interface.package}.client;

import java.applet.Applet;
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

/**
 * Client for Client${interface.simpleName}
 * @author generator
 *
 */
public class Client${interface.simpleName} {
	private ${interface.simpleName}Impl implementation;
	
	/** Constructor 
	 * @throws InvalidName name specified not found
	 * @throws CannotProceed can't execute process
	 * @throws NotFound can't find requierd object on server
	 */
	public Client${interface.simpleName}() throws InvalidName, NotFound, CannotProceed  {

		//FIXME: Used file properties		
		Properties props = new Properties();
		props.put("org.omg.CORBA.ORBInitialHost","localhost");
		props.put("org.omg.CORBA.ORBInitialPort",1234);
		ORB orb = ORB.init((Applet)null,props);

		org.omg.CORBA.Object o;
		o = orb.resolve_initial_references("NameService");
		NamingContextExt context = NamingContextExtHelper.narrow(o);
		NameComponent [] name = context.to_name(this.getClass().getSimpleName());
		Implementation = Client${interface.simpleName}Helper.narrow(context.resolve(name));
	}
    
	public static void main(String[] args) {
		Client${interface.simpleName} client${interface.simpleName} = new Client${interface.simpleName}();
    }
}