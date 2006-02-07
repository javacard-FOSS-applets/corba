package fr.umlv.ir3.corba.calculator.applet;


//	--------------------------------------------------------------------------------
//  � Copyright Schlumberger Technology Corp. 1999-2002. All Rights Reserved.
//--------------------------------------------------------------------------------
//
//SimpleString.java
//
//
//This card applet allows the user to write a string to the card and
//read it back.  It provides a simple example of dynamic message
//resizing (see GetString() method).  The class must store the
//strings in byte arrays, but a client can easily handle type translation
//to store strings.
//
//This card applet is built on the Skeleton.java file.
//


import javacard.framework.APDU;
import javacard.framework.ISO7816;
import javacard.framework.ISOException;

//Class Definition

public class CalculatorRPNAppletOld extends javacard.framework.Applet {

	//  This applet is designed to respond to the following
		//  class of instructions.

	final static byte CalculatorApplet_CLA = (byte)0x86;

	//  Instruction set for CalculatorApplet
	final static byte PUSH = (byte)0x10;
	final static byte RESULT = (byte)0x20;
	final static byte CLEAR = (byte) 0x30;
	
	//  Declare your instance variables here
	final static byte BUFFER_SIZE = (byte) 0x40;

	byte stackBuffer[];	//  This buffer contains the string data on the card
	byte cursor;
	
	
	//  The constructor. 

	private CalculatorRPNAppletOld(byte buffer[],short offset,byte length) {

		stackBuffer = new byte[BUFFER_SIZE];
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

		new CalculatorRPNAppletOld(buffer, offset, length);

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
		
		byte buffer[] = apdu.getBuffer();

		byte size = (byte)(apdu.setIncomingAndReceive());
		size = (byte)((byte)cursor + (byte)size);

		byte index;

		//fill buffer
		for (index = cursor; index < size; index++) 
		{
			pushStack(buffer[(byte)(ISO7816.OFFSET_CDATA + index)]);
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

		byte size = (byte)(apdu.setIncomingAndReceive());
		size =(byte)((byte)cursor + (byte)size);

		byte index;

		//fill buffer
		for (index = cursor; index < size; index++) 
		{
			byte left;
			byte right;
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
		

		/*byte numBytes = buffer[ISO7816.OFFSET_LC];

		if (numBytes == (byte)0) {
			ISOException.throwIt((short)(0x6200 + stackBuffer[0]));
		}*/

		//Send Result
		apdu.setOutgoing();
		apdu.setOutgoingLength((byte)1);
		
		/*byte index;

		for (index = 0; index <= numBytes; index++) */
		buffer[0] = popStack();

		apdu.sendBytes((short)0,(short)1);

		return;
		
	}

	private byte calculate(byte operator, byte left, byte right) 
	{
		byte result;

		switch(operator)
		{
			case '+':
				result=(byte)((byte)left+(byte)right);
				break;
			case '-':
				result=(byte)((byte)left-(byte)right);
				break;
			case '/':
				result=(byte)((byte)left/(byte)right);
				break;
			case '*':
				result=(byte)((byte)left*(byte)right);				
				break;
			default:
				//todo : thorx exception
				result=0;
		}
		return result;
		
				
	}
	private void pushStack(byte number)
	{
		if(isFullStack()==false)
		{
			stackBuffer[(byte)cursor++]=number;
		}
		else
		{
		//todo: retourner excption
		}
	}
	
	private byte popStack()
	{
		if(isEmptyStack()==false)
		{
			return stackBuffer[(byte)cursor--];
		}
		//todo : retourner les exception
		return 0;
	}

	private boolean isEmptyStack()
	{
		if(cursor<=(byte)0)
		{
			cursor=(byte)0;
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

}
