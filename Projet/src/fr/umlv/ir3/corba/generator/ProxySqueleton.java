package fr.umlv.ir3.corba.generator;

import java.lang.reflect.Method;
import java.util.Iterator;

/**
 * This Squeleteton is used to generate access to Applet, for each method it
 * create associated method
 * 
 * @author lbarbisan
 * 
 */
public class ProxySqueleton extends AbstractSqueleton {

	private StringBuilder code;

	private int indentation = 0;

	/**
	 * Constructor of ProxySqueleton
	 * 
	 * @param squeletonInterface
	 *            Interface which define method
	 */
	public ProxySqueleton(InterfaceView squeletonInterface) {
		super(squeletonInterface);
	}

	/**
	 * @see fr.umlv.ir3.corba.generator.AbstractSqueleton#setName()
	 */
	@Override
	public String setName() {
		// TODO Auto-generated method stub
		return null;
	}

	private void line(String line) {
		code.append(indent(indentation)).append(line).append("\n");
	}

	/**
	 * @see fr.umlv.ir3.corba.generator.AbstractSqueleton#generateStartClass(java.lang.StringBuilder)
	 */
	@Override
	protected void generateStartClass(StringBuilder code) {
		this.code = code;
		indentation++;
		line("package fr.umlv.ir3.corba.calculator.impl;");
		line("");
		line("import java.util.Enumeration;");
		line("");
		line("import opencard.cflex.service.CFlex32CardService;");
		line("import opencard.core.service.CardRequest;");
		line("import opencard.core.service.CardServiceException;");
		line("import opencard.core.service.SmartCard;");
		line("import opencard.core.terminal.CardTerminal;");
		line("import opencard.core.terminal.CardTerminalException;");
		line("import opencard.core.terminal.CardTerminalRegistry;");
		line("import opencard.core.terminal.ResponseAPDU;");
		line("import opencard.core.util.HexString;");
		line("import opencard.core.util.OpenCardPropertyLoadingException;");
		line("import opencard.opt.terminal.ISOCommandAPDU;");
		line("import fr.umlv.ir3.corba.calculator.AppletCalculatorPOA;");
		line("import fr.umlv.ir3.corba.calculator.CardException;");
		line("import fr.umlv.ir3.corba.calculator.InitializationException;");
		line("import fr.umlv.ir3.corba.calculator.InvalidOperator;");
		line("import fr.umlv.ir3.corba.calculator.StackOverFlow;");
		indentation--;
	}

	/**
	 * @see fr.umlv.ir3.corba.generator.AbstractSqueleton#generateMethods(java.lang.StringBuilder)
	 */
	@Override
	protected void generateMethods(StringBuilder code) {
		this.code = code;
		indentation++;
		Iterator<Method> iterator = SqueletonInterface.getMethodsIterator();
		while(iterator.hasNext())
		{
			Method method = iterator.next();
			code.append("public void").append(method.getName()).append("()\n");
			indentation++;
			line("{");
			line("}");
			indentation--;
		}
	}

	/**
	 * @see fr.umlv.ir3.corba.generator.AbstractSqueleton#generateFinalize(java.lang.StringBuilder)
	 */
	@Override
	protected void generateFinalize(StringBuilder code) {
		line("}");
	}

	/**
	 * @see fr.umlv.ir3.corba.generator.AbstractSqueleton#generateInitialize(java.lang.StringBuilder)
	 */
	@Override
	protected void generateInitialize(StringBuilder code) {
		// TODO Auto-generated method stub

	}

