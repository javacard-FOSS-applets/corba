package fr.umlv.ir3.corba.calculator.server;

import java.util.Properties;

import org.omg.CORBA.ORB;
import org.omg.CORBA.ORBPackage.InvalidName;
import org.omg.CosNaming.NameComponent;
import org.omg.CosNaming.NamingContextExt;
import org.omg.CosNaming.NamingContextExtHelper;
import org.omg.CosNaming.NamingContextPackage.AlreadyBound;
import org.omg.CosNaming.NamingContextPackage.CannotProceed;
import org.omg.CosNaming.NamingContextPackage.NotFound;
import org.omg.PortableServer.POA;
import org.omg.PortableServer.POAHelper;
import org.omg.PortableServer.POAManagerPackage.AdapterInactive;
import org.omg.PortableServer.POAPackage.ObjectNotActive;
import org.omg.PortableServer.POAPackage.ServantAlreadyActive;
import org.omg.PortableServer.POAPackage.WrongPolicy;

import fr.umlv.ir3.corba.calculator.impl.CalculatorImpl;

public class Server{
	public Server() throws InvalidName, ServantAlreadyActive, WrongPolicy, org.omg.CosNaming.NamingContextPackage.InvalidName, ObjectNotActive, NotFound, CannotProceed, AdapterInactive {
		String host = "localhost";
		String port = "1234";
		String nameObject   = "calculator";
		
		Properties props = new Properties();
		props.put("org.omg.CORBA.ORBInitialHost",host);
		props.put("org.omg.CORBA.ORBInitialPort",port);
		ORB orb = ORB.init(new String[1],props);
		org.omg.CORBA.Object o = orb.resolve_initial_references("NameService");
		NamingContextExt context = NamingContextExtHelper.narrow(o);
		
		POA root =  POAHelper.narrow(orb.resolve_initial_references("RootPOA"));
		CalculatorImpl calculator = new CalculatorImpl();
		byte [] id = root.activate_object(calculator);
		org.omg.CORBA.Object ref = root.id_to_reference(id);
		
		NameComponent [] name = context.to_name(nameObject);
		try {
			context.bind(name,ref);
		} catch (AlreadyBound e) {
			context.rebind(name,ref);
		}
		root.the_POAManager().activate();
		System.out.println("Server Running");
		orb.run();
		System.out.println("Server stopped");
	}        
}