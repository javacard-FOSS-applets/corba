package fr.umlv.ir3.corba.calculator.server;
/**
 * This class is used to launch a calculator corba server
 * @author BARBISAN Laurent, BOITEL Olivier, GUILLON Denis, LAMPS S�bastien 
 */

public class Main {
    
    /**
     * This method launchs a calculator corba server
     * @param args not used
     */
	public static void main(String[] args) {
		try {
		    //initialize server
			Server server = new Server("A00000000201");
			//launch server
            System.out.println("Server Running");
            server.start();
            //stop server
            server.stop();
            System.out.println("Server stopped");  
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("Erreur: " + e.getMessage());
		}
	}
}
