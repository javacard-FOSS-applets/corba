package fr.umlv.ir3.corba.manager.server;

import java.util.Properties;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

import opencard.core.service.CardServiceException;
import opencard.core.terminal.CardTerminalException;
import opencard.core.util.OpenCardPropertyLoadingException;

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

import fr.umlv.ir3.corba.manager.AppletManagerImpl;

/**
 * This class provides an implementation of corba server using a distant calculator applet in card terminal 
 * @author BARBISAN Laurent, BOITEL Olivier, GUILLON Denis, LAMPS S�bastien 
 */

public class AppletManagerServer{
	private ORB orb;
	private AppletManagerImpl appletManager;
	
	/**
	 * TODO: completer les exceptions
	 * Constructs an instance of calculator applet server
	 * This method initializes javacard terminal access
	 * @throws InvalidName - if the given name is not associated with a known service
	 * @throws WrongPolicy raised if the SYSTEM_ID and RETAIN policies are not specified.
	 * @throws ServantAlreadyActive - is raised if the POA has UNIQUE_ID policy and servant is is already in the Active Object Map.
	 * @throws ObjectNotActive - if the Object Id value is not active in the POA.
	 * @throws org.omg.CosNaming.NamingContextPackage.InvalidName - Indicates the name does not identify a binding.
	 * @throws CannotProceed - Indicates that the implementation has given up for some reason. The client, however, may be able to continue the operation at the returned naming context.
	 * @throws NotFound - Indicates the name does not identify a binding.
	 * @throws AdapterInactive - is raised if the operation is invoked on the POAManager in inactive state.
	 * @throws ClassNotFoundException 
	 * @throws CardTerminalException 
	 * @throws CardServiceException 
	 * @throws OpenCardPropertyLoadingException 
	 */
	
	public AppletManagerServer() throws InvalidName, ServantAlreadyActive, WrongPolicy, ObjectNotActive, org.omg.CosNaming.NamingContextPackage.InvalidName, NotFound, CannotProceed, AdapterInactive, OpenCardPropertyLoadingException, CardServiceException, CardTerminalException, ClassNotFoundException {
		
		Properties props = new Properties();
		props.put("org.omg.CORBA.ORBInitialHost","localhost");
		props.put("org.omg.CORBA.ORBInitialPort","1234");
		this.orb = ORB.init((String[])null,props);
		orb = ORB.init(new String[1],props);
		org.omg.CORBA.Object o = orb.resolve_initial_references("NameService");
		NamingContextExt context = NamingContextExtHelper.narrow(o);
		POA root =  POAHelper.narrow(orb.resolve_initial_references("RootPOA"));
		
		try {
			appletManager = new AppletManagerImpl();
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		
		byte [] id = root.activate_object(appletManager);
		org.omg.CORBA.Object ref = root.id_to_reference(id);
		
		NameComponent [] name = context.to_name("manager");
		try {
			context.bind(name,ref);
		} catch (AlreadyBound e) {
			context.rebind(name,ref);
		}
		root.the_POAManager().activate();
	}
	
	/**
	 * Starts a calculator applet server instance
	 */
	public void start() {
		orb.run();
	}
	/**
	 * Stops a calculator applet server instance
	 */  
	public void stop() {
		appletManager.closeCardAccess();
		orb.destroy();     
	}   
	
	/**
	 * This method launchs a calculator corba server
	 * @param args not used
	 */
	public static void main(String[] args) {
		try {
			//initialize server
			AppletManagerServer server = new AppletManagerServer();
			//launch server
			System.out.println("Server Running");
			server.start();
			//stop server
			server.stop();
			System.out.println("Server stopped");  
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("Erreur: " + e.getMessage());
		}
	}
}