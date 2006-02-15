package fr.umlv.ir3.corba.generator.proxy;

import java.lang.reflect.Method;
import java.util.Iterator;

import fr.umlv.ir3.corba.generator.AbstractSqueleton;
import fr.umlv.ir3.corba.generator.InterfaceView;

/**
 * This Squeleteton is used to generate access to Applet, for each method it
 * create associated method.
 * this class define a protocle use to send data and receive data in apdu like this :
 * <b>Send Packet</b>
 * The send send packet is decomposed in arguments :
 * byte : contain only the value
 * byte array : contain 1 byte : Lentgth of array, N byte
 * 
 *  <b>Receive Packet</b>
 *  8 bit[1 byte]: N :length of return value
 *  N*8 : Data of argument
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
		line("");
		line("/**");
		line(" * This class provides an implementation of servant corba object using a");
		line(" * distant calculator applet in card terminal");
		line(" */");
		code.append("public class ").append(setName()).append("Impl extends ").append(setName()).append("POA{");
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
			code.append(CodeMethodsFactory.createCodeMethodsFactory().generateMethodCode(method));
		}
		indentation--;
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
		line(" // This applet is designed to respond to the following");
		line(" // class of instructions.");
		line(" final static byte CalculatorApplet_CLA = (byte)0x86;");
		line("");
		line(" // Instruction set for CalculatorApplet");
		line(" final static byte PUSH = (byte)0x10;");
		line(" final static byte POP = (byte) 0x20;");
		line(" final static byte RESULT = (byte)0x30;");
		line(" final static byte CLEAR = (byte) 0x40;");
		line("");
		line(" //TODO: Ca s'appelle comment ce truc ? ;-)");
		line(" private CFlex32CardService javacard;");
		line("");
		line(" private CardTerminal terminal=null;");
		line(" private SmartCard sm = null;");
		line(" private CardRequest cr = null;");
		line("");
		line("");
		line(" /**");
		line(" * TODO: completer les exceptions");
		line(" * Constructs an instance of calculator applet client by initializing Java");
		line(" Card access service");
		line(" * This method initializes javacard terminal access");
		line(" * @throws ClassNotFoundException ?");
		line(" * @throws CardTerminalException ?");
		line(" * @throws CardServiceException ?");
		line(" * @throws OpenCardPropertyLoadingException ?");
		line(" */");
		line(" public CalculatorImpl() throws OpenCardPropertyLoadingException,");
		line(" CardServiceException, CardTerminalException, ClassNotFoundException{");
		line(" super();");
		line(" initTerminal();");
		line(" }");
		line("");
		line(" /**");
		line(" * TODO: completer les exceptions");
		line(" * Initializes javacard card access");
		line(" * @param appletId - id of applet in the Java card");
		line(" * @throws ClassNotFoundException ?");
		line(" * @throws CardTerminalException ?");
		line(" * @throws CardServiceException ?");
		line(" * @throws OpenCardPropertyLoadingException ?");
		line(" */");
		line(" public void initCardAccess(String appletId) throws CardTerminalException,");
		line(" CardServiceException, ClassNotFoundException,");
		line(" OpenCardPropertyLoadingException{");
		line(" //Wait for insert card");
		line(" if(terminal.isCardPresent(0)==false)");
		line(" {");
		line(" System.out.println(\"Re-insert/Insert your card ...\");");
		line(" cr = new CardRequest(CardRequest.NEWCARD, terminal, null);");
		line(" }");
		line(" else");
		line(" {");
		line(" cr = new CardRequest(CardRequest.ANYCARD, terminal, null);");
		line(" }");
		line(" sm = SmartCard.waitForCard(cr);");
		line("");
		line(" if(sm==null)");
		line(" {");
		line(" throw new NullPointerException(\"Error when waiting for card to become ready\"");
		line(" }");
		line("");
		line(" //Test application");
		line(" javacard = (CFlex32CardService)");
		line(" sm.getCardService(CFlex32CardService.class, true);");
		line(" javacard.selectApplication(HexString.parseHexString(\"A00000000201\"));");
		line("");
		line(" javacard.allocateChannel();");
		line("");
		line(" //Start card service");
		line(" if (!SmartCard.isStarted()) {");
		line(" SmartCard.start();");
		line(" }");
		line("");
		line(" //DEBUG: List terminal on the computer");
		line(" printDebug(CardTerminalRegistry.getRegistry());");
		line(" }");
		line("");
		line(" /**");
		line(" * Closes access applet channel");
		line(" */");
		line(" public void closeCardAccess(){");
		line(" javacard.releaseChannel();");
		line(" }");
		line(" /************************************************ Private methods");
		line(" *******************************************************************/");
		line(" private void initTerminal() throws OpenCardPropertyLoadingException,");
		line(" CardServiceException, CardTerminalException, ClassNotFoundException{");
		line("");
		line(" //Try to starrt service");
		line(" if (SmartCard.isStarted() == false) {");
		line(" SmartCard.start();");
		line(" }");
		line("");
		line(" //List terminal on the computer");
		line(" CardTerminalRegistry ctr = CardTerminalRegistry.getRegistry();");
		line("");
		line(" for (Enumeration terminals =");
		line(" ctr.getCardTerminals();terminals.hasMoreElements();) {");
		line(" terminal = (CardTerminal) terminals.nextElement();");
		line(" //TODO : Trouver une solution pous élégante");
		line(" }");
		line("");
		line(" if(terminal==null)");
		line(" {");
		line(" //TODO : Remonter les exception correctement");
		line(" throw new NullPointerException(\"Couldn't not retrieve a card reader\");");
		line(" }");
		line(" }");
		line("");
		line(" private void printDebug(CardTerminalRegistry ctr ) {");
		line(" for (Enumeration terminals =");
		line(" ctr.getCardTerminals();terminals.hasMoreElements();) {");
		line(" CardTerminal terminal = (CardTerminal) terminals.nextElement();");
		line(" int slots = terminal.getSlots();");
		line(" System.err.println(\"Address: \" + terminal.getAddress() + \"\\n\" + \"Name: \"");
		line(" + terminal.getName() + \"\\n\" + \"Type: \" + terminal.getType() + \"\\n\"");
		line(" + \"Slots: \" + terminal.getSlots() + \"\\n\");");
		line(" for (int aSlotID = 0; aSlotID < slots; aSlotID++) {");
		line(" try {");
		line(" // First print the ID of the slot");
		line(" System.err.println(\"Info for slot ID: \" + aSlotID);");
		line(" if (terminal.isCardPresent(aSlotID)) {");
		line(" System.err.println(\"card present: yes\");");
		line(" // If there is a card in the slot print the ATR the OCF got form this");
		line(" // card");
		line(" System.err.println(\"ATR: \"");
		line(" + HexString.hexify(terminal.getCardID(aSlotID).getATR()));");
		line(" // As we do not have a driver for this card we cannot interpret this ATR");
		line(" } else");
		line(" System.err.println(\"card present: no\");");
		line(" } catch (CardTerminalException e) {");
		line(" e.printStackTrace();");
		line(" }");
		line(" }");
		line(" }");
		line(" }");
		line("");
		line(" private ResponseAPDU sendCommand(byte command, byte value) throws");
		line(" CardTerminalException, InitializationException{");
		line(" byte[] values = new byte[1];");
		line(" values[0] = value;");
		line(" return javacard.sendAPDU(new");
		line(" ISOCommandAPDU(CalculatorApplet_CLA,command,(byte)0,(byte)0,values,values.length));");
		line(" }");
		line("");
		line(" private ResponseAPDU sendCommand(byte command) throws");
		line(" CardTerminalException, InitializationException{");
		line(" return javacard.sendAPDU(new");
		line(" ISOCommandAPDU(CalculatorApplet_CLA,command,(byte)0,(byte)0,new");
		line(" byte[0],0));");
		line("    }");
		line("}");

	}
		
	/**
	 * Add a line into code StringBuilder
	 * @param line line to add in code
	 */
	private void line(String line) {
		code.append(indent(indentation)).append(line).append("\n");
	}


}
