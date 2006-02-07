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
package opencard.cflex.test;

import javacard.framework.APDU;
import javacard.framework.ISO7816;
import javacard.framework.ISOException;

// Class Definition

public class SimpleString extends javacard.framework.Applet {

	//  This applet is designed to respond to the following
	//  class of instructions.

	final static byte SimpleString_CLA = (byte)0x85;

	//  Instruction set for SimpleString

	final static byte SET = (byte)0x10;
	final static byte GET = (byte)0x20;
	final static byte SELECT = (byte) 0xA4;
	//  Declare your instance variables here

	//byte buffer[];		//  This buffer holds APDU's for processing
	byte TheBuffer[];	//  This buffer contains the string data on the card

	//  The constructor. 

	private SimpleString(byte buffer[],short offset,byte length) {

		TheBuffer = new byte[100];

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

		new SimpleString(buffer, offset, length);

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
		
		if (buffer[ISO7816.OFFSET_CLA] != SimpleString_CLA) 
				ISOException.throwIt(ISO7816.SW_CLA_NOT_SUPPORTED);
    
        byte ins = buffer[ISO7816.OFFSET_INS];
        
		switch (ins) {

		case SET:
			SetString(apdu);
			break;
		case GET:
			GetString(apdu);
			break;
		default:
			ISOException.throwIt(ISO7816.SW_INS_NOT_SUPPORTED);
		}

	}

	//  Class methods

	//  SetString stores the string on the card.

	private void SetString(APDU apdu) {

		byte buffer[] = apdu.getBuffer();

		byte size = (byte)(apdu.setIncomingAndReceive());

		byte index;

		//  Store the length of the string and the string itself

		TheBuffer[0] = size;

		for (index = 0; index < size; index++) 
			TheBuffer[(byte)(index + 1)] = buffer[(byte)(ISO7816.OFFSET_CDATA + index)];

		return;

	}

	//  GetString retrieves the string from the card.
	//  This is actually the interesting function.  When a client
	//  asks for the string, it really has no way of knowing how
	//  large the string would be.  We handle this with the following steps:
	//		
	//		1.  Client sends a GetString APDU with a length of 0
	//		2.  Card responds with a Status Word of 0x62YY, where YY is the length
	//			of the string (in hex).
	//		3.  The client sends its GetString APDU again, but this time with the
	//			correct length.
	//

	private void GetString(APDU apdu) {

		byte buffer[] = apdu.getBuffer();

		byte numBytes = buffer[ISO7816.OFFSET_LC];


		if (numBytes == (byte)0) {
			ISOException.throwIt((short)(0x6200 + TheBuffer[0]));
		}

		apdu.setOutgoing();
		apdu.setOutgoingLength(numBytes);
		

		byte index;

		for (index = 0; index <= numBytes; index++) 
			buffer[index] = TheBuffer[(byte)(index + 1)];

		apdu.sendBytes((short)0,(short)numBytes);


		return;
	}


}
