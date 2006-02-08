package fr.umlv.ir3.corba.calculator.client;

import javax.swing.JOptionPane;


class Main {
	public static void main(String[] args) {
		try {
			CalcModel model = new CalcModel();
			CalcGUI window = new CalcGUI(model);
			window.show();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e.getLocalizedMessage(), "Corba error", JOptionPane.ERROR_MESSAGE);
		}
	}
}
