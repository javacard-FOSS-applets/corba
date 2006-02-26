/**
 * 
 */
package fr.umlv.ir3.corba.manager;

import java.io.ByteArrayInputStream;
import java.util.Enumeration;

import opencard.cflex.service.CFlex32CardService;
import opencard.core.event.CTListener;
import opencard.core.event.CardTerminalEvent;
import opencard.core.event.EventGenerator;
import opencard.core.service.CardRequest;
import opencard.core.service.CardServiceException;
import opencard.core.service.SmartCard;
import opencard.core.terminal.CardTerminal;
import opencard.core.terminal.CardTerminalException;
import opencard.core.terminal.CardTerminalRegistry;
import opencard.core.terminal.ResponseAPDU;
import opencard.core.util.HexString;
import opencard.core.util.OpenCardPropertyLoadingException;



/**
 * @author Olivier Boitel
 * @author Laurent Barbisan
 * @author Denis Guillon
 * @author Sebastien Lamps
 * 
 * this class is a proxy corba which allow to manage an applet on the card 
 * 
 */
public class AppletManagerImpl extends AppletManagerPOA implements CTListener{
	
	static String auth_enc_ = "404142434445464748494A4B4C4D4E4F";
	static String mac_ = auth_enc_;
	
	private SmartCard sm;
	
	private CardTerminal terminal=null;
	private CardRequest cr = null;
	private CFlex32CardService javacard;
	
	private final static Object monitor = "synchronization monitor";
	private CFlex32CardService loader = null;
	
	
	
	public AppletManagerImpl()throws Exception{
		init();
	}
	
	
	/* (non-Javadoc)
	 * @see fr.umlv.ir3.corba.manager.AppletManagerOperations#init()
	 */
	public void init() throws Exception{
		CardTerminalRegistry ctr = CardTerminalRegistry.getRegistry();
		CardTerminal terminal = null;
		CardRequest cr = null;
		
		if (SmartCard.isStarted() == false) {
			SmartCard.start();
		}
		
		for (Enumeration terminals = ctr.getCardTerminals(); terminals
		.hasMoreElements();) {
			terminal = (CardTerminal) terminals.nextElement();
			int slots = printTerminalInfos(terminal);
			for (int j = 0; j < slots; j++) {
				printSlotInfos(terminal, j);
			}
		}
		
		if (terminal == null) {
			throw new NullPointerException(
			"Couldn't not retrieve a card reader");
		}
		
		//Wait for insert card
		if (terminal.isCardPresent(0) == false) {
			System.out.println("Re-insert/Insert your card ...");
			cr = new CardRequest(CardRequest.NEWCARD, terminal, null);
		} else {
			cr = new CardRequest(CardRequest.ANYCARD, terminal, null);
		}
		sm = SmartCard.waitForCard(cr);
		
		if(sm==null)
		{
			throw new NullPointerException("Error when waiting for card to become ready");
		}
		
		EventGenerator.getGenerator().addCTListener(this);
		
	}
	
	/**
	 * @see fr.umlv.ir3.corba.manager.AppletManagerOperations#load(byte[], int, java.lang.String)
	 */
	public void load(byte[] input, int staticsize, String pakgId) throws ManagerException 
	{
		synchronized(monitor){
			try {
				
				getLoader().createSecureChannel(getAuthKey(),getMacKey());
				this.loader.installLoad(HexString.parseHexString(pakgId),new ByteArrayInputStream(input),staticsize);
				this.loader.load();
				
			}catch (Exception e) {
				throw new ManagerException("erreur pendant le chargement de l'applet");
			}
			
		}
	}
	
	/** 
	 * @see fr.umlv.ir3.corba.manager.AppletManagerOperations#install(int, java.lang.String, java.lang.String)
	 */
	public void install(int instance_size, String pakgId, String appId) throws ManagerException {
		synchronized(monitor){
			try {
				getLoader().createSecureChannel(getAuthKey(), getMacKey());
				getLoader().install(HexString.parseHexString(pakgId), HexString.parseHexString(appId), HexString.parseHexString(appId),
						instance_size);
			} catch (Exception e) {
				throw new ManagerException("erreur pendant l'installation de l'applet");
			}
		}
	}
	
