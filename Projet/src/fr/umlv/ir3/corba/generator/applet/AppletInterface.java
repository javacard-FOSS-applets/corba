package fr.umlv.ir3.corba.generator.applet;

/**
 * 
 * @author olive
 * 
 * this interface represent all the required methods of a javacard applet
 * a generator of applet must implement all of those methods
 */
public interface AppletInterface 
{
	public String generateProcessMethod();
	public String generateInstallMethod();
	public String generateSelectMethod();
}
