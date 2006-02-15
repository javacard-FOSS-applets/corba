/**
 * 
 */
package fr.umlv.ir3.corba.generator.squeleton;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

import fr.umlv.ir3.corba.generator.GeneratorInterface;
import fr.umlv.ir3.corba.resources.Resources;
import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;
import freemarker.template.TemplateException;

/**
 * This class implements AbstractSkeleton for using FreeMarker for generating code
 * @author lbarbisan
 *
 */
public abstract class AbstractFreeMarkerSqueleton extends AbstractSqueleton {
	
	protected Template template = null;
	protected Map<String, Object> root = new HashMap<String,Object>();
	
	/**
	 * @param squeletonInterface
	 */
	protected AbstractFreeMarkerSqueleton(GeneratorInterface squeletonInterface) {
		super(squeletonInterface);
	}
	
	/**
	 * @see fr.umlv.ir3.corba.generator.squeleton.AbstractSqueleton#generateInitialize(java.lang.StringBuilder)
	 */
	@Override
	protected void generateInitialize(StringBuilder code) {
		initFreeMarker();
		root.put("interface", generatorInterface);
	}
	
	/**
	 * @see fr.umlv.ir3.corba.generator.squeleton.AbstractSqueleton#generateFinalize(java.lang.StringBuilder)
	 */
	@Override
	protected void generateFinalize(StringBuilder code) {
		finalizeFreeMarker(code);
	}
	
	/**
	 * Initialize FreeMarker
	 *
	 */
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

			template = cfg.getTemplate(this.getClass().getSimpleName() + ".ftl");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * Write Code for free marker
	 * @param code
	 */
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
}
