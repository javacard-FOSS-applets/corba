package fr.umlv.ir3.corba.generator;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

import fr.umlv.ir3.corba.generator.proxy.ProxySqueleton;

/**
 * This class is the main class used to generate all necessary classes to communicate
 * with javacard from an corab interface (a .java interface of an idl)
 * @author lbarbisan
 *
 */
public class Generator {
	private Class file;
	private InterfaceView interfaceView;
	private String generatedSourcePath = "./";
	
	/**
	 * Contructor
	 * @param className FullyQualifiedName of the java interfaco of an idl
	 * @throws ClassNotFoundException id the class isn't found
	 */
	public Generator(String className) throws ClassNotFoundException{	
		this.file = Class.forName(className);
	}
	
	
	/**
	 * Call this method to reparse Interface classes
	 */
	public void parseInterface()
	{
		interfaceView = new InterfaceView(
				getOperationsMethod(),
				//FIXME : Relpacer le packageID
				"ApppletID",
				//FIXME : Renplacer le AppletID
				"PackageID",
				(byte) 0x86,
				file.getSimpleName()
		);
	}
	
	/**
	 * Generate Proxy Class 
	 */
	public void generateProxy(){
		ProxySqueleton proxySqueleton = new ProxySqueleton(interfaceView);
	} 

	public boolean generateAppletStub() throws ClassNotFoundException{
		if(file==null) throw new ClassNotFoundException("Attribute class is missing");
		return false;
	} 
	
	public void setInterface(String name) throws ClassNotFoundException {
		this.file = Class.forName(name);
	}
	
//	public String getError() {
//		return error;
//	}
//	public String getPackageApplet() {
//		return packageApplet;
//	}
//	public void setPackageApplet(String packageApplet) {
//		this.packageApplet = packageApplet;
//	}
//	public String getPackageProxy() {
//		return packageProxy;
//	}
//	public void setPackageProxy(String packageProxy) {
//		this.packageProxy = packageProxy;
//	}
//	public String getDestinationPath() {
//		return destinationPath;
//	}
//	public void setDestinationPath(String destinationPath) {
//		this.destinationPath = destinationPath;
//	}
//	public String getInterface() throws ClassNotFoundException {
//		if(file==null) throw new ClassNotFoundException("Attribute class is missing");
//		return file.getName();
//	}

	private List<Method> getOperationsMethod() {
		return Arrays.asList(this.file.getMethods());
	}


	/**
	 * Set the source path were the generated sources while be put
	 * @param generatedSourcePath Path
	 */
	public void setGeneratedSourcePath(String generatedSourcePath) {
		this.generatedSourcePath=generatedSourcePath;
	}
}
