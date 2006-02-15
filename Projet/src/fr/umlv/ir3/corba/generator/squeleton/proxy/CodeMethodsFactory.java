/**
 * 
 */
package fr.umlv.ir3.corba.generator.squeleton.proxy;

import java.lang.reflect.Method;

/**
 * This class is used to generate method, depends of signature
 * it's used for proxy squeleton. This class implement singleton pattern
 * @author lbarbisan
 */
public class CodeMethodsFactory {
	
	private static CodeMethodsFactory codeMethodsFactory=null;
	
	private CodeMethodsFactory(){}
	
	/**
	 * Create or return the unique instance of codeMethodsFactory
	 * @return CodeMethodsFactory
	 */
	public static CodeMethodsFactory createCodeMethodsFactory()
	{
		if(codeMethodsFactory==null)
		{
			codeMethodsFactory= new CodeMethodsFactory();
		}
		return codeMethodsFactory;
	}
	
	/**
	 * Return the code corresponding to the specified method
	 * @param method method to generate code
	 * @return code
	 */
	public String generateMethodCode(Method method)
	{
		StringBuilder code = new StringBuilder();
		code.append("private ").append(method.getName());
		//.append(method.)
		return null;
		
	}
	
	
	/**
	 * Generate arguents for spcfied method
	 * @param method method
	 * @return return the string code represent arguments
	 */
	private String generateArgument(Method method)
	{
		return null;
	}
}
