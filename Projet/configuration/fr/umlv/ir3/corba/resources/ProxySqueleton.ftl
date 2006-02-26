${interface.package};

import opencard.cflex.service.CFlex32CardService;
import opencard.core.service.CardServiceException;
import opencard.core.terminal.CardTerminalException;
import opencard.core.terminal.ResponseAPDU;
import opencard.core.util.OpenCardPropertyLoadingException;
import opencard.opt.terminal.ISOCommandAPDU;


/**
 * This class provides an implementation of ${interface.simpleName}POA servant corba object using a distant 
 * applet in card terminal.
 * @author CorbaCard generator
 */
public class ${interface.simpleName}Impl extends ${interface.simpleName}POA{
	
	//This applet is designed to respond to the following
	//class of instructions.
	final static byte ${interface.simpleName}AppletCLA = (byte)${interface.appletCLA};
	//dataBuffer
	byte[] dataBuffer;
	
	//Instruction set for ${interface.simpleName}Applet
	private final static byte GETRESULT = 0x7F;
	<#list interface.getDeclaredMethods() as method>
	private final static byte ${method.name?upper_case} = 0x${interface.instructionsNumber[method_index]};
	</#list>
	
	//JavaCard Manager
	private CFlex32CardService javacard;
	
	/**
	 * Constructs an instance of ReferenceProxyInterface applet client by initializing Java Card access service
     * This method initializes javacard terminal access
	 * @param javacard ?
	 * @throws OpenCardPropertyLoadingException ?
	 * @throws CardServiceException ?
	 * @throws CardTerminalException ?
	 * @throws ClassNotFoundException ?
	 */
	public ${interface.simpleName}Impl(CFlex32CardService javacard) throws OpenCardPropertyLoadingException, CardServiceException, CardTerminalException, ClassNotFoundException{
		super();
		this.javacard = javacard;
	}
    
	<#list interface.getDeclaredMethods() as method>
	
	/**
	*<#list method.getParameterTypes() as type>
	* @param ${type} arg_${type_index}</#list>
	* @return ${method.returnType}
	*/
	public ${method.returnType} ${method.name}(<#list method.getParameterTypes() as type>${type} arg_${type_index}</#list>)
	{
	<#assign conversioncode = methodGenerator.generateConversionTypeCode(method)>${conversioncode}
	}
	</#list>
    
       //========================================== G E S T I O N S  D E S  T R A M E S===========================================
    
	private byte[] sendCommand(byte command, byte[] value) throws CardTerminalException, IllegalAccessException{
		byte[] buffer=null;
		int size;
		
		ResponseAPDU responseAPDU = javacard.sendAPDU(new ISOCommandAPDU(ReferenceProxyInterfaceAppletCLA,command,(byte)0,(byte)0,value,value.length));
    	//If the response contains result, then send a GETRESTULT
    	if(responseAPDU.sw1()==0x62)
    	{
    		responseAPDU = javacard.sendAPDU(new ISOCommandAPDU(ReferenceProxyInterfaceAppletCLA,GETRESULT,(byte)0,(byte)0,new byte[responseAPDU.sw2()],responseAPDU.sw2()));
    	}
    	if(responseAPDU.sw()!=0x9000)
    	{
    		throw new IllegalAccessException("JavaCard return code :" + responseAPDU.sw1() + " " + responseAPDU.sw2());
    	}
    	
    	size = (short) responseAPDU.getLength();
    	if(size==0)
    	{
    		throw new IllegalAccessException("return buffer is null");
    	}
    	else
    	{
    		buffer= new byte[size];
    		for(int index = 0;index<buffer.length;index++)
    		{
    			buffer[index]= responseAPDU.getBuffer()[index];
    		}
    	}
    	return buffer;
    }
    
    private byte[] sendCommand(byte command) throws CardTerminalException, IllegalAccessException{
    	return sendCommand(command, new byte[0]);
    }
    
    /**
	* this method retrive a byte in the buffer and increment the index
	* @param index index in the buffer
	* @param buffer
	* @return return the byte
	*/
	private byte getbyte(int index, byte[] buffer) {
		//TODO : Vérifie la longueur
		index++;
		return buffer[index-1];
	}
}
