<#macro selectGetterType type index>
	//retrieve argument ${index}
 	<#if type.isArray()== true>
	${type.simpleName} arg_${index} = get${type.componentType.simpleName?cap_first}Array(inBuffer);	
	<#else>
	${type} arg_${index} = get${type?cap_first}(inBuffer);
	</#if>
</#macro>
<#macro selectSetterType type index>
	//set argument ${index}
 	<#if type.isArray()== true>
	set${type.componentType.simpleName?cap_first}Array(arg_${index});		
	<#else>
	set${type?cap_first}(arg_${index});
	</#if>
</#macro>
<#macro selectCallArgType type index has_next><#if type.isArray()== true>arg_${index}<#if has_next>,</#if>	<#else>arg_${index}<#if has_next>,</#if></#if></#macro>	
<#macro selectArgType type index has_next><#if type.isArray()== true>${type.simpleName} arg_${index}<#if has_next>,</#if>	<#else>	${type} arg_${index}<#if has_next>,</#if></#if></#macro>
<#macro utils>	
	/**
	* this method retrive a byte in the buffer and increment the index
	* @param index index in the buffer
	* @param buffer
	* @return return the byte
	*/
	private byte getByte(byte[] buffer) {
		//TODO : Vérifie la longueur
		outBufferIndex++;
		return buffer[(short)(outBufferIndex-1)];
	}
	
	/**
	* this method retrive a byte in the buffer and increment the index
	* @param index index in the buffer
	* @param buffer
	* @return return the byte
	*/
	private void setByte(byte value) {
		//TODO : Vérifie la longueur
		outBuffer[outBufferIndex++] = value;
	}
	
	/**
	* this method retrive a byte in the buffer and increment the index
	* @param index index in the buffer
	* @param buffer
	* @return return the byte
	*/
	private short getShort(byte[] buffer) {
		//TODO : Vérifie la longueur
		return bytePairToShort(buffer[(short)(outBufferIndex++)],buffer[(short)(outBufferIndex++)]);
	}
	
	/**
	* this method retrive a byte in the buffer and increment the index
	* @param index index in the buffer
	* @param buffer
	* @return return the byte
	*/
	private void setShort(short value) {
		//TODO : Vérifie la longueur
		byte[] result = shortToBytePair(value);
		outBuffer[outBufferIndex++] = result[0];
		outBuffer[outBufferIndex++] = result[1];
	}
	
	 /**
	* this method retrive a byte in the buffer and increment the index
	* @param index index in the buffer
	* @param buffer
	* @return return the byte
	*/
	private byte[] getByteArray(byte[] buffer) {
	 	short length = buffer[outBufferIndex++];
	 	byte[] bytes = new byte[length];
		for(short index=0;index < length;index++)
		{
			bytes[index] = buffer[outBufferIndex++];
		}
		return bytes;
	}
	
	/**
	* this method retrive a byte in the buffer and increment the index
	* @param index index in the buffer
	* @param buffer
	* @return return the byte
	*/
	private void setByteArray(byte[] values) {
		outBuffer[outBufferIndex++] = (byte)values.length;
		for(short index=0;index < values.length;index++)
		{
			outBuffer[outBufferIndex++] = values[index];
		}
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
</#macro>	