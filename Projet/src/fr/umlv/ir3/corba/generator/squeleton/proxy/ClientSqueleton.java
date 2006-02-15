/**
 * 
 */
package fr.umlv.ir3.corba.generator.squeleton.proxy;

import fr.umlv.ir3.corba.generator.GeneratorInterface;

/**
 * @author lbarbisan
 *
 */
public class ClientSqueleton extends AbstractFreeMarkerSqueleton {

	/**
	 * @param squeletonInterface
	 */
	public ClientSqueleton(GeneratorInterface squeletonInterface) {
		super(squeletonInterface);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see fr.umlv.ir3.corba.generator.squeleton.AbstractSqueleton#getName()
	 */
	@Override
	public String getName() {
		return "Client" + generatorInterface.getJavaInterface().getSimpleName();
	}
	/**
	 * @see fr.umlv.ir3.corba.generator.squeleton.AbstractSqueleton#getPackage()
	 */
	@Override
	public String getPackage() {
		return generatorInterface.getJavaInterface().getPackage().getName() + ".client";
	}

	/* (non-Javadoc)
	 * @see fr.umlv.ir3.corba.generator.squeleton.AbstractSqueleton#generateStartClass(java.lang.StringBuilder)
	 */
	@Override
	protected void generateStartClass(StringBuilder code) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see fr.umlv.ir3.corba.generator.squeleton.AbstractSqueleton#generateMethods(java.lang.StringBuilder)
	 */
	@Override
	protected void generateMethods(StringBuilder code) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see fr.umlv.ir3.corba.generator.squeleton.AbstractSqueleton#generateFinalize(java.lang.StringBuilder)
	 */
	@Override
	protected void generateFinalize(StringBuilder code) {
		// TODO Auto-generated method stub

	}
}
