package fr.umlv.ir3.corba.generator;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * Graphical interface for generator
 * @author lbarbisan
 *
 */
public class GeneratorUI {
	private static final long serialVersionUID = 1L;
	private final Generator generator;
	
	private Font BIGGER_FONT = new Font("monspaced", Font.PLAIN, 20);
	private JFrame window;
	private JTextField pathField;
	
	/**
	 * Create the user interface for spcecified generator
	 * @param generator generator to used
	 */
	public GeneratorUI(final Generator generator) {
		this.generator = generator;
		window = new JFrame();
		
		//Display fields
		pathField = new JTextField("", 12);
		pathField.setHorizontalAlignment(JTextField.LEFT);
		pathField.setFont(BIGGER_FONT);
		
		//Buttons
		JButton fileButton = new JButton("...");
		fileButton.setFont(BIGGER_FONT);
		fileButton.addActionListener(new FileChooserListener());
		JButton generateAppletButton = new JButton("Generate applet");
		generateAppletButton.setFont(BIGGER_FONT);
		generateAppletButton.addActionListener(new GenerateListener());
		
		JButton generateProxyButton = new JButton("Generate proxy");
		generateProxyButton.setFont(BIGGER_FONT);
		generateProxyButton.addActionListener(new GenerateListener());
		
		//Layout the top-level panel.
		JPanel content = new JPanel();
		content.setLayout(new BorderLayout(5, 5));
		content.add(pathField, BorderLayout.CENTER );
		content.add(fileButton, BorderLayout.EAST );
		content.add(generateAppletButton, BorderLayout.SOUTH );
		content.add(generateProxyButton, BorderLayout.SOUTH );
		
		//Finish building the window (JFrame)
		window.setContentPane(content);
		window.pack();
		window.setTitle("Generator");
		window.setResizable(false);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setVisible(true);		
	}
	
	private class GenerateListener implements ActionListener{
		public void actionPerformed(ActionEvent arg0) {
			try {
				generator.generateAppletStub();
			} catch (ClassNotFoundException e) {
				JOptionPane.showMessageDialog(window, "Class not found", "Generator error", JOptionPane.ERROR_MESSAGE);
			}
		}
	};
	
	private class FileChooserListener implements ActionListener{
		public void actionPerformed(ActionEvent arg0) {
			new JFileChooser().setVisible(true);
		}
	};
}
