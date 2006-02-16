package fr.umlv.ir3.corba.generator;

import java.lang.reflect.Method;

/**
 * 
 * @author Olivier Boitel
 *
 * this class represent all the common declarations used to generate applet
 * and proxy squeletons
 */
public class GeneratorInterface {
	
	// liste des methodes de l'interface IDL
	private Class javaInterface;
	// liste des numeros d'instructions correspondant aux methodes
	private int [] instructionsNumber;
	private String classPrefix;
	private byte appletCLA;
	private String appletID;
	private String appletPackage;
	
	/**
	 * Constructor, this constructor must be bulid ton construct object
	 * @param javaInterface list of the method interface from java interface
	 * @param AppletID appletID for the javacard ie A00000000201, it must include PackageID (here A000000002)
	 * @param PackageID packageID for the javacard A0000000002
	 * @param appletCLA CLA for the javacard (ie 0x86)
	 *
	 * @param prefix ?
	 */
	//TODO : lbarbisan - A quoi sert l'argument prefix 
	public GeneratorInterface(Class javaInterface, String appletID, String appletPackage,  byte appletCLA)
	{
		this.javaInterface = javaInterface;
		this.instructionsNumber = new int [javaInterface.getDeclaredMethods().length];
		generateInstructionNumbers();

		this.appletCLA = appletCLA; 
		this.appletID = appletID;
		this.appletPackage= appletPackage;
		this.classPrefix = getSimpleName();
	}
	
	/**
	 * this method generate all number for the instructions used in the applet
	 * those number are used to call methods in the applet 
	 * 
	 */
	private void generateInstructionNumbers(){
		int increment = 10;
		this.instructionsNumber[0] = increment;
		
		for (int i = 1; i < this.javaInterface.getDeclaredMethods().length; i++) {
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
		int nbMethods = javaInterface.getDeclaredMethods().length;
		
		String[] declarations = new String [nbMethods];
		
		for(int i=0; i < nbMethods; i++)
		{
			Method m = javaInterface.getDeclaredMethods()[i];
			
			declarations[i] ="final static byte "
				+ m.getName().toUpperCase()+" = (byte)0x"+this.instructionsNumber[i];  
		}
		
		return declarations;
	}
	
	/**
	 * return the instruction name for javacard
	 * @return list of instruction names
	 */
	public String[] getInstructionNames()
	{
		int nbMethods = javaInterface.getDeclaredMethods().length;
		
		String[] declarations = new String [nbMethods];
		
		for(int i=0; i < nbMethods; i++)
		{
			Method m = javaInterface.getDeclaredMethods()[i];
			
			declarations[i] = m.getName();  
		}
		
		return declarations;
	}
	

	/**
	 * @return Returns the applet_CLA.
	 */
	public byte getAppletCLA() {
		return appletCLA;
	}
	/**
	 * @return Returns the classPrefix.
	 */
	public String getClassPrefix() {
		return classPrefix;
	}

	/**
	 * @return Method[]
	 * @throws SecurityException 
	 * @see java.lang.Class#getDeclaredMethods()
	 */
	public Method[] getDeclaredMethods() throws SecurityException {
		return javaInterface.getDeclaredMethods();
	}

	/**
	 * @return Package
	 * @see java.lang.Class#getPackage()
	 */
	public Package getPackage() {
		return javaInterface.getPackage();
	}

	/**
	 * @return String
	 * @see java.lang.Class#getSimpleName()
	 */
	public String getSimpleName() {
		//FIXME : Trouver un truc moins degeulasse
		return javaInterface.getSimpleName().replace("Operations","");
	}

	/**
	 * @return Returns the appletID.
	 */
	public String getAppletID() {
		return appletID;
	}

	/**
	 * @return Returns the appletPackage.
	 */
	public String getAppletPackage() {
		return appletPackage;
	}
}
