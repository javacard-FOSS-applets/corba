package fr.umlv.ir3.corba.calculator.client;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import fr.umlv.ir3.corba.calculator.CardException;
import fr.umlv.ir3.corba.calculator.InitializationException;
import fr.umlv.ir3.corba.calculator.InvalidOperator;
import fr.umlv.ir3.corba.calculator.StackOverFlow;


class CalcGUI{
	private static final long serialVersionUID = 1L;
	private CalcClient model; 
	private String number="";
	
	private JFrame window;
	private JTextField operationField;
	private JTextField resultField;
	private final Font BIGGER_FONT = new Font("monspaced", Font.PLAIN, 20);
	public CalcGUI(CalcClient model) {
		this.model = model;
    }
    
    public void start(){
		window = new JFrame();
		
		//Display fields
		operationField = new JTextField("", 12);
		operationField.setHorizontalAlignment(JTextField.RIGHT);
		operationField.setFont(BIGGER_FONT);
		operationField.setEnabled(false);
		resultField = new JTextField("0", 12);
		resultField.setHorizontalAlignment(JTextField.RIGHT);
		resultField.setFont(BIGGER_FONT);
		resultField.setEnabled(false);
		JPanel fieldPanel = new JPanel();
		fieldPanel.setLayout(new BorderLayout());
		fieldPanel.add(operationField, BorderLayout.NORTH);
		fieldPanel.add(resultField, BorderLayout.SOUTH);
		
		//Clear button
		JButton clearButton = new JButton("ENTER");
		clearButton.setFont(BIGGER_FONT);
		clearButton.addActionListener(new EnterListener());
		
		//Layout numeric keys in a grid.  Generate the buttons in a loop from the chars in a string.
		String buttonOrder = "789456123 0 ";
		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new GridLayout(5, 3, 5, 5));
		for (int i = 0; i < buttonOrder.length(); i++) {
			String keyTop = buttonOrder.substring(i, i+1);
			if (keyTop.equals(" ")) {
				buttonPanel.add(new JLabel(""));
			} else {
				JButton b = new JButton(keyTop);
				b.addActionListener(new NumListener());
				b.setFont(BIGGER_FONT);
				buttonPanel.add(b);
			}
		}
		
		
		//--- Create panel with gridlayout to hold operator buttons.
		//    Use array of button names to create buttons in a loop.
		JPanel opPanel = new JPanel();
		opPanel.setLayout(new GridLayout(5, 1, 5, 5));
		String[] opOrder = {"+", "-", "*", "/"};
		for (int i = 0; i < opOrder.length; i++) {
			JButton b = new JButton(opOrder[i]);
			//--- One ActionListener to use for all operator buttons.
			b.addActionListener(new OpListener());
			b.setFont(BIGGER_FONT);
			opPanel.add(b);
		}
		JButton b = new JButton("CLR");
		b.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				action_clear();
			}
		});
		b.setFont(BIGGER_FONT);
		opPanel.add(b);
		
		//--- Layout the top-level panel.
		JPanel content = new JPanel();
		content.setLayout(new BorderLayout(5, 5));
		content.add(fieldPanel, BorderLayout.NORTH );
		content.add(buttonPanel   , BorderLayout.CENTER);
		content.add(opPanel       , BorderLayout.EAST  );
		content.add(clearButton   , BorderLayout.SOUTH );
		
		//--- Finish building the window (JFrame)
		window.setContentPane(content);
		window.pack();
		window.setTitle("Calculator");
		window.setResizable(false);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setVisible(true);	
	}
	
	
	/** Called by Clear btn action listener and elsewhere.
	 * @throws CardException */
	private void action_clear(){
		try {
			model.clear();
			operationField.setText("");
			resultField.setText("0");
			number="";
		} catch (CardException e) {
			System.err.println("Card error: Card use error ("+ e.getMessage() + ")");
			JOptionPane.showMessageDialog(window, "Card use error", "Card error", JOptionPane.ERROR_MESSAGE);
		} catch (InitializationException e) {
            System.err.println("Card error: Card initialization error ("+ e.getMessage() + ")");
            JOptionPane.showMessageDialog(window, "Card initialization error", "Card error", JOptionPane.ERROR_MESSAGE);
        }
	}
	
	private class NumListener implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			//Get text from button
			String digit = e.getActionCommand(); 
			number += digit;
			operationField.setText(operationField.getText() + digit);
		}
	};
	private class OpListener implements ActionListener{
		public void actionPerformed(ActionEvent event) {
			char operator = event.getActionCommand().charAt(0);
			try {
				if (!number.equals("")) model.addOperand(Short.parseShort(number));
				resultField.setText("" + model.getResult(operator));
				operationField.setText(operationField.getText() + " " + operator + " " );
				number="";
				resultField.setText("10");
			} catch (InvalidOperator e) {
				System.err.println("Applet error: Invalid operator ("+ e.getMessage() + ")");
				JOptionPane.showMessageDialog(window, "Invalid operator", "Applet error", JOptionPane.ERROR_MESSAGE);
			} catch (StackOverFlow e) {
				System.err.println("Applet error: Stack overflow ("+ e.getMessage() + ")");				
				JOptionPane.showMessageDialog(window, "Stack overflow", "Applet error", JOptionPane.ERROR_MESSAGE);
			}catch (NumberFormatException e) {
				System.err.println("Internal error: Number format invalid ("+ e.getMessage() + ")");
				JOptionPane.showMessageDialog(window, "Number format invalid", "Internal error", JOptionPane.ERROR_MESSAGE);
            } catch (InitializationException e) {
                System.err.println("Card error: Card initialization error ("+ e.getMessage() + ")");
                JOptionPane.showMessageDialog(window, "Card initialization error", "Card error", JOptionPane.ERROR_MESSAGE);
            } catch (CardException e) {
				System.err.println("Card error: Card use error ("+ e.getMessage() + ")");
				JOptionPane.showMessageDialog(window, "Card use error", "Card error", JOptionPane.ERROR_MESSAGE);
			}
		}
	};
	private class EnterListener implements ActionListener{
		public void actionPerformed(ActionEvent event) {
			try {
				if (!number.equals("")){
					model.addOperand(Short.parseShort(number));
					operationField.setText(operationField.getText() + " ");
					number="";
				}
			} catch (StackOverFlow e) {
				System.err.println("Applet error: Stack overflow ("+ e.getMessage() + ")");
				JOptionPane.showMessageDialog(window, "Stack overflow", "Applet error", JOptionPane.ERROR_MESSAGE);
			} catch (NumberFormatException e) {
				System.err.println("Internal error: Number format invalid ("+ e.getMessage() + ")");
				JOptionPane.showMessageDialog(window, "Number format invalid", "Internal error", JOptionPane.ERROR_MESSAGE);
			} catch (CardException e) {
				System.err.println("Card error: Card use error ("+ e.getMessage() + ")");
				JOptionPane.showMessageDialog(window, "Card use error", "Card error", JOptionPane.ERROR_MESSAGE);
            } catch (InitializationException e) {
                System.err.println("Card error: Card initialization error ("+ e.getMessage() + ")");
                JOptionPane.showMessageDialog(window, "Card initialization error", "Card error", JOptionPane.ERROR_MESSAGE);
            }
		}
	}
}