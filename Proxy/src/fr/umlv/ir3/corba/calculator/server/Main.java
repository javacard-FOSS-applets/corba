package fr.umlv.ir3.corba.calculator.server;


public class Main {
	public static void main(String[] args) {
		try {
			Server server = new Server();
		} catch (Exception e) {
			System.err.println("Erreur: " + e.getMessage());
		}
	}
}
