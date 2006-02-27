/**
 * 
 */
package fr.umlv.ir3.corba.calculator.applet;

import javacard.framework.ISO7816;
import javacard.framework.ISOException;

/**
 * @author lbarbisan
 *
 */
public class Stack {
	
	//  This buffer contains the string data on the card
	private short stackBuffer[];	
	
	// Cursor for stack
	private short cursor = 0;
	
	private short size;
	
	/**
	 * 
	 * @param size max size of stack
	 */
	public Stack(short size)
	{
		this.size = size;
		stackBuffer = new short[size];
	}
	
	/**
	 * Push on the top of the stack the new number
	 * @param number number to put
	 * @throws SW_FILE_FULL if an overflow occurs
	 */
	public void pushStack(short number)
	{
		if(isFullStack()==false)
		{
			stackBuffer[(short)cursor++]=number;
		}
		else
		{
			ISOException.throwIt(ISO7816.SW_FILE_FULL);
		}
	}
	
	/**
	 * Pop form the top of th stack the current number
	 * @return return the number
	 * @throws SW_FILE_FULL if an underflow occurs
	 */
	public short popStack()
	{
		if(isEmptyStack()==false)
		{
			return stackBuffer[(short)--cursor];
		}
		ISOException.throwIt(ISO7816.SW_FILE_FULL);
		return 0;
	}

	/**
	 * 
	 * @return true if the stack is empty,else false
	 */
	public boolean isEmptyStack()
	{
		if(cursor<=(short)0)
		{
			cursor=(short)0;
			return true;
		}
		return false;
			
	}

	/**
	 *
	 * @return return true, if the stack is full, false else
	 */
	public boolean isFullStack()
	{
		if(cursor>=((short)(size-1)))
		{
			cursor=(short)(size-1);
			return true;
		}
		return false;
	}
	
	
	/**
	 * reset the stack
	 */
	public void clear() {
		//FIXME: renvoyé une comande de success
		cursor=0;
	}
}
