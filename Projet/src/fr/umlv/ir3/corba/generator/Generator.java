package fr.umlv.ir3.corba.generator;

import java.lang.reflect.Method;

public class Generator {
	private Class file;
	private String packageProxy = "";
	private String packageApplet = "";
	private String destinationPath = "./";
	private String error="";
	
	public Generator(){
	}
	
	public Generator(String className) throws ClassNotFoundException{	
		this.file = Class.forName(className);
	}
	
	public boolean generateAppletStub() throws ClassNotFoundException{
		if(file==null) throw new ClassNotFoundException("Attribute class is missing");
		return false;
	} 
	
	public boolean generateProxy() throws ClassNotFoundException{
		if(file==null) throw new ClassNotFoundException("Attribute class is missing");
		return false;
	} 

	public void setInterface(String name) throws ClassNotFoundException {
		this.file = Class.forName(name);
	}
	
	public String getError() {
		return error;
	}
	public String getPackageApplet() {
		return packageApplet;
	}
	public void setPackageApplet(String packageApplet) {
		this.packageApplet = packageApplet;
	}
	public String getPackageProxy() {
		return packageProxy;
	}
	public void setPackageProxy(String packageProxy) {
		this.packageProxy = packageProxy;
	}
	public String getDestinationPath() {
		return destinationPath;
	}
	public void setDestinationPath(String destinationPath) {
		this.destinationPath = destinationPath;
	}
	public String getInterface() throws ClassNotFoundException {
		if(file==null) throw new ClassNotFoundException("Attribute class is missing");
		return file.getName();
	}

	public Method[] getOperationsMethod() {
		Method[] methods = this.file.getMethods();
		return methods;
	}
}
