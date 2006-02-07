package fr.umlv.ir3.corba.proxy.server;

import org.omg.CORBA.Context;
import org.omg.CORBA.ContextList;
import org.omg.CORBA.DomainManager;
import org.omg.CORBA.ExceptionList;
import org.omg.CORBA.NVList;
import org.omg.CORBA.NamedValue;
import org.omg.CORBA.Object;
import org.omg.CORBA.Policy;
import org.omg.CORBA.Request;
import org.omg.CORBA.SetOverrideType;

import fr.umlv.ir3.corba.calculator.AppletCalculator;
import fr.umlv.ir3.corba.calculator.InvalidOperator;
import fr.umlv.ir3.corba.calculator.StackOverFlow;

public class Server  implements AppletCalculator{

	public short result(char _operator) throws InvalidOperator {
		// TODO Auto-generated method stub
		return 0;
	}

	public void addNumber(short number) throws StackOverFlow {
		// TODO Auto-generated method stub
		
	}

	public void clear() {
		// TODO Auto-generated method stub
		
	}

	public boolean _is_a(String repositoryIdentifier) {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean _is_equivalent(Object other) {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean _non_existent() {
		// TODO Auto-generated method stub
		return false;
	}

	public int _hash(int maximum) {
		// TODO Auto-generated method stub
		return 0;
	}

	public Object _duplicate() {
		// TODO Auto-generated method stub
		return null;
	}

	public void _release() {
		// TODO Auto-generated method stub
		
	}

	public Object _get_interface_def() {
		// TODO Auto-generated method stub
		return null;
	}

	public Request _request(String operation) {
		// TODO Auto-generated method stub
		return null;
	}

	public Request _create_request(Context ctx, String operation, NVList arg_list, NamedValue result) {
		// TODO Auto-generated method stub
		return null;
	}

	public Request _create_request(Context ctx, String operation, NVList arg_list, NamedValue result, ExceptionList exclist, ContextList ctxlist) {
		// TODO Auto-generated method stub
		return null;
	}

	public Policy _get_policy(int policy_type) {
		// TODO Auto-generated method stub
		return null;
	}

	public DomainManager[] _get_domain_managers() {
		// TODO Auto-generated method stub
		return null;
	}

	public Object _set_policy_override(Policy[] policies, SetOverrideType set_add) {
		// TODO Auto-generated method stub
		return null;
	}

}
