package fr.umlv.ir3.corba.generator;

import java.io.FileNotFoundException;
import java.io.PrintWriter;

import javacard.framework.ISO7816;
import javacard.framework.ISOException;

public class AppletSqueleton extends AbstractSqueleton implements appletInterface
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

	@Override
	public void generateSqueleton(String path) throws FileNotFoundException 
	{	
		String fullPath = AbstractSqueleton.treatPath(path)+this.className+".java";
		PrintWriter outFile = new PrintWriter(fullPath);
		
		//on insere l'ensemble des instruction dans le fichier créé
		
	}

	/**
	 * @see fr.umlv.ir3.corba.generator.appletInterface#generateProcessMethod()
	 */
	public String generateProcessMethod() 
	{
		String method = "";
		method+= "public void process(APDU apdu) throws ISOException{\n";
		method+= indent(1) + "byte buffer[] = apdu.getBuffer();\n";
		method+= indent(1) + "if (selectingApplet()) {\n";
		method+= indent(2) + "SOException.throwIt(ISO7816.SW_NO_ERROR);\n";
		method+= indent(1) + "}\n";
		
		method+= indent(1) + " byte ins = buffer[ISO7816.OFFSET_INS];\n";
		method+= indent(1) + "switch (ins) {\n";
		
		String[] instructions = this.SqueletonInterface.getInstructionNames();
		for (int i = 0; i < instructions.length; i++) 
		{
			method+= indent(1) + "case" + instructions[i]+":\n";
		}
		
		/*
		
		if (buffer[ISO7816.OFFSET_CLA] != CalculatorApplet_CLA) 
				ISOException.throwIt(ISO7816.SW_CLA_NOT_SUPPORTED);
    
        byte ins = buffer[ISO7816.OFFSET_INS];
        
		switch (ins) {

		case PUSH:
			push(apdu);
			break;
		case POP:
			pop(apdu);
			break;
		case RESULT:
			result(apdu);
			break;
		case CLEAR:
			clear();
			break;
		default:
			ISOException.throwIt(ISO7816.SW_INS_NOT_SUPPORTED);
		}*/
		
		return method;
	}

	/**
	 * @see fr.umlv.ir3.corba.generator.appletInterface#generateInstallMethod()
	 */
	public String generateInstallMethod() 
	{	
		return null;
	}

	/**
	 * @see fr.umlv.ir3.corba.generator.appletInterface#generateSelectMethod()
	 */
	public String generateSelectMethod() 
	{
		return null;
	}

	/**
	 * @see fr.umlv.ir3.corba.generator.AbstractSqueleton#starClass()
	 */
	@Override
	protected String starClass() 
	{
		return "public class "+ this.className + "extends javacard.framework.Applet {";
	}

	
	
	

}
