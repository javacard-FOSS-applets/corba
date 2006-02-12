package fr.umlv.ir3.corba.generator;

/**
 * 
 * @author olive
 * 
 * this interface represent all the required methods of a javacard applet
 * a generator of applet must implement all of those methods
 */
public interface appletInterface 
{
	public String generateProcessMethod();
	public String generateInstallMethod();
	public String generateSelectMethod();
}
