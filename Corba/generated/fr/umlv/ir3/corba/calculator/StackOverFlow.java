package fr.umlv.ir3.corba.calculator;


/**
* fr/umlv/ir3/corba/calculator/StackOverFlow.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from calculator.idl
* mardi 7 f�vrier 2006 13 h 39 CET
*/

public final class StackOverFlow extends org.omg.CORBA.UserException
{
  public String message = null;

  public StackOverFlow ()
  {
    super(StackOverFlowHelper.id());
  } // ctor

  public StackOverFlow (String _message)
  {
    super(StackOverFlowHelper.id());
    message = _message;
  } // ctor


  public StackOverFlow (String $reason, String _message)
  {
    super(StackOverFlowHelper.id() + "  " + $reason);
    message = _message;
  } // ctor

} // class StackOverFlow
