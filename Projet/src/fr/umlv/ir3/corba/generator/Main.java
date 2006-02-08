package fr.umlv.ir3.corba.generator;

import javax.swing.JOptionPane;

public class Main {
	public static void main(String[] args) {
		try {
			Generator generator = new Generator();
			GeneratorUI window = new GeneratorUI(generator);
		} catch (Exception e) {
			System.err.println("Generator error: " + e.getMessage());
			JOptionPane.showMessageDialog(null, e.getMessage(), "Generator error", JOptionPane.ERROR_MESSAGE);
		}
	}
}
