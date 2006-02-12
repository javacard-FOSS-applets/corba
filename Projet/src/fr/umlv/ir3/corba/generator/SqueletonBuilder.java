package fr.umlv.ir3.corba.generator;


import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.util.ArrayList;

import javacard.framework.APDU;
import javacard.framework.ISOException;



public class SqueletonBuilder {

	// liste des methodes de l'interface IDL
	private ArrayList<Method> interfaceMethods;
	// liste des numeros d'instructions correspondant aux methodes
	private byte [] instructionsNumber;
	
	private String appletName;
	private String proxyName;
	
	public SqueletonBuilder(ArrayList<Method> methods){
		this.interfaceMethods = methods;
		this.instructionsNumber = new byte [this.interfaceMethods.size()];
		generateInstructionNumbers();
	}
	
	/**
	 * this method generate all number for the instructions used in the applet
	 * those number are used to call methods in the applet 
	 * 
	 */
	private void generateInstructionNumbers(){
		byte increment = 0x10;
		this.instructionsNumber[0] = increment;
		
		for (int i = 1; i < this.interfaceMethods.size(); i++) {
			increment += 0x10;
			this.instructionsNumber[i] = increment; 
		}
	}
	
	/**
	 * this method allow to change the interface of methods used to generate squeletons 
	 * @param methods the array of methods representing the interface 
	 */
	public void setInterface(ArrayList<Method> methods){
		this.interfaceMethods = methods;
		generateInstructionNumbers();
	}
	
	public void generateProcessMethod(PrintWriter appletFile){
		
		appletFile.println("public void process(APDU apdu) throws ISOException{");
		
	}
	
	public static void main(String[] args) {
		
	}
}
