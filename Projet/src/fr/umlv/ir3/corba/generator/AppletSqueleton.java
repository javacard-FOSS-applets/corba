package fr.umlv.ir3.corba.generator;


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
	 * @see fr.umlv.ir3.corba.generator.AppletInterface#generateProcessMethod()
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
		.append("}");
		
		return sb.toString();
	}
	
	/**
	 * @see fr.umlv.ir3.corba.generator.AppletInterface#generateInstallMethod()
	 */
	public String generateInstallMethod() 
	{	
		return null;
	}
	
	/**
	 * @see fr.umlv.ir3.corba.generator.AppletInterface#generateSelectMethod()
	 */
	public String generateSelectMethod() 
	{
		return null;
	}
	
	/**
	 * @see fr.umlv.ir3.corba.generator.AbstractSqueleton#generateStartClass(java.lang.StringBuilder)
	 */
	@Override
	protected void generateStartClass(StringBuilder code) {
		//TODO : ajouter les import
		code.append("public class "+ this.className + "extends javacard.framework.Applet {");
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
	
	
	public static void main(String[] args) {
		
	}
	
	
}
