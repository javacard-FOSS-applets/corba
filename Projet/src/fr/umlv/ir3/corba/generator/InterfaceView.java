package fr.umlv.ir3.corba.generator;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * 
 * @author Olivier Boitel
 *
 * this class represent all the common declarations used to generate applet
 * and proxy squeletons
 */
public class InterfaceView {
	
//	liste des methodes de l'interface IDL
	private ArrayList<Method> interfaceMethods;
	// liste des numeros d'instructions correspondant aux methodes
	private int [] instructionsNumber;
	
	private String classPrefix;
	
	private byte applet_CLA;
	
	public InterfaceView(ArrayList<Method> methods,byte appletID,String prefix)
	{
		this.interfaceMethods = methods;
		this.instructionsNumber = new int [this.interfaceMethods.size()];
		generateInstructionNumbers();
		
		this.classPrefix = prefix;
		this.applet_CLA = appletID; 
	}
	
	/**
	 * this method generate all number for the instructions used in the applet
	 * those number are used to call methods in the applet 
	 * 
	 */
	private void generateInstructionNumbers(){
		int increment = 10;
		this.instructionsNumber[0] = increment;
		
		for (int i = 1; i < this.interfaceMethods.size(); i++) {
			increment += 10;
			this.instructionsNumber[i] = increment; 
		}
	}
	
	/**
	 * this method gives all the necessary declarations to use and 
	 * call methods in teh applet 
	 * @return the array of all declarations
	 */
	public String[] getDeclaredInstruction()
	{
		int nbMethods = interfaceMethods.size();
		
		String[] declarations = new String [nbMethods];
		
		for(int i=0; i < nbMethods; i++)
		{
			Method m = interfaceMethods.get(i);
			
			declarations[i] ="final static byte "
				+ m.getName().toUpperCase()+" = (byte)0x"+this.instructionsNumber[i];  
		}
		
		return declarations;
	}
	
	public String[] getInstructionNames()
	{
		int nbMethods = interfaceMethods.size();
		
		String[] declarations = new String [nbMethods];
		
		for(int i=0; i < nbMethods; i++)
		{
			Method m = interfaceMethods.get(i);
			
			declarations[i] = m.getName();  
		}
		
		return declarations;
	}
	
	public String getClassPrefix() {
		return classPrefix;
	}
	
	public void setClassPrefix(String classPrefix) {
		this.classPrefix = classPrefix;
	}
	
	public Iterator getMethodsIterator()
	{
		return this.interfaceMethods.iterator();
	}
	
}