	/** 
	 * @see fr.umlv.ir3.corba.manager.AppletManagerOperations#delete(byte[])
	 */
	public void delete(byte[] aid) throws ManagerException {
		synchronized(monitor){
			try {
				System.out.println("test1");
				getLoader().createSecureChannel(getAuthKey(), getMacKey());
				System.out.println("test2");
				getLoader().deleteApplication(aid);
			} catch (Exception e1) {
				e1.printStackTrace();
				throw new ManagerException("erreur pendant la suppression de l'applet");
			}
		}
	}
	
	
	/**
	 * @see fr.umlv.ir3.corba.manager.AppletManagerOperations#status(int)
	 */
	public void status(int type) throws ManagerException {
		synchronized(monitor){
			try {
				getLoader().createSecureChannel(getAuthKey(), getMacKey());
				
				ResponseAPDU res = getLoader().status(type);
				System.out
				.println("Response status : " + HexString.hexify(res.sw1())
						+ HexString.hexify(res.sw2()));
				byte[] data = res.data();
				if (data == null)
					return;
				for (int i = 0; i < data.length; i++) {
					int len = data[i] & 0xFF;
					i++;
					System.out.print("Data: ");
					for (int j = 0; j < len; j++, i++) {
						System.out.print(HexString.hexify(data[i]));
					}
					System.out.print(" / State: " + HexString.hexify(data[i]));
					i++;
					System.out.println(" / Privileges: "
							+ HexString.hexify(data[i]));
				}
			} catch (Exception e) {
				throw new ManagerException("erreur sur l'appel du status");
			}
			
		}
	}
	
	/**
	 * @see opencard.core.event.CTListener#cardInserted(opencard.core.event.CardTerminalEvent)
	 */
	public void cardInserted(CardTerminalEvent arg0) throws CardTerminalException {
		
	}
	
	/**
	 * @see opencard.core.event.CTListener#cardRemoved(opencard.core.event.CardTerminalEvent)
	 */
	public void cardRemoved(CardTerminalEvent arg0) throws CardTerminalException {
		synchronized (monitor) {
			monitor.notifyAll();
		}	
	}
	
	private void shutdown() throws Exception {
		SmartCard.shutdown();
	}
	
	private CFlex32CardService getLoader() throws Exception {
		if (loader == null) {
			loader = (CFlex32CardService) sm.getCardService(
					CFlex32CardService.class, true);
		}
		return loader;
	}
	
	private int printTerminalInfos(CardTerminal terminal) {
		// First of all print all information stored about this reader
		System.out.println("Address: " + terminal.getAddress() + "\n"
				+ "Name:    " + terminal.getName() + "\n" + "Type:    "
				+ terminal.getType() + "\n" + "Slots:   " + terminal.getSlots()
				+ "\n");
		return terminal.getSlots();
	}
	
	private void printSlotInfos(CardTerminal terminal, int aSlotID) {
		try {
			// First print the ID of the slot
			System.out.println("Info for slot ID: " + aSlotID);
			if (terminal.isCardPresent(aSlotID)) {
				System.out.println("card present: yes");
				// If there is a card in the slot print the ATR the OCF got form this
				// card
				System.out.println("ATR: "
						+ HexString
						.hexify(terminal.getCardID(aSlotID).getATR()));
				// As we do not have a driver for this card we cannot interpret this ATR
			} else
				System.out.println("card present: no");
		} catch (CardTerminalException e) {
			e.printStackTrace();
		}
	}
	
	private byte[] getAuthKey() {
		
		return HexString.parseHexString(auth_enc_);
		
	}
	
	private byte[] getMacKey() {
		
		return HexString.parseHexString(mac_);
		
	}

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
    
    private void printDebug(CardTerminalRegistry ctr ) {
        for (Enumeration terminals = ctr.getCardTerminals();terminals.hasMoreElements();) {
              CardTerminal terminal = (CardTerminal) terminals.nextElement(); 
              int slots = terminal.getSlots();
              System.err.println("Address: " + terminal.getAddress() + "\n" + "Name:    "
                        + terminal.getName() + "\n" + "Type:    " + terminal.getType() + "\n"
                        + "Slots:   " + terminal.getSlots() + "\n");
              for (int aSlotID = 0; aSlotID < slots; aSlotID++) {
                  try {
                      // First print the ID of the slot
                      System.err.println("Info for slot ID: " + aSlotID);
                      if (terminal.isCardPresent(aSlotID)) {
                          System.err.println("card present: yes");
                          // If there is a card in the slot print the ATR the OCF got form this
                          // card
                          System.err.println("ATR: "
                                  + HexString.hexify(terminal.getCardID(aSlotID).getATR()));
                          // As we do not have a driver for this card we cannot interpret this ATR
                      } else
                          System.err.println("card present: no");
                  } catch (CardTerminalException e) {
                      e.printStackTrace();
                  }
              }
        }
    }
}
