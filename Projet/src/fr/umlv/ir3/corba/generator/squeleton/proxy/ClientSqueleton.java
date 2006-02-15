/**
 * 
 */
package fr.umlv.ir3.corba.generator.squeleton.proxy;

import fr.umlv.ir3.corba.generator.GeneratorInterface;
import fr.umlv.ir3.corba.generator.squeleton.AbstractFreeMarkerSqueleton;

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

	/**
	 * @see fr.umlv.ir3.corba.generator.squeleton.AbstractSqueleton#generateStartClass(java.lang.StringBuilder)
	 */
	@Override
	protected void generateStartClass(StringBuilder code) {}

	/**
	 * @see fr.umlv.ir3.corba.generator.squeleton.AbstractSqueleton#generateMethods(java.lang.StringBuilder)
	 */
	@Override
	protected void generateMethods(StringBuilder code) {}
}
