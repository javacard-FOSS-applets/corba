${interface.package};

import java.util.Enumeration;
import opencard.cflex.service.CFlex32CardService;
import opencard.core.service.CardRequest;
import opencard.core.service.CardServiceException;
import opencard.core.service.SmartCard;
import opencard.core.terminal.CardTerminal;
import opencard.core.terminal.CardTerminalException;
import opencard.core.terminal.CardTerminalRegistry;
import opencard.core.terminal.ResponseAPDU;
import opencard.core.util.HexString;
import opencard.core.util.OpenCardPropertyLoadingException;
import opencard.opt.terminal.ISOCommandAPDU;
import fr.umlv.ir3.corba.calculator.AppletCalculatorPOA;
import fr.umlv.ir3.corba.calculator.CardException;
import fr.umlv.ir3.corba.calculator.InitializationException;
import fr.umlv.ir3.corba.calculator.InvalidOperator;
import fr.umlv.ir3.corba.calculator.StackOverFlow;

/**
 * This class provides an implementation of ${interface.simpleName}POA servant corba object using a distant 
 * applet in card terminal.
 * @author CorbaCard generator
 */
public class ${interface.simpleName}Impl extends ${interface.simpleName}POA{
	//This applet is designed to respond to the following
	//class of instructions.
	final static byte ${interface.simpleName}Applet_CLA = (byte)${interface.applet_CLA};
	
	//Instruction set for ${interface.simpleName}Applet
	<#list interface.getDeclaredMethods() as method>
	private byte ${method.name} = 0x10;
	</#list>
	
	//JavaCard Manager
	private CFlex32CardService javacard;	
	//Repesentation of terminal
	private CardTerminal terminal=null;
	//Representation of Card
	private SmartCard sm = null;
	//Object used to send data to card
	private CardRequest cr = null;
	
    /**
     * //TODO: completer les exceptions
     * Constructs an instance of ${interface.simpleName} applet client by initializing Java Card access service
     * This method initializes javacard terminal access
     * @throws ClassNotFoundException ?
     * @throws CardTerminalException ?
     * @throws CardServiceException ?
     * @throws OpenCardPropertyLoadingException ?
     */
	public ${interface.simpleName}Impl() throws OpenCardPropertyLoadingException, CardServiceException, CardTerminalException, ClassNotFoundException{
		super();
        initTerminal();
	}
    
    /**
     * //TODO: completer les exceptions
     * Initializes javacard card access
     * @param appletId - id of applet in the Java card
     * @throws ClassNotFoundException ?
     * @throws CardTerminalException ?
     * @throws CardServiceException ?
     * @throws OpenCardPropertyLoadingException ?
     */
    public void initCardAccess(String appletId) throws CardTerminalException, CardServiceException, ClassNotFoundException, OpenCardPropertyLoadingException{
    	//Wait for insert card
        if(terminal.isCardPresent(0)==false)
        {
        	System.out.println("Re-insert/Insert your card ...");
        	cr = new CardRequest(CardRequest.NEWCARD, terminal, null);	
        }
        else
        {
        	cr = new CardRequest(CardRequest.ANYCARD, terminal, null);
        }
        sm = SmartCard.waitForCard(cr);
        
        if(sm==null)
        {
        	throw new NullPointerException("Error when waiting for card to become ready");
        }

    	//Test application
        javacard = (CFlex32CardService) sm.getCardService(CFlex32CardService.class, true);
        javacard.selectApplication(HexString.parseHexString("A00000000201"));
        
    	javacard.allocateChannel();

    	
    	//Start card service
        if (!SmartCard.isStarted()) {
          SmartCard.start();
        }
        
        //DEBUG: List terminal on the computer
        printDebug(CardTerminalRegistry.getRegistry());
	}
    
    /**
     * Closes access applet channel
     */
    public void closeCardAccess(){
        javacard.releaseChannel();
    }
    
    /************************************************ Private methods *******************************************************************/
    private void initTerminal() throws OpenCardPropertyLoadingException, CardServiceException, CardTerminalException, ClassNotFoundException{
        
    	//Try to starrt service
        if (SmartCard.isStarted() == false) {
          SmartCard.start();
        }
        
    	//List terminal on the computer
    	CardTerminalRegistry ctr = CardTerminalRegistry.getRegistry();
            	
    	for (Enumeration terminals = ctr.getCardTerminals();terminals.hasMoreElements();) {
          terminal = (CardTerminal) terminals.nextElement(); 
          //TODO : Trouver une solution pous élégante
        }

    	if(terminal==null)
    	{
    		//TODO : Remonter les exception correctement
    		throw new NullPointerException("Couldn't not retrieve a card reader");
    	}
    }
    
    private ResponseAPDU sendCommand(byte command, byte value) throws CardTerminalException, InitializationException{
        byte[] values = new byte[1];
        values[0] = value;
    	return javacard.sendAPDU(new ISOCommandAPDU(${interface.simpleName}_CLA,command,(byte)0,(byte)0,values,values.length));
    }
    
    private ResponseAPDU sendCommand(byte command) throws CardTerminalException, InitializationException{
    	return javacard.sendAPDU(new ISOCommandAPDU(${interface.simpleName}_CLA,command,(byte)0,(byte)0,new byte[0],0));
    }
}
