package fr.umlv.ir3.corba.generator;

import javax.swing.JOptionPane;

/**
 * Main class
 * @author lbarbisan
 *
 */
public class Main {
	/**
	 * Main method
	 * @param args command line arguments
	 */
	public static void main(String[] args) {
		try {
			Generator generator = new Generator("fr.umlv.ir3.corba.calculator.AppletCalculatorOperations");
			
		} catch (Exception e) {
			System.err.println("Generator error: " + e.getMessage());
			JOptionPane.showMessageDialog(null, e.getMessage(), "Generator error", JOptionPane.ERROR_MESSAGE);
		}
	}
}
