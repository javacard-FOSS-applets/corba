//--------------------------------------------------------------------------------
//    � Copyright Schlumberger Technology Corp. 1999-2002. All Rights Reserved.
//--------------------------------------------------------------------------------
//
//  SimpleString.java
//
//
//  This card applet allows the user to write a string to the card and
//  read it back.  It provides a simple example of dynamic message
//  resizing (see GetString() method).  The class must store the
//  strings in byte arrays, but a client can easily handle type translation
//  to store strings.
//
//  This card applet is built on the Skeleton.java file.
//
package fr.umlv.ir3.corba.calculator.applet;

import javacard.framework.APDU;
import javacard.framework.ISO7816;
import javacard.framework.ISOException;

/**
 * This class is used to represent a calculator
 * @author lbarbisan
 *
 */
public class CalculatorRPNApplet extends javacard.framework.Applet {

	/**
	 * This applet is designed to respond to the following
	 * class of instructions.
	 */
	public final static byte CalculatorApplet_CLA = (byte)0x86;

	/**
	 *  Instruction for push a number
	 *  The APDU with this instruction must have a short cast into byte in data buffer
	 */ 
	public final static byte PUSH = (byte)0x10;
	/**
	 * Instruction for pop a number
	 * The APDU with this must not have data
	 */
	public final static byte POP = (byte) 0x20;
	/**
	 * Instruction for proceed to a calcul
	 * The APDU with this command must have a char cast into byte into the data buffer
	 * ie : '+', '-', etc....
	 */
	public final static byte RESULT = (byte)0x30;
	/**
	 * Instruction for clearing stack
	 * The APDU with this must not have data
	 */
	public final static byte CLEAR = (byte) 0x40;

	//  Declare your instance variables here
	private final static short BUFFER_SIZE = (short) 0x40;
	private Stack stack;

	/**
	 * Constructor for the applet
	 * @param buffer buffer
	 * @param offset offset
	 * @param length length
	 */
	private CalculatorRPNApplet(byte buffer[],short offset,byte length) {
		
		stack= new Stack(BUFFER_SIZE);
		
		if (buffer[offset] == (byte)0) {
			register();
		}
		else {
			register(buffer, (short)(offset+1) ,(byte)(buffer[offset]));
		}
	}

	//  Every applet running JavaCard 2.0 must implement the following
	//  three functions.
	
	/**
	 *  You create the one instance of your applet here.
	 */ 
	public static void install(byte buffer[],short offset,byte length) {
		new CalculatorRPNApplet(buffer, offset, length);
	}

	/**
	 *  This function is called when your applet is selected.
	 */ 
	public boolean select() {
		return true;
	}

	/**
	 *  The process method dispatches messages to your class methods
	 *  depending on the instruction type.
	 */
	public void process(APDU apdu) throws ISOException{

		byte buffer[] = apdu.getBuffer();

        // Implement a select handler 
        if (selectingApplet()) {
            ISOException.throwIt(ISO7816.SW_NO_ERROR);
		}
		
		if (buffer[ISO7816.OFFSET_CLA] != CalculatorApplet_CLA) 
				ISOException.throwIt(ISO7816.SW_CLA_NOT_SUPPORTED);
    
        byte ins = buffer[ISO7816.OFFSET_INS];
        
		switch (ins) {

		case PUSH:
			push(apdu);
			break;
		case POP:
			pop(apdu);
			break;
		case RESULT:
			result(apdu);
			break;
		case CLEAR:
			clear();
			break;
		default:
			ISOException.throwIt(ISO7816.SW_INS_NOT_SUPPORTED);
		}

	}
	
	/**
	 * reset the stack
	 */
	private void clear() {
		stack.clear();
	}

	/**
	 * Push into stack the number pass into apdu
	 * @param apdu apdu with the number
	 * @throws ISO7816.SW_WRONG_LENGTH if the apdu buffer data isn't equals to 1
	 * @throws ISO7816.SW_FILE_FULL if an overflow occurs
	 */
	private void push(APDU apdu) {
		byte buffer[] = apdu.getBuffer();

		byte numBytes = (byte)(apdu.setIncomingAndReceive());
		
		if (numBytes != (byte)2) {
			ISOException.throwIt(ISO7816.SW_WRONG_LENGTH);
		}	
		stack.pushStack(bytePairToShort(buffer[(byte)(ISO7816.OFFSET_CDATA)], buffer[(byte)((ISO7816.OFFSET_CDATA)+1)]));
	}

