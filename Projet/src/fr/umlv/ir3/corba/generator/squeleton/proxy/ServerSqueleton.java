/**
 * 
 */
package fr.umlv.ir3.corba.generator.squeleton.proxy;

import java.util.List;

import fr.umlv.ir3.corba.generator.GeneratorInterface;
import fr.umlv.ir3.corba.generator.squeleton.AbstractFreeMarkerSqueleton;

/**
 * @author lbarbisan
 *
 */
public class ServerSqueleton extends AbstractFreeMarkerSqueleton{
	private List<GeneratorInterface> generatorInterfaces; 
	
	/**
	 * @param squeletonInterface
	 */
	public ServerSqueleton(List<GeneratorInterface> generatorInterfaces) {
		super(generatorInterfaces.get(0));
		this.generatorInterfaces = generatorInterfaces;
	}

	/**
	 * @see fr.umlv.ir3.corba.generator.squeleton.AbstractSqueleton#getName()
	 */
	@Override
	public String getName() {
		return "Server";
	}
	/**
	 * @see fr.umlv.ir3.corba.generator.squeleton.AbstractSqueleton#getPackage()
	 */
	@Override
	public String getPackage() {
		return generatorInterface.getJavaInterface().getPackage().getName() + ".server";
	}
	/**
	 * @see fr.umlv.ir3.corba.generator.squeleton.AbstractFreeMarkerSqueleton#generateInitialize(java.lang.StringBuilder)
	 */
	@Override
	protected void generateInitialize(StringBuilder code) {
		super.generateInitialize(code);
		root.put("generatorInterfaces",generatorInterfaces);
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
