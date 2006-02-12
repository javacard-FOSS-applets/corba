package fr.umlv.ir3.corba.calculator.utils;

/**
 * Util classes for conversion number
 * @author lbarbisan
 *
 */
public class Util {

	/**
     * Converts a short into a 2 byte array
     * 
     * @param i short to convert
	 * @return array of 2 byte
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
     * @return short from the 2 byte
     */
    public static short BytePairToShort(byte msb, byte lsb)
    {
        short smsb, slsb;
        smsb = (short)((msb & (short)0x00FF) << (short)8);
        slsb = (short)(lsb & (short)0x00FF);
        return (short) (smsb | slsb);
    }
}
