package fr.umlv.ir3.corba.generator.proxy;

import fr.umlv.ir3.corba.generator.Generator;


/**
 * Test main for ReferenceProxyInterface
 * @author lbarbisan
 *
 */
public class Main {
	/**
	 * Main Method
	 * @param args arguements receive for command line
	 * @throws ClassNotFoundException 
	 */
	public static void main(String[] args) throws ClassNotFoundException {
		Generator generator = new Generator("fr.umlv.ir3.corba.generator.proxy.ReferenceProxyInterfaceOperations",
				"A000000003",
				"A00000000301");
		generator.setGeneratedSourcePath("../Applet/src/");
		generator.generateProxy();
		generator.generateAppletStub();
	}
}
