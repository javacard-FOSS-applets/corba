package fr.umlv.ir3.corba.calculator;


/**
* fr/umlv/ir3/corba/calculator/AppletCalculatorOperations.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from calculator.idl
* mardi 7 f�vrier 2006 13 h 39 CET
*/

public interface AppletCalculatorOperations 
{
  short result (char _operator) throws fr.umlv.ir3.corba.calculator.InvalidOperator;
  void addNumber (short number) throws fr.umlv.ir3.corba.calculator.StackOverFlow;
  void clear ();
} // interface AppletCalculatorOperations
