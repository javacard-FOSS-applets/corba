package fr.umlv.ir3.corba.generator.squeleton.proxy;

import fr.umlv.ir3.corba.generator.GeneratorInterface;
import fr.umlv.ir3.corba.generator.squeleton.AbstractFreeMarkerSqueleton;

/**
 * This Squeleteton is used to generate access to Applet, for each method it
 * create associated method.
 * this class define a protocle use to send data and receive data in apdu like this :
 * <b>Send Packet</b>
 * The send send packet is decomposed in arguments :
 * byte : contain only the value
 * byte array : contain 1 byte : Lentgth of array, N byte
 * 
 *  <b>Receive Packet</b>
 *  8 bit[1 byte]: N :length of return value
 *  N*8 : Data of argument
 *  
 * @author lbarbisan
 * 
 */
public class ProxySqueleton extends AbstractFreeMarkerSqueleton {

	/**
	 * Constructor of ProxySqueleton
	 * @param generatorInterface Interface which define method
	 */
	public ProxySqueleton(GeneratorInterface generatorInterface) {
		super(generatorInterface);
	}
	/**
	 * @see fr.umlv.ir3.corba.generator.squeleton.AbstractSqueleton#getName()
	 */
	@Override
	public String getName() {
		return generatorInterface.getJavaInterface().getSimpleName() + "Impl";
	}
	/**
	 * @see fr.umlv.ir3.corba.generator.squeleton.AbstractSqueleton#getPackage()
	 */
	@Override
	public String getPackage() {
		return generatorInterface.getJavaInterface().getPackage().getName();
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
