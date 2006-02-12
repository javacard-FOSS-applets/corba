package fr.umlv.ir3.corba.generator;

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
	
	
}
