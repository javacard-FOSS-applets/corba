${interface.package}.server;

import java.util.Enumeration;
import java.util.Properties;

import opencard.cflex.service.CFlex32CardService;
import opencard.core.service.CardRequest;
import opencard.core.service.CardServiceException;
import opencard.core.service.SmartCard;
import opencard.core.terminal.CardTerminal;
import opencard.core.terminal.CardTerminalException;
import opencard.core.terminal.CardTerminalRegistry;
import opencard.core.util.HexString;
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
import org.omg.PortableServer.Servant;
import org.omg.PortableServer.POAManagerPackage.AdapterInactive;
import org.omg.PortableServer.POAPackage.ObjectNotActive;
import org.omg.PortableServer.POAPackage.ServantAlreadyActive;
import org.omg.PortableServer.POAPackage.WrongPolicy;

<#list generatorInterfaces as interface>
import ${interface.package.name}.${interface.simpleName}Impl;
</#list>


/**
 * This class provides an implementation of corba server using a distant calculator applet in card terminal 
 * @author BARBISAN Laurent, BOITEL Olivier, GUILLON Denis, LAMPS S�bastien 
 */
public class Server {
	//CORBA -ORB
	private ORB orb;
	//Applets
	<#list generatorInterfaces as interface>
	private ${interface.simpleName}Impl proxy${interface.simpleName};
	</#list>
    
	//JavaCard Manager
	private CFlex32CardService javacard;	

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
	public Server() 
			throws InvalidName, ServantAlreadyActive, WrongPolicy,
			ObjectNotActive, org.omg.CosNaming.NamingContextPackage.InvalidName,
			NotFound, CannotProceed, AdapterInactive, 
			OpenCardPropertyLoadingException, CardServiceException, 
			CardTerminalException, ClassNotFoundException {
			
		//FIXME: Used file properties		
		Properties props = new Properties();
		props.put("org.omg.CORBA.ORBInitialHost","localhost");
		props.put("org.omg.CORBA.ORBInitialPort",1234);
		ORB orb = ORB.init((String[])null,props);

		org.omg.CORBA.Object o = orb.resolve_initial_references("NameService");
		NamingContextExt context = NamingContextExtHelper.narrow(o);
		POA root =  POAHelper.narrow(orb.resolve_initial_references("RootPOA"));


		<#list generatorInterfaces as interface>
		proxy${interface.simpleName} = new ${interface.simpleName}Impl(javacard);
		bindObject(proxy${interface.simpleName}, root, context);
		</#list>
		
		root.the_POAManager().activate();
	}

	private void bindObject(Servant bindObject, POA root, NamingContextExt contextExt)
			throws ServantAlreadyActive, WrongPolicy, ObjectNotActive, 
			org.omg.CosNaming.NamingContextPackage.InvalidName, 
			NotFound, CannotProceed
	{        
		byte [] id = root.activate_object(bindObject);
		org.omg.CORBA.Object ref = root.id_to_reference(id);
		
		NameComponent [] name = contextExt.to_name(bindObject.getClass().getSimpleName());
		try {
			contextExt.bind(name,ref);
		} catch (AlreadyBound e) {
			contextExt.rebind(name,ref);
		}
	}
	
    /**
     * Starts a calculator applet server instance
     */
	public void start()
			throws OpenCardPropertyLoadingException, 
			CardServiceException, CardTerminalException, 
			ClassNotFoundException {
		CardTerminal cardTerminal  = selectLastTerminal();
		CardRequest cardRequest = openCardAccess(cardTerminal);
		//FIXME : Accès concurents sur plusieurs applet
		selectApplet("${interface.appletID}", cardRequest);
		orb.run();
	}
    /**
     * Stops a calculator applet server instance
     */  
    public void stop() {
        closeCardAccess();
    	orb.destroy();     
    }   
     
     /**
     * Select the specified applet on the javacard
     * @param appletId applet id
     * @param cardRequest cardresquest to find card
     */
    public void selectApplet(String appletId, CardRequest cardRequest)
    		throws OpenCardPropertyLoadingException, CardServiceException, 
    		CardTerminalException, ClassNotFoundException 
    {
        SmartCard smartCard = SmartCard.waitForCard(cardRequest);
        
        if(smartCard==null)
        {
        	throw new NullPointerException("Error when waiting for card to become ready");
        }
        
    	//Test application
        javacard = (CFlex32CardService) smartCard.getCardService(CFlex32CardService.class, true);
        javacard.selectApplication(HexString.parseHexString(appletId));
    	javacard.allocateChannel();

    	
    	//Start card service
        if (!SmartCard.isStarted()) {
          SmartCard.start();
        }
    }
    
     /**
     * //TODO: completer les exceptions
     * Initializes javacard card access
     * @throws ClassNotFoundException ?
     * @throws CardTerminalException ?
     * @throws CardServiceException ?
     * @throws OpenCardPropertyLoadingException ?
     */
    private CardRequest openCardAccess(CardTerminal terminal) 
    			throws CardTerminalException, CardServiceException, 
    			ClassNotFoundException, OpenCardPropertyLoadingException{
    	CardRequest cardRequest;
    	
    	//Wait for insert card
        if(terminal.isCardPresent(0)==false)
        {
        	System.out.println("Re-insert/Insert your card ...");
        	cardRequest = new CardRequest(CardRequest.NEWCARD, terminal, null);	
        }
        else
        {
        	cardRequest = new CardRequest(CardRequest.ANYCARD, terminal, null);
        }

        return cardRequest;
	}
    
    /**
     * Closes access applet channel
     */
    public void closeCardAccess(){
        javacard.releaseChannel();
    }
    
    /**
     *
     * Select the last Terminal on the computer
     * @return
     * @throws OpenCardPropertyLoadingException
     * @throws CardServiceException
     * @throws CardTerminalException
     * @throws ClassNotFoundException
     */
    //TODO : Complete javadoc
    public CardTerminal selectLastTerminal() 
    		throws OpenCardPropertyLoadingException, CardServiceException, 
    		CardTerminalException, ClassNotFoundException{
        CardTerminal cardTerminal=null;
    	
    	//Try to starrt service
        if (SmartCard.isStarted() == false) {
          SmartCard.start();
        }
        
    	//List terminal on the computer
    	CardTerminalRegistry ctr = CardTerminalRegistry.getRegistry();
            	
    	for (Enumeration terminals = ctr.getCardTerminals();terminals.hasMoreElements();) {
    		cardTerminal = (CardTerminal) terminals.nextElement(); 
        }

    	if(cardTerminal==null)
    	{
    		throw new NullPointerException("Couldn't not retrieve a card reader");
    	}
    	
    	return cardTerminal;
    }
    
    /**
     * This method launchs a calculator corba server
     * @param args not used
     */
    public static void main(String[] args) {
        try {
            //initialize server
            Server server = new Server();
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