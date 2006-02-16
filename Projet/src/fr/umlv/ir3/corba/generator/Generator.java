package fr.umlv.ir3.corba.generator;

import java.io.FileNotFoundException;
import java.util.ArrayList;

import fr.umlv.ir3.corba.generator.squeleton.proxy.ClientSqueleton;
import fr.umlv.ir3.corba.generator.squeleton.proxy.ProxySqueleton;
import fr.umlv.ir3.corba.generator.squeleton.proxy.ServerSqueleton;

/**
 * This class is the main class used to generate all necessary classes to communicate
 * with javacard from an corab interface (a .java interface of an idl)
 * @author lbarbisan
 *
 */
public class Generator {
	private Class javaInterface;
	private GeneratorInterface generatorInterface;
	private String generatedSourcePath = "./";
	
	/**
	 * Contructor
	 * @param className FullyQualifiedName of the java interfaco of an idl
	 * @throws ClassNotFoundException id the class isn't found
	 */
	public Generator(String className) throws ClassNotFoundException{	
		this.javaInterface = Class.forName(className);
	}
	
	/**
	 * Generate Proxy Class 
	 */
	public void generateProxy(){
		parseInterface();
		ClientSqueleton clientSqueleton = new ClientSqueleton(generatorInterface);
		ProxySqueleton proxySqueleton = new ProxySqueleton(generatorInterface);
		
		ArrayList<GeneratorInterface> lists = new ArrayList<GeneratorInterface>();
		lists.add(generatorInterface);
		ServerSqueleton serverSqueleton = new ServerSqueleton(lists);
		
		try {
			proxySqueleton.generateFile(this.getGeneratedSourcePath());
			clientSqueleton.generateFile(this.getGeneratedSourcePath());
			serverSqueleton.generateFile(this.getGeneratedSourcePath());
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	} 

	public boolean generateAppletStub() throws ClassNotFoundException{
		if(javaInterface==null) throw new ClassNotFoundException("Attribute class is missing");
		return false;
	} 
	
	/**
	 * Set the source path were the generated sources while be put
	 * @param generatedSourcePath Path
	 */
	public void setGeneratedSourcePath(String generatedSourcePath) {
		this.generatedSourcePath=generatedSourcePath;
	}
	/**
	 * @return Returns the javaInterface.
	 */
	public Class getJavaInterface() {
		return javaInterface;
	}
	/**
	 * @param javaInterface The javaInterface to set.
	 */
	public void setJavaInterface(Class javaInterface) {
		this.javaInterface = javaInterface;
	}
	/**
	 * @return Returns the generatedSourcePath.
	 */
	public String getGeneratedSourcePath() {
		return generatedSourcePath;
	}
	/**
	 * @return Returns the generatorInterface.
	 */
	public GeneratorInterface getGeneratorInterface() {
		return generatorInterface;
	}
	
	/**
	 * Call this method to reparse Interface classes
	 */
	private void parseInterface()
	{
		generatorInterface = new GeneratorInterface(
				javaInterface,
				//FIXME : Relpacer le packageID
				"ApppletID",
				//FIXME : Renplacer le AppletID
				"PackageID",
				(byte) 0x86
		);
	}

}