	//
	// /**
	// * This class provides an implementation of servant corba object using a
	// distant calculator applet in card terminal
	// * @author BARBISAN Laurent, BOITEL Olivier, GUILLON Denis, LAMPS
	// S�bastien
	// */
	//
	// public class CalculatorImpl extends AppletCalculatorPOA{
	// // This applet is designed to respond to the following
	// // class of instructions.
	// final static byte CalculatorApplet_CLA = (byte)0x86;
	//
	// // Instruction set for CalculatorApplet
	// final static byte PUSH = (byte)0x10;
	// final static byte POP = (byte) 0x20;
	// final static byte RESULT = (byte)0x30;
	// final static byte CLEAR = (byte) 0x40;
	//		
	// //TODO: Ca s'appelle comment ce truc ? ;-)
	// private CFlex32CardService javacard;
	//		
	// private CardTerminal terminal=null;
	// private SmartCard sm = null;
	// private CardRequest cr = null;
	//		
	//		
	// /**
	// * TODO: completer les exceptions
	// * Constructs an instance of calculator applet client by initializing Java
	// Card access service
	// * This method initializes javacard terminal access
	// * @throws ClassNotFoundException ?
	// * @throws CardTerminalException ?
	// * @throws CardServiceException ?
	// * @throws OpenCardPropertyLoadingException ?
	// */
	// public CalculatorImpl() throws OpenCardPropertyLoadingException,
	// CardServiceException, CardTerminalException, ClassNotFoundException{
	// super();
	// initTerminal();
	// }
	//	    
	// /**
	// * TODO: completer les exceptions
	// * Initializes javacard card access
	// * @param appletId - id of applet in the Java card
	// * @throws ClassNotFoundException ?
	// * @throws CardTerminalException ?
	// * @throws CardServiceException ?
	// * @throws OpenCardPropertyLoadingException ?
	// */
	// public void initCardAccess(String appletId) throws CardTerminalException,
	// CardServiceException, ClassNotFoundException,
	// OpenCardPropertyLoadingException{
	// //Wait for insert card
	// if(terminal.isCardPresent(0)==false)
	// {
	// System.out.println("Re-insert/Insert your card ...");
	// cr = new CardRequest(CardRequest.NEWCARD, terminal, null);
	// }
	// else
	// {
	// cr = new CardRequest(CardRequest.ANYCARD, terminal, null);
	// }
	// sm = SmartCard.waitForCard(cr);
	//	        
	// if(sm==null)
	// {
	// throw new NullPointerException("Error when waiting for card to become
	// ready");
	// }
	//
	// //Test application
	// javacard = (CFlex32CardService)
	// sm.getCardService(CFlex32CardService.class, true);
	// javacard.selectApplication(HexString.parseHexString("A00000000201"));
	//	        
	// javacard.allocateChannel();
	//
	//	    	
	// //Start card service
	// if (!SmartCard.isStarted()) {
	// SmartCard.start();
	// }
	//	        
	// //DEBUG: List terminal on the computer
	// printDebug(CardTerminalRegistry.getRegistry());
	// }
	//	    
	// /**
	// * Closes access applet channel
	// */
	// public void closeCardAccess(){
	// javacard.releaseChannel();
	// }
	//	    
	// /**
	// * Sends a result request to the calculator applet
	// * @return a short representing response to result request of calculator
	// applet
	// * @throws InvalidOperator - input operator incorrect, means not +,-,*,/
	// * @throws CardException - error in using card terminal
	// * @throws InitializationException - error if Java card access is not
	// initialized
	// */
	// public short result(char operator) throws InvalidOperator, CardException,
	// InitializationException{
	// try {
	// //Send APDU trame to applet
	// ResponseAPDU res = sendCommand(RESULT,(byte)operator);
	// //TODO: verifier la bonne utilisation du traitement des reponses
	// return res.data()[0];
	// } catch (CardTerminalException e) {
	// throw new CardException(e.getMessage());
	// }
	// }
	//
	// /**
	// * Sends a operand depository to the calculator applet
	// * @throws InvalidOperator - input operator incorrect, means not +,-,*,/
	// * @throws CardException - error in using card terminal
	// * @throws InitializationException - error if Java card access is not
	// initialized
	// */
	// public void addNumber(short number) throws StackOverFlow, CardException,
	// InitializationException {
	// try {
	// //Send APDU trame to applet
	// sendCommand(PUSH,(byte)number);
	// } catch (CardTerminalException e) {
	// throw new CardException(e.getMessage());
	// }
	// }
	//
	// /**
	// * Sends a clear request to the calculator applet
	// * @throws CardException - error in using card terminal
	// * @throws InitializationException - error if Java card access is not
	// initialized
	// */
	// public void clear() throws InitializationException, CardException {
	// try {
	// //Send APDU trame to applet
	// sendCommand(CLEAR);
	// } catch (CardTerminalException e) {
	// throw new CardException(e.getMessage());
	// }
	// }
	//	    
	// /************************************************ Private methods
	// *******************************************************************/
	// private void initTerminal() throws OpenCardPropertyLoadingException,
	// CardServiceException, CardTerminalException, ClassNotFoundException{
	//	        
	// //Try to starrt service
	// if (SmartCard.isStarted() == false) {
	// SmartCard.start();
	// }
	//	        
	// //List terminal on the computer
	// CardTerminalRegistry ctr = CardTerminalRegistry.getRegistry();
	//	            	
	// for (Enumeration terminals =
	// ctr.getCardTerminals();terminals.hasMoreElements();) {
	// terminal = (CardTerminal) terminals.nextElement();
	// //TODO : Trouver une solution pous élégante
	// }
	//
	// if(terminal==null)
	// {
	// //TODO : Remonter les exception correctement
	// throw new NullPointerException("Couldn't not retrieve a card reader");
	// }
	// }
	//	    
	// private void printDebug(CardTerminalRegistry ctr ) {
	// for (Enumeration terminals =
	// ctr.getCardTerminals();terminals.hasMoreElements();) {
	// CardTerminal terminal = (CardTerminal) terminals.nextElement();
	// int slots = terminal.getSlots();
	// System.err.println("Address: " + terminal.getAddress() + "\n" + "Name: "
	// + terminal.getName() + "\n" + "Type: " + terminal.getType() + "\n"
	// + "Slots: " + terminal.getSlots() + "\n");
	// for (int aSlotID = 0; aSlotID < slots; aSlotID++) {
	// try {
	// // First print the ID of the slot
	// System.err.println("Info for slot ID: " + aSlotID);
	// if (terminal.isCardPresent(aSlotID)) {
	// System.err.println("card present: yes");
	// // If there is a card in the slot print the ATR the OCF got form this
	// // card
	// System.err.println("ATR: "
	// + HexString.hexify(terminal.getCardID(aSlotID).getATR()));
	// // As we do not have a driver for this card we cannot interpret this ATR
	// } else
	// System.err.println("card present: no");
	// } catch (CardTerminalException e) {
	// e.printStackTrace();
	// }
	// }
	// }
	// }
	//	    
	// private ResponseAPDU sendCommand(byte command, byte value) throws
	// CardTerminalException, InitializationException{
	// byte[] values = new byte[1];
	// values[0] = value;
	// return javacard.sendAPDU(new
	// ISOCommandAPDU(CalculatorApplet_CLA,command,(byte)0,(byte)0,values,values.length));
	// }
	//	    
	// private ResponseAPDU sendCommand(byte command) throws
	// CardTerminalException, InitializationException{
	// return javacard.sendAPDU(new
	// ISOCommandAPDU(CalculatorApplet_CLA,command,(byte)0,(byte)0,new
	// byte[0],0));
	//	    }
	//	}

}
