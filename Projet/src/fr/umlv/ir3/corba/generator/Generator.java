package fr.umlv.ir3.corba.generator;


public class Generator {
	private Class file;
	private String packageProxy = "";
	private String packageApplet = "";
	private String destinationPath = "./";
	private String error="";
	
	public boolean generateAppletStub() throws ClassNotFoundException{
		if(file==null) throw new ClassNotFoundException("Attribute class is missing");
		return false;
	} 
	
	public boolean generateProxy() throws ClassNotFoundException{
		if(file==null) throw new ClassNotFoundException("Attribute class is missing");
		return false;
	} 

	public void setInterface(String fullPath) throws ClassNotFoundException {
		this.file = this.getClass().getClassLoader().loadClass(fullPath);
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
}
