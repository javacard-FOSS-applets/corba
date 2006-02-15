package fr.umlv.ir3.corba.calculator.client;

import java.util.Properties;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

import org.omg.CORBA.ORB;
import org.omg.CORBA.ORBPackage.InvalidName;
import org.omg.CosNaming.NameComponent;
import org.omg.CosNaming.NamingContextExt;
import org.omg.CosNaming.NamingContextExtHelper;
import org.omg.CosNaming.NamingContextPackage.CannotProceed;
import org.omg.CosNaming.NamingContextPackage.NotFound;

import fr.umlv.ir3.corba.calculator.AppletCalculator;
import fr.umlv.ir3.corba.calculator.AppletCalculatorHelper;
import fr.umlv.ir3.corba.calculator.CardException;
import fr.umlv.ir3.corba.calculator.InitializationException;
import fr.umlv.ir3.corba.calculator.InvalidOperator;
import fr.umlv.ir3.corba.calculator.StackOverFlow;
import fr.umlv.ir3.corba.calculator.utils.Util;

public class CalcClient {
	private AppletCalculator calculator;
	
	/** Constructor 
	 * @throws InvalidName 
	 * @throws InvalidName 
	 * @throws org.omg.CosNaming.NamingContextPackage.InvalidName 
	 * @throws org.omg.CosNaming.NamingContextPackage.InvalidName 
	 * @throws CannotProceed 
	 * @throws NotFound 
	 * @throws CannotProceed 
	 * @throws NotFound */
	public CalcClient() throws InvalidName, org.omg.CosNaming.NamingContextPackage.InvalidName, NotFound, CannotProceed  {
		ResourceBundle config = PropertyResourceBundle.getBundle("config");
		String host = config.getString("host");
		String port = config.getString("port");
		String nameObject = config.getString("id");
		
		Properties props = new Properties();
		props.put("org.omg.CORBA.ORBInitialHost",host);
		props.put("org.omg.CORBA.ORBInitialPort",port);
		ORB orb = ORB.init(new String[1],props);

		org.omg.CORBA.Object o;
		o = orb.resolve_initial_references("NameService");
		NamingContextExt context = NamingContextExtHelper.narrow(o);
		NameComponent [] name = context.to_name(nameObject);
		calculator =AppletCalculatorHelper.narrow(context.resolve(name));
	}
    
	public short getResult(char operator) throws InvalidOperator, CardException, InitializationException {
		return calculator.result(operator);
	}
	public void addOperand(short operand) throws StackOverFlow, CardException, InitializationException{
		calculator.addNumber(operand);
	}
	public void clear() throws CardException, InitializationException{
		calculator.clear();
	}
	public static void main(String[] args) {
        try {
            CalcClient client = new CalcClient();
            client.addOperand((short)20);
            client.addOperand((short)1);
            short result = client.getResult('+');
            System.out.println("Resultat: " + result);
        } catch (InvalidName e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (org.omg.CosNaming.NamingContextPackage.InvalidName e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (NotFound e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (CannotProceed e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (StackOverFlow e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (CardException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (InitializationException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (InvalidOperator e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}