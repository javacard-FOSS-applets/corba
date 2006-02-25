${interface.package}.client;

import java.util.Properties;

import org.omg.CORBA.ORB;
import org.omg.CORBA.ORBPackage.InvalidName;
import org.omg.CosNaming.NameComponent;
import org.omg.CosNaming.NamingContextExt;
import org.omg.CosNaming.NamingContextExtHelper;
import org.omg.CosNaming.NamingContextPackage.CannotProceed;
import org.omg.CosNaming.NamingContextPackage.NotFound;

import ${interface.package.name}.${interface.simpleName};
import ${interface.package.name}.${interface.simpleName}Helper;


/**
 * Client for Client${interface.simpleName}
 * @author generator
 *
 */
public class Client${interface.simpleName} {
	private ${interface.simpleName} implementation;
	
	/** Constructor 
	 * @throws InvalidName name specified not found
	 * @throws CannotProceed can't execute process
	 * @throws NotFound can't find requierd object on server
	 */
	public Client${interface.simpleName}() throws InvalidName, NotFound, CannotProceed, org.omg.CosNaming.NamingContextPackage.InvalidName {

		//FIXME: Used file properties		
		Properties props = new Properties();
		props.put("org.omg.CORBA.ORBInitialHost","localhost");
		props.put("org.omg.CORBA.ORBInitialPort","1234");
		ORB orb = ORB.init((String[])null,props);

		org.omg.CORBA.Object o;
		o = orb.resolve_initial_references("NameService");
		NamingContextExt context = NamingContextExtHelper.narrow(o);
		NameComponent [] name = context.to_name("proxy${interface.simpleName}");
		implementation = (${interface.simpleName}) ${interface.simpleName}Helper.narrow(context.resolve(name));
		//TODO: Add code here
	}
	
	/**
	 * @return Returns the implementation.
	 */
	public ${interface.simpleName} getImplementation() {
		return implementation;
	}
	
    
    /**
	 * Main method
	 * @param args command line argument
	 * @throws InvalidName name specified not found
	 * @throws CannotProceed can't execute process
	 * @throws NotFound can't find requierd object on server
	 */
	public static void main(String[] args)  throws InvalidName, NotFound, CannotProceed, org.omg.CosNaming.NamingContextPackage.InvalidName {
		Client${interface.simpleName} client${interface.simpleName} = new Client${interface.simpleName}();
		//TODO: Add code here
    }
}