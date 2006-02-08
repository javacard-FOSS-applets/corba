package fr.umlv.ir3.corba.calculator.impl;

import fr.umlv.ir3.corba.calculator.AppletCalculatorPOA;
import fr.umlv.ir3.corba.calculator.InvalidOperator;
import fr.umlv.ir3.corba.calculator.StackOverFlow;

public class CalculatorImpl extends AppletCalculatorPOA{

	public short result(char _operator) throws InvalidOperator {
		return 0;
	}

	public void addNumber(short number) throws StackOverFlow {
		
	}

	public void clear() {
		
	}

}
