package fr.umlv.ir3.corba.generator;

import java.lang.reflect.Method;

import javax.swing.JOptionPane;

public class Main {
	public static void main(String[] args) {
		try {
			Generator generator = new Generator("fr.umlv.ir3.corba.calculator.AppletCalculatorOperations");
			GeneratorUI generatorUI = new GeneratorUI(generator);
			Method[] m = generator.getOperationsMethod();
			for(int i=0;i<m.length;i++){
				System.out.println("affichage methode" + m[i]);
				System.out.println("Nom : " + m[i].getName() + 
						"/// Return type: " + m[i].getGenericReturnType().toString() + "    //// Paramètres : " + m[i].getGenericParameterTypes().toString() );
			}
			
			//GeneratorUI window = new GeneratorUI(generator);
		} catch (Exception e) {
			System.err.println("Generator error: " + e.getMessage());
			JOptionPane.showMessageDialog(null, e.getMessage(), "Generator error", JOptionPane.ERROR_MESSAGE);
		}
	}
}
