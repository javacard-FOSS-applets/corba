package fr.umlv.ir3.corba.generator.squeleton.applet;

import fr.umlv.ir3.corba.generator.GeneratorInterface;
import fr.umlv.ir3.corba.generator.squeleton.AbstractFreeMarkerSqueleton;
import fr.umlv.ir3.corba.generator.squeleton.proxy.CodeMethodsFactory;


/**
 * @author lbarbisan
 *
 */
public class AppletInterfaceSqueleton extends AbstractFreeMarkerSqueleton 
{
	
	/**
	 * @param squeletonInterface
	 */
	public AppletInterfaceSqueleton(GeneratorInterface squeletonInterface)
	{
		super(squeletonInterface);
	}
	
	@Override
	public String getName() 
	{
		return this.generatorInterface.getClassPrefix() + "InterfaceApplet";
	}
	
	/**
	 * @see fr.umlv.ir3.corba.generator.squeleton.AbstractSqueleton#getPackage()
	 */
	@Override
	public String getPackage() {
		return this.generatorInterface.getPackage().getName() + ".applet";
	}
	
	/** (non-Javadoc)
	 * @see fr.umlv.ir3.corba.generator.squeleton.AbstractSqueleton#generateMethods(java.lang.StringBuilder)
	 */
	@Override
	protected void generateMethods(StringBuilder code) {
		// TODO Auto-generated method stub
		
	}
}
