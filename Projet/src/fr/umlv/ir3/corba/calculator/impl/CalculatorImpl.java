package fr.umlv.ir3.corba.calculator.impl;

import java.util.Enumeration;

import opencard.cflex.service.CFlex32CardService;
import opencard.cflex.util.Util;
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
import fr.umlv.ir3.corba.calculator.InvalidOperator;
import fr.umlv.ir3.corba.calculator.StackOverFlow;

public class CalculatorImpl extends AppletCalculatorPOA{
	//  This applet is designed to respond to the following
	//  class of instructions.
	final static byte CalculatorApplet_CLA = (byte)0x86;

	//  Instruction set for CalculatorApplet
	final static byte PUSH = (byte)0x10;
	final static byte RESULT = (byte)0x20;
	final static byte CLEAR = (byte) 0x30;
	
	private CFlex32CardService javacard;
	
	public CalculatorImpl(String appletId) throws OpenCardPropertyLoadingException, CardServiceException, CardTerminalException, ClassNotFoundException {
		super();
		initCard(appletId);
	}
	
	public void initCard(String appletId) throws OpenCardPropertyLoadingException, CardServiceException, CardTerminalException, ClassNotFoundException{
//		Try to starrt service
	    if (SmartCard.isStarted() == false) {
	      SmartCard.start();
	    }
	    
		//List terminal on the computer
		CardTerminalRegistry ctr = CardTerminalRegistry.getRegistry();
		printDebug(ctr);

		//Wait for insert card
	    System.out.println("Insert your card ...");
	    CardRequest cr = new CardRequest(CardRequest.ANYCARD, null, null);
	    SmartCard sm = SmartCard.waitForCard(cr);

		//Test application
	    javacard = (CFlex32CardService) sm.getCardService(CFlex32CardService.class, true);
	    javacard.selectApplication(HexString.parseHexString(appletId));
		javacard.allocateChannel();
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

	public short result(char operator) throws InvalidOperator, CardTerminalException {
		ResponseAPDU res;
		byte[] operatorInByte = {(byte)operator};
      	sendCommand(RESULT,operatorInByte);
		return 0;
	}

	public void addNumber(short number) throws StackOverFlow, CardTerminalException {
		ResponseAPDU res;
		byte[] numberInByte = Util.ShortToBytePair(number);
		sendCommand(PUSH,numberInByte);
	}

	public void clear() {
	}
	
	private ResponseAPDU sendCommand(byte command, byte[] value) throws CardTerminalException{
		return javacard.sendAPDU(new ISOCommandAPDU(CalculatorApplet_CLA,command,(byte)0,(byte)0,value,0));
	}
}
