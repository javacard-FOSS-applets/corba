package fr.umlv.ir3.corba.generator.applet;

import fr.umlv.ir3.corba.generator.AbstractSqueleton;
import fr.umlv.ir3.corba.generator.InterfaceView;


public class AppletSqueleton extends AbstractSqueleton implements AppletInterface
{
	
	public AppletSqueleton(InterfaceView squeletonInterface)
	{
		super(squeletonInterface);
	}
	
	@Override
	public String setName() 
	{
		return this.SqueletonInterface.getClassPrefix() + "Applet";
	}
	
	/**
	 * @see fr.umlv.ir3.corba.generator.applet.AppletInterface#generateProcessMethod()
	 */
	public String generateProcessMethod() 
	{
		StringBuilder sb = new StringBuilder();
		
		sb.append("public void process(APDU apdu) throws ISOException{\n")
		.append(indent(1)).append("byte buffer[] = apdu.getBuffer();\n")
		.append(indent(1)).append("if (selectingApplet()) {\n")
		.append(indent(2)).append("SOException.throwIt(ISO7816.SW_NO_ERROR);\n")
		.append(indent(1)).append("}\n")
		.append(indent(1)).append(" byte ins = buffer[ISO7816.OFFSET_INS];\n")
		.append( indent(1)).append("switch (ins) {\n");
		
		//TODO : ajouter le test sur le CLA
		
		String[] instructions = this.SqueletonInterface.getInstructionNames();
		for (int i = 0; i < instructions.length; i++) 
		{
			sb.append(indent(1)).append("case").append(instructions[i].toUpperCase()).append(":\n")
			.append(indent(2)).append(instructions[i]).append("(apdu);\n")
			.append(indent(2)).append("break;\n");
		}
		
		sb.append(indent(1)).append("default :\n")
		.append("ISOException.throwIt(ISO7816.SW_INS_NOT_SUPPORTED);\n")
		.append("}\n");
		
		return sb.toString();
	}
	
	/**
	 * @see fr.umlv.ir3.corba.generator.applet.AppletInterface#generateInstallMethod()
	 */
	public String generateInstallMethod() 
	{	

		StringBuilder sb = new StringBuilder();
		
		sb.append("public static void install(byte buffer[],short offset,byte length) {\n")
		.append(indent(1)).append("new").append(this.className).append("(buffer, offset, length);\n")
		.append("}\n");
		
		return sb.toString();

	}
	
	/**
	 * @see fr.umlv.ir3.corba.generator.applet.AppletInterface#generateSelectMethod()
	 */
	public String generateSelectMethod() 
	{

		StringBuilder sb = new StringBuilder();
		sb.append("public boolean select() {\n")
		.append(indent(1)).append("return true;\n")
		.append("}\n");
		
		return sb.toString();
	}
	
	/**
	 * @see fr.umlv.ir3.corba.generator.AbstractSqueleton#generateStartClass(java.lang.StringBuilder)
	 */
	@Override
	protected void generateStartClass(StringBuilder code) {
		//TODO : ajouter les import
		code.append("public class "+ this.className + "extends javacard.framework.Applet {\n");
	}
	
	/**
	 * @see fr.umlv.ir3.corba.generator.AbstractSqueleton#generateMethods(java.lang.StringBuilder)
	 */
	@Override
	protected void generateMethods(StringBuilder code) {
		
	}
	
	/**
	 * @see fr.umlv.ir3.corba.generator.AbstractSqueleton#generateFinalize(java.lang.StringBuilder)
	 */
	@Override
	protected void generateFinalize(StringBuilder code) {
	
	}
	
	/**
	 * @see fr.umlv.ir3.corba.generator.AbstractSqueleton#generateInitialize(java.lang.StringBuilder)
	 */
	@Override
	protected void generateInitialize(StringBuilder code) {
		
	}
	
	
}
