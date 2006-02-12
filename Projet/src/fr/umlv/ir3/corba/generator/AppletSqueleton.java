package fr.umlv.ir3.corba.generator;

public class AppletSqueleton extends AbstractSqueleton 
{

	public AppletSqueleton(InterfaceView squeletonInterface)
	{
		super(squeletonInterface);
	}
	
	@Override
	public String setName() 
	{
		return this.SqueletonInterface.getClassPrefix() + "Applet";
	}
	
	

}
