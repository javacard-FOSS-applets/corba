${interface.package};

import opencard.cflex.service.CFlex32CardService;
import opencard.core.service.CardServiceException;
import opencard.core.terminal.CardTerminalException;
import opencard.core.terminal.ResponseAPDU;
import opencard.core.util.OpenCardPropertyLoadingException;
import opencard.opt.terminal.ISOCommandAPDU;
import fr.umlv.ir3.corba.calculator.InitializationException;

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
	<#list interface.getDeclaredMethods() as method>
	private byte ${method.name?upper_case} = 0x${method_index+1}0;
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
	*
	<#list method.getParameterTypes() as type>* @param ${type} arg_${type_index}</#list>
	*/
	public void ${method.name}(<#list method.getParameterTypes() as type>${type} arg_${type_index}</#list>)
	{
	<#assign conversioncode = methodGenerator.generateConversionTypeCode(method)>
${conversioncode}
	}
	</#list>
    
    private ResponseAPDU sendCommand(byte command, byte[] value) throws CardTerminalException, InitializationException{
    	return javacard.sendAPDU(new ISOCommandAPDU(${interface.simpleName}AppletCLA,command,(byte)0,(byte)0,value,value.length));
    }
    
    private ResponseAPDU sendCommand(byte command) throws CardTerminalException, InitializationException{
    	return javacard.sendAPDU(new ISOCommandAPDU(${interface.simpleName}AppletCLA,command,(byte)0,(byte)0,new byte[0],0));
    }
}