	/**
	 * Return the number on the top of the stack
	 * @param apdu apdu
	 * @throws ISO7816.SW_WRONG_LENGTH if the apdu buffer data isn't equals to 1
	 * @throws ISO7816.SW_FILE_FULL if an underflow occurs
	 */
	private void pop(APDU apdu) {

		byte buffer[] = apdu.getBuffer();

		byte numBytes = ( byte )( apdu.setIncomingAndReceive() );
		
		if (numBytes != (byte)0) {
			ISOException.throwIt(ISO7816.SW_WRONG_LENGTH);
		}

		apdu.setOutgoing();
		apdu.setOutgoingLength((short)2);
		 
		byte[] stackBuffer=shortToBytePair(stack.popStack());
		
		buffer[0] = stackBuffer[0];
		buffer[1] = stackBuffer[1];
		
		apdu.sendBytes((short)0,(short)2);

		return;
	}
	
	/**
	 * get the operator of the operation in the apdu
	 * pop the two number on the top of the stack and then do the operation
	 * push the result on the top of the stack  
	 * @param apdu apdu
	 * @throws ISO7816.SW_WRONG_LENGTH if the apdu buffer data isn't equals to 1
	 * @throws ISO7816.SW_FILE_FULL if an underflow occurs
	 */
	private void result(APDU apdu){
		
		byte buffer[] = apdu.getBuffer();

		byte numBytes = (byte)(apdu.setIncomingAndReceive());
		
		if (numBytes != (byte)2) {
			ISOException.throwIt(ISO7816.SW_WRONG_LENGTH);
		}	
		
		short left;
		short right;
		
		left=stack.popStack();
		right=stack.popStack();
		
		short result = calculate(buffer[(byte)(ISO7816.OFFSET_CDATA)],	left, right);
		
		stack.pushStack(result);
	
		apdu.setOutgoing();
		apdu.setOutgoingLength((short)2);
		 
		byte[] stackBuffer=shortToBytePair(result);
		
		buffer[0] = stackBuffer[0];
		buffer[1] = stackBuffer[1];
		
		apdu.sendBytes((short)0,(short)2);

		return;
		
	}
	
	/**
	 * Do a calculation with the spécified parameters
	 * @param operator opertion to done. Implementation operators :
	 * 			'+' : addition
	 * 			'-' : substraction
	 * 			'/' : division
	 * 			'*' : multiplication
	 * @param left left number
	 * @param right right number
	 * @return return the result
	 * @throws ISO7816.SW_COMMAND_NOT_ALLOWED if the operator isn't know
	 */
	//FIXME : division par zéro
	private short calculate(byte operator, short left, short right) 
	{
		short result;

		switch(operator)
		{
			case (byte)'+':
				result=(short)((short)left+(short)right);
				break;
			case (byte)'-':
				result=(short)((short)left-(short)right);
				break;
			case (byte)'/':
				result=(short)((short)left/(short)right);
				break;
			case (byte)'*':
				result=(short)((short)left*(short)right);				
				break;
			default:
				ISOException.throwIt(ISO7816.SW_COMMAND_NOT_ALLOWED);
				result=0;
		}
		return result;
	}
	
	/**
     * Converts a short into a 2 byte array
     * 
     * @param i short to convert
	 * @return  array of 2 byte from short
     */
    public static byte[] shortToBytePair(short i)
    {
        byte[] retVal = new byte[2];
        retVal[0] = (byte)((short)((i & (short)0xFFFF) >> (short)8));
        retVal[1] = (byte)(i & (short)0x00FF);
        return retVal;
    }
    
    /**
     * Converts a byte pair to a short.
     * 
     * @param msb most significant byte
     * @param lsb least significant byte
     * @return short from array of 2 byte
     */
    public static short bytePairToShort(byte msb, byte lsb)
    {
        short smsb, slsb;
        smsb = (short)((msb & (short)0x00FF) << (short)8);
        slsb = (short)(lsb & (short)0x00FF);
        return (short) (smsb | slsb);
    }
}
