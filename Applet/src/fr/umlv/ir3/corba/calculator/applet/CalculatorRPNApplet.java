package fr.umlv.ir3.corba.calculator.applet;

import javacard.framework.APDU;
import javacard.framework.ISO7816;
import javacard.framework.ISOException;

/**
 * Reverse polonish calculator loaded in javacard
 * This class have 3 command
 * PUSH : put a short number on the top of the stack
 * CLEAR : clear the calculator
 * RESULT : Pass the operation and return the result of both number on the top of the stack
 * 
 * @author lbarbisan
 *
 */
public class CalculatorRPNApplet extends javacard.framework.Applet {

	//  This applet is designed to respond to the following
	//  class of instructions.

	final static byte CalculatorApplet_CLA = (byte)0x86;

	//  Instruction set for CalculatorApplet
	final static byte PUSH = (byte)0x10;
	final static byte RESULT = (byte)0x20;
	final static byte CLEAR = (byte) 0x30;
	
	//  Declare your instance variables here
	final static short BUFFER_SIZE = (short) 0x40;

	short stackBuffer[];	//  This buffer contains the string data on the card
	short cursor;
	
	
	//  The constructor. 

	private CalculatorRPNApplet(byte buffer[],short offset,byte length) {

		stackBuffer = new short[BUFFER_SIZE];
		cursor = 0;

		if (buffer[offset] == (byte)0) {
			register();
		}
		else {
			register(buffer, (short)(offset+1) ,(byte)(buffer[offset]));
		}
		
	}

	//  Every applet running JavaCard 2.0 must implement the following
	//  three functions.

	//  You create the one instance of your applet here.
	public static void install(byte buffer[],short offset,byte length) {

		new CalculatorRPNApplet(buffer, offset, length);

	}

	//  This function is called when your applet is selected.
	public boolean select() {
		return true;
	}

	//  The process method dispatches messages to your class methods
	//  depending on the instruction type.
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
		case RESULT:
			result(apdu);
			break;
		case CLEAR :
			clear();
		default:
			ISOException.throwIt(ISO7816.SW_INS_NOT_SUPPORTED);
		}

	}

	//  Class methods
	
	//push a number at the top of the stack
	private void push(APDU apdu){
		
		//Retrieve buffer data from the apdu
		byte buffer[] = apdu.getBuffer();
		//Retrieve the data from apdu
		short size = (short)(apdu.setIncomingAndReceive());
		short index;

		//fill buffer
		for (index = 0; index < size; index+=2) 
		{
			pushStack(BytePairToShort(buffer[(short)(ISO7816.OFFSET_CDATA + index)], buffer[(short)(ISO7816.OFFSET_CDATA + (short)index+(short)1)]));
		}

		return;
		
	}

	//clear all the values in the stack
	//it is called when the client has finished
	private void clear(){
		cursor = 0;
	}

	//get the operator of the operation in the apdu
	//pop the two number on the top of the stack and then do the operation
	//push the result on the top of the stack  
	private void result(APDU apdu){
		
		byte buffer[] = apdu.getBuffer();
		short size = (short)(apdu.setIncomingAndReceive());
		short index;

		//fill buffer
		for (index = cursor; index < size; index++) 
		{
			short left;
			short right;
			left=popStack();
			right=popStack();
			pushStack(
				calculate(
					buffer[(byte)(ISO7816.OFFSET_CDATA + index)],
						left,
						right
					)
				);
		}
		
		//Send Result
		apdu.setOutgoing();
		apdu.setOutgoingLength((byte)2);
		
		byte[] sendBuffer = ShortToBytePair(popStack());
		
		buffer[0] = sendBuffer[0];
		buffer[1] = sendBuffer[1];

		apdu.sendBytes((short)0,(short)2);

		return;
		
	}

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
				ISOException.throwIt(ISO7816.SW_NO_ERROR);
				result=0;
		}
		return result;
		
				
	}
	private void pushStack(short number)
	{
		if(isFullStack()==false)
		{
			stackBuffer[(short)cursor++]=number;
		}
		else
		{
			 ISOException.throwIt(ISO7816.SW_NO_ERROR);
		}
	}
	
	private short popStack()
	{
		if(isEmptyStack()==false)
		{
			return stackBuffer[(short)cursor--];
		}
		ISOException.throwIt(ISO7816.SW_NO_ERROR);
		return 0;
	}

	private boolean isEmptyStack()
	{
		if(cursor<=(short)0)
		{byte
			cursor=(short)0;
			return true;
		}
		return false;
			
	}

	private boolean isFullStack()
	{
		if(cursor>=(BUFFER_SIZE-1))
		{
			cursor=BUFFER_SIZE-1;
			return true;
		}
		return false;
	}
	
	/**
     * Converts a short into a 2 byte array
     * 
     * @param i short to convert
     */
    public static byte[] ShortToBytePair(short i)
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
     */
    public static short BytePairToShort(byte msb, byte lsb)
    {
        short smsb, slsb;
        smsb = (short)((msb & (short)0x00FF) << (short)8);
        slsb = (short)(lsb & (short)0x00FF);
        return (short) (smsb | slsb);
    }

}
