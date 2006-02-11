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
import fr.umlv.ir3.corba.calculator.CardException;
import fr.umlv.ir3.corba.calculator.InitializationException;
import fr.umlv.ir3.corba.calculator.InvalidOperator;
import fr.umlv.ir3.corba.calculator.StackOverFlow;

/**
 * This class provides an implementation of servant corba object using a distant calculator applet in card terminal 
 * @author BARBISAN Laurent, BOITEL Olivier, GUILLON Denis, LAMPS S�bastien 
 */

public class CalculatorImpl extends AppletCalculatorPOA{
	//This applet is designed to respond to the following class of instructions.
	private final static byte CalculatorApplet_CLA = (byte)0x86;

	//Instruction set for CalculatorApplet
    private final static byte PUSH = (byte)0x10;
    private final static byte RESULT = (byte)0x20;
    private final static byte CLEAR = (byte) 0x30;
	
    //TODO: Ca s'appelle comment ce truc ? ;-)
	private CFlex32CardService javacard;
	
    /**
     * TODO: completer les exceptions
     * Constructs an instance of calculator applet client by initializing Java Card access service
     * This method initializes javacard terminal access
     * @throws ClassNotFoundException ?
     * @throws CardTerminalException ?
     * @throws CardServiceException ?
     * @throws OpenCardPropertyLoadingException ?
     */
	public CalculatorImpl() throws OpenCardPropertyLoadingException, CardServiceException, CardTerminalException, ClassNotFoundException{
		super();
        initTerminal();
	}
    
    /**
     * TODO: completer les exceptions
     * Initializes javacard card access
     * @param appletId - id of applet in the Java card
     * @throws ClassNotFoundException ?
     * @throws CardTerminalException ?
     * @throws CardServiceException ?
     */
    public void initCardAccess(String appletId) throws CardTerminalException, CardServiceException, ClassNotFoundException{
		//Wait for insert card
	    CardRequest cr = new CardRequest(CardRequest.ANYCARD, null, null);
	    SmartCard sm = SmartCard.waitForCard(cr);

		//Get channel to access card applet
	    javacard = (CFlex32CardService) sm.getCardService(CFlex32CardService.class, true);
	    javacard.selectApplication(HexString.parseHexString(appletId));
		javacard.allocateChannel();
	}
    
    /**
     * Closes access applet channel
     */
    public void closeCardAccess(){
        //TODO: doit on refermer le channel à la fin ?
    }
    
    /**
     * Sends a result request to the calculator applet
     * @return a short representing response to result request of calculator applet
     * @throws InvalidOperator - input operator incorrect, means not +,-,*,/
     * @throws CardException - error in using card terminal
     * @throws InitializationException - error if Java card access is not initialized
     */
	public short result(char operator) throws InvalidOperator, CardException, InitializationException{
      	try {
    		byte[] operatorInByte = {(byte)operator};
            //Send APDU trame to applet
    		ResponseAPDU res = sendCommand(RESULT,operatorInByte);
    		//TODO: verifier la bonne utilisation du traitement des reponses
    		return Util.BytePairToShort(res.data()[0],res.data()[1]);
		} catch (CardTerminalException e) {
			throw new CardException(e.getMessage());
		}
	}

    /**
     * Sends a operand depository to the calculator applet
     * @throws InvalidOperator - input operator incorrect, means not +,-,*,/
     * @throws CardException - error in using card terminal
     * @throws InitializationException - error if Java card access is not initialized
     */
	public void addNumber(short number) throws StackOverFlow, CardException, InitializationException {
		try {
			byte[] numberInByte = Util.ShortToBytePair(number);
            //Send APDU trame to applet
			sendCommand(PUSH,numberInByte);
		} catch (CardTerminalException e) {
			throw new CardException(e.getMessage());
		}
	}

    /**
     * Sends a clear request to the calculator applet
     * @throws CardException - error in using card terminal
     * @throws InitializationException - error if Java card access is not initialized
     */
	public void clear() throws InitializationException, CardException {
        try {
            byte[] empty = {};
            //Send APDU trame to applet
            sendCommand(CLEAR,empty);
        } catch (CardTerminalException e) {
            throw new CardException(e.getMessage());
        }
	}
    
    /************************************************ Private methods *******************************************************************/
    private void initTerminal() throws OpenCardPropertyLoadingException, CardServiceException, CardTerminalException, ClassNotFoundException{
        //Start card service
        if (!SmartCard.isStarted()) {
          SmartCard.start();
        }
        
        //DEBUG: List terminal on the computer
        printDebug(CardTerminalRegistry.getRegistry());
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
    private ResponseAPDU sendCommand(byte command, byte[] value) throws CardTerminalException, InitializationException{
        return javacard.sendAPDU(new ISOCommandAPDU(CalculatorApplet_CLA,command,(byte)0,(byte)0,value,0));
    }
}
