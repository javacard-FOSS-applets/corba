/**
 * 
 */
package fr.umlv.ir3.corba.manager;

import java.util.Enumeration;

import opencard.core.event.CTListener;
import opencard.core.event.CardTerminalEvent;
import opencard.core.event.EventGenerator;
import opencard.core.service.CardRequest;
import opencard.core.service.SmartCard;
import opencard.core.terminal.CardTerminal;
import opencard.core.terminal.CardTerminalException;
import opencard.core.terminal.CardTerminalRegistry;
import opencard.core.util.HexString;


/**
 * @author olive
 *
 */
public class AppletManagerImpl extends AppletManagerPOA implements CTListener{
	
	private SmartCard sm;
	private final static Object monitor = "synchronization monitor";
	
	/* (non-Javadoc)
	 * @see fr.umlv.ir3.corba.manager.AppletManagerOperations#init()
	 */
	public void init() throws ManagerException{
		CardTerminalRegistry ctr = CardTerminalRegistry.getRegistry();
		CardTerminal terminal = null;
		CardRequest cr = null;
		
		if (SmartCard.isStarted() == false) {
			try {
				SmartCard.start();
			} catch (Exception e){
				throw new ManagerException("erreur de lancement de la carte");
			}
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
		
		try{
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
		}catch(Exception e){
			throw new ManagerException("erreur de gestion du terminal");
		}
		
		EventGenerator.getGenerator().addCTListener(this);
		
	}
	
	/* (non-Javadoc)
	 * @see fr.umlv.ir3.corba.manager.AppletManagerOperations#load(byte[], int)
	 */
	public void load(byte[] input, int staticsize) throws ManagerException {
		// TODO Auto-generated method stub
		
	}


	/* (non-Javadoc)
	 * @see fr.umlv.ir3.corba.manager.AppletManagerOperations#install(int, java.lang.String, java.lang.String)
	 */
	public void install(int instance_size, String pakgId, String appId) throws ManagerException {
		// TODO Auto-generated method stub
		
	}
	
	/* (non-Javadoc)
	 * @see fr.umlv.ir3.corba.manager.AppletManagerOperations#delete(byte[])
	 */
	public void delete(byte[] aid) throws ManagerException {
		// TODO Auto-generated method stub
		
	}
	
	
	/* (non-Javadoc)
	 * @see fr.umlv.ir3.corba.manager.AppletManagerOperations#status(int)
	 */
	public void status(int type) throws ManagerException {
		// TODO Auto-generated method stub
		
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
	
	/* (non-Javadoc)
	 * @see opencard.core.event.CTListener#cardInserted(opencard.core.event.CardTerminalEvent)
	 */
	public void cardInserted(CardTerminalEvent arg0) throws CardTerminalException {
		// TODO Auto-generated method stub
		
	}
	
	/* (non-Javadoc)
	 * @see opencard.core.event.CTListener#cardRemoved(opencard.core.event.CardTerminalEvent)
	 */
	public void cardRemoved(CardTerminalEvent arg0) throws CardTerminalException {
		synchronized (monitor) {
			monitor.notifyAll();
		}	
	}


	
	
}
