package fr.umlv.ir3.corba.generator.squeleton.proxy;

import java.io.File;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import fr.umlv.ir3.corba.generator.Generator;
import fr.umlv.ir3.corba.generator.GeneratorInterface;
import fr.umlv.ir3.corba.generator.squeleton.AbstractSqueleton;
import fr.umlv.ir3.corba.resources.Resources;
import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;
import freemarker.template.TemplateException;

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
public class ProxySqueleton extends AbstractSqueleton {

	Template template = null;
	Map<String, Object> root = new HashMap<String,Object>();

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
	 * @see fr.umlv.ir3.corba.generator.squeleton.AbstractSqueleton#generateStartClass(java.lang.StringBuilder)
	 */
	@Override
	protected void generateStartClass(StringBuilder code) {
		initFreeMarker();
		root.put("package", generatorInterface.getJavaInterface().getPackage().toString());
	}
	/**
	 * @see fr.umlv.ir3.corba.generator.squeleton.AbstractSqueleton#generateMethods(java.lang.StringBuilder)
	 */
	@Override
	protected void generateMethods(StringBuilder code)
	{
	}
	/**
	 * @see fr.umlv.ir3.corba.generator.squeleton.AbstractSqueleton#generateFinalize(java.lang.StringBuilder)
	 */
	@Override
	protected void generateFinalize(StringBuilder code) {
		
		finalizeFreeMarker(code);
	}

	/**
	 * @see fr.umlv.ir3.corba.generator.squeleton.AbstractSqueleton#generateInitialize(java.lang.StringBuilder)
	 */
	@Override
	protected void generateInitialize(StringBuilder code) {
		
	}

	private void finalizeFreeMarker(StringBuilder code)
	{
		try {
			Writer out = new StringWriter();
			template.process(root, out);
			code.append(out.toString());
		} catch (TemplateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void initFreeMarker()
	{
		try {
			Configuration cfg = new Configuration();
			//Specify the data source where the template files come from.
			//Here I set a file directory for it:
			cfg.setDirectoryForTemplateLoading(new File(Resources.class.getResource(".").getPath()));
			//Specify how templates will see the data model. This is an advanced topic...
			//but just use this:
			cfg.setObjectWrapper(new DefaultObjectWrapper());

			template = cfg.getTemplate("proxy.ftl");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
