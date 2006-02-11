package fr.umlv.ir3.corba.calculator.server;
/**
 * This class is used to launch a calculator corba server
 * @author BARBISAN Laurent, BOITEL Olivier, GUILLON Denis, LAMPS S�bastien 
 */

public class Main {
    
    /**
     * This method launchs a calculator corba server
     * @param args not used
     * @param pass the password of the current user
     * @return the user authenticated , null if no user in database
     * @throws HileException
     */
	public static void main(String[] args) {
		try {
		    //initialize server
			Server server = new Server("A00000000101");
			//launch server
            System.out.println("Server Running");
            server.start();
            //stop server
            server.stop();
            System.out.println("Server stopped");  
		} catch (Exception e) {
			System.err.println("Erreur: " + e.getMessage());
		}
	}
}
