<#import "generals.ftl" as tools>
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
	private byte[] outBuffer = new byte[255];
	private short outBufferIndex;
	
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
	public ${method.returnType} ${method.name}(<#list method.getParameterTypes() as type><@tools.selectArgType type=type index=type_index has_next=type_has_next/></#list>)
	{
		byte[] buffer=null;
		outBufferIndex=0;
		<#list method.getParameterTypes() as type>
		<@tools.selectSetterType type=type index=type_index/> 
		</#list>
		try {
			buffer = sendCommand(${method.name?upper_case});
		} catch (CardTerminalException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		outBufferIndex=0;
		<#if method.getReturnType().toString()!="void">
		return get${method.returnType?cap_first}(buffer);
		</#if>
	}
	</#list>
    
       //========================================== G E S T I O N S  D E S  T R A M E S===========================================
    
	private byte[] sendCommand(byte command) throws CardTerminalException, IllegalAccessException{
		byte[] buffer=null;
		int size;
	
		buffer = new byte[outBufferIndex];
		for(int index = 0;index <outBufferIndex;index++)
		{
			buffer[index] = outBuffer[index];
		}
		
		ResponseAPDU responseAPDU = javacard.sendAPDU(new ISOCommandAPDU(ReferenceProxyInterfaceAppletCLA,command,(byte)0,(byte)0,outBuffer,outBufferIndex));
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
<@tools.utils/>
}
