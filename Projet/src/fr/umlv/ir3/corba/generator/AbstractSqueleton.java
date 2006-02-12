package fr.umlv.ir3.corba.generator;

import java.io.FileNotFoundException;
import java.io.PrintWriter;

/**
 * 
 * @author olivier Boitel
 *
 */
public abstract class AbstractSqueleton 
{
	protected InterfaceView SqueletonInterface;
	protected String className;
	
	
	protected AbstractSqueleton(InterfaceView squeletonInterface) 
	{
		this.SqueletonInterface = squeletonInterface;
		this.className = setName();
		
	}
	
	/**
	 * this method set the squeleton class name
	 * @return the name of the class
	 */
	abstract public String setName();
	
	/**
	 * this method generate the squeleton of the class to create
	 * @param path the path where the squeleton will be created
	 * @throws FileNotFoundException if the the path given in argument doesn't exist
	 */
	abstract public void generateSqueleton(String path) throws FileNotFoundException;
	
	/**
	 * this method generate the header of the class
	 * @return the header of the class
	 */
	
	abstract protected String starClass();
	
	protected String endClass()
	{
		return "}";
	}
	
	/**
	 * indent a string in the code of the class
	 * @param nbIndent the number of indentation
	 * @return the string indented
	 */
	protected String indent(int nbIndent)
	{
		String indent = "";
		for (int i = 0; i < nbIndent; i++) 
		{
			indent+="\t";
		}
		return indent;
	}
	
	public static String treatPath(String path)
	{	
		if(path.endsWith("/"))
			return path;
		else
			return path+"/";
	}
	
}
