package fr.umlv.ir3.corba.manager.client;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import opencard.core.util.HexString;

import org.omg.CORBA.ORB;
import org.omg.CORBA.ORBPackage.InvalidName;
import org.omg.CosNaming.NameComponent;
import org.omg.CosNaming.NamingContextExt;
import org.omg.CosNaming.NamingContextExtHelper;
import org.omg.CosNaming.NamingContextPackage.CannotProceed;
import org.omg.CosNaming.NamingContextPackage.NotFound;
import fr.umlv.ir3.corba.manager.AppletManager;
import fr.umlv.ir3.corba.manager.AppletManagerHelper;
import fr.umlv.ir3.corba.manager.ManagerException;


public class AppletManagerClient {
	private AppletManager appletManager;
	
	/** Constructor 
	 * @throws InvalidName 
	 * @throws InvalidName 
	 * @throws org.omg.CosNaming.NamingContextPackage.InvalidName 
	 * @throws org.omg.CosNaming.NamingContextPackage.InvalidName 
	 * @throws CannotProceed 
	 * @throws NotFound 
	 * @throws CannotProceed 
	 * @throws NotFound 
	 * @throws ManagerException */
	public AppletManagerClient() throws InvalidName, org.omg.CosNaming.NamingContextPackage.InvalidName, NotFound, CannotProceed, ManagerException  {
		
		Properties props = new Properties();
		props.put("org.omg.CORBA.ORBInitialHost","localhost");
		props.put("org.omg.CORBA.ORBInitialPort","1234");
		ORB orb = ORB.init(new String[1],props);
		
		org.omg.CORBA.Object o;
		o = orb.resolve_initial_references("NameService");
		NamingContextExt context = NamingContextExtHelper.narrow(o);
		NameComponent [] name = context.to_name("manager");
		appletManager = AppletManagerHelper.narrow(context.resolve(name));
		
		String appletId = "A000000002";
		byte[] applet = HexString.parseHexString(appletId);

		String appletInst = "A00000000201";
		byte[] appletInstb = HexString.parseHexString(appletId);

		
		File cap = new File("/classes/fr/umlv/ir3/corba/calculator/applet/javacard/applet.ijc");
		FileInputStream in;
		byte[] buf=null;;
		
		try {
			in = new FileInputStream(cap);
			
			buf = new byte[(int) cap.length()];
			in.read(buf);
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		appletManager.load(buf,buf.length,appletId);
		
		appletManager.delete(appletInstb);
		appletManager.delete(applet);
		
	}
	
	
	public static void main(String[] args) {
		
		try {		
			AppletManagerClient client  = new AppletManagerClient();
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
		} catch (ManagerException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
	}
}