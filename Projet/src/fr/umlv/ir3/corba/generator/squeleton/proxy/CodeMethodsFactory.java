/**
 * 
 */
package fr.umlv.ir3.corba.generator.squeleton.proxy;

import java.lang.reflect.Array;
import java.lang.reflect.Method;

/**
 * This class is used to generate method, depends of signature it's used for
 * proxy squeleton. This class implement singleton pattern
 * 
 * @author lbarbisan
 */
public class CodeMethodsFactory {

	private static CodeMethodsFactory codeMethodsFactory = null;

	private int bufferLength = 0;

	private CodeMethodsFactory() {
	}

	/**
	 * Create or return the unique instance of codeMethodsFactory
	 * 
	 * @return CodeMethodsFactory
	 */
	public static CodeMethodsFactory createCodeMethodsFactory() {
		if (codeMethodsFactory == null) {
			codeMethodsFactory = new CodeMethodsFactory();
		}
		return codeMethodsFactory;
	}

	/**
	 * Return the code corresponding to the specified method
	 * 
	 * @param method
	 *            method to generate code
	 * @return code
	 */
	public String generateConversionTypeCode(Method method) {
		StringBuilder beforeAlloc = new StringBuilder();
		StringBuilder afterAlloc = new StringBuilder();

		beforeAlloc.append("dataBuffer = new byte[");
		afterAlloc.append("];\n");

		if (method.getParameterTypes().length != 0) {
			for (int index = 0; index < method.getParameterTypes().length; index++) {
				Class klass = method.getParameterTypes()[index];
				afterAlloc.append(generateArguments(klass, index));
			}
		}

		beforeAlloc.append(bufferLength).append(afterAlloc).append(
				"\t\ttry {\n");

		if (bufferLength != 0) {

			beforeAlloc.append("\t\t\tsendCommand("
					+ method.getName().toUpperCase() + ", dataBuffer);\n");
		} else {
			beforeAlloc.append("\t\t\tsendCommand("
					+ method.getName().toUpperCase() + ");\n");
		}

		beforeAlloc.append("\t\t} catch (CardTerminalException e) {\n").append(
				"\t\t\t// TODO Auto-generated catch block\n").append(
				"\t\t\te.printStackTrace();\n").append(
				"\t\t} catch (InitializationException e) {\n").append(
				"\t\t\t// TODO Auto-generated catch block\n").append(
				"\t\t\te.printStackTrace();}\n");
		bufferLength = 0;
		return beforeAlloc.toString();
	}

	/**
	 * Generate arguents for specified method
	 * 
	 * @param method
	 *            method
	 * @return return the string code represent arguments
	 */
	private String generateArguments(Class parameter, int parameterIndex) {
		StringBuilder code = new StringBuilder();

		if (parameter.isArray()) {
			long length;
			bufferLength++;
			length = Array.getLength(parameter);
			for (int index = 0; index < length; index++)
				code.append(
						generateSimpleArgument(Array.get(parameter, index)
								.getClass(), "arg_" + parameterIndex + "["
								+ index + "]")).append("\n");
			return code.toString();
		} else {
			return generateSimpleArgument(parameter, "arg_" + parameterIndex);
		}
	}

	private String generateSimpleArgument(Class parameter, String rightAffection) {
		if (parameter == byte.class) {
			bufferLength++;
			return "dataBuffer[" + (bufferLength - 1) + "]=" + rightAffection
					+ ";\n";
		} else if (parameter == short.class) {
			bufferLength++;
			return "dataBuffer[" + (bufferLength - 1) + "]=" + rightAffection
					+ ";\n";
		}
		return "";

	}
}
