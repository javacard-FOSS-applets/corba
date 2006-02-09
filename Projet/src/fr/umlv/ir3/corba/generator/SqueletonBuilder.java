package fr.umlv.ir3.corba.generator;


import java.lang.reflect.Method;
import java.util.ArrayList;



public class SqueletonBuilder {

	// liste des methodes de l'interface IDL
	private ArrayList<Method> interfaceMethods;
	// liste des numeros d'instructions correspondant aux methodes
	private byte [] instructionsNumber;
	
	public SqueletonBuilder(ArrayList<Method> methods){
		this.interfaceMethods = methods;
		this.instructionsNumber = new byte [this.interfaceMethods.size()];
	}
	
	private void generateInstructionNumber(){
		byte increment = 0x10;
		this.instructionsNumber[0] = increment;
		
		for (int i = 1; i < this.interfaceMethods.size(); i++) {
			increment += 0x10;
			this.instructionsNumber[i] = increment; 
		}
	}
	
	public static void main(String[] args) {
		//TODO: tester les increment d'instruction
	}
}
