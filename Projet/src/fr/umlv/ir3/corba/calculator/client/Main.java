package fr.umlv.ir3.corba.calculator.client;

import javax.swing.JOptionPane;

/**
 * This class is used to launch a graphical corba client
 * @author BARBISAN Laurent, BOITEL Olivier, GUILLON Denis, LAMPS S�bastien 
 */

class Main {
    
    /**
     * This method launchs a graphical corba client
     * @param args not used
     * @param pass the password of the current user
     * @return the user authenticated , null if no user in database
     * @throws HileException
     */
	public static void main(String[] args) {
		try {
            //initialize model
			CalcClient client = new CalcClient();
            //initialize view
			CalcGUI window = new CalcGUI(client);
			//launch graphical interface
            window.start();
		} catch (Exception e) {
		    //display any exceptions
			System.err.println("Calculator client error: " + e.getMessage());
			JOptionPane.showMessageDialog(null, e.getLocalizedMessage(), "Calculator client error", JOptionPane.ERROR_MESSAGE);
		}
	}
}
