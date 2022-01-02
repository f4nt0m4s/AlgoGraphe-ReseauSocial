package fr.algographe.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;


public class PanelConsole extends JPanel {
	
	private static final long serialVersionUID = 1L;

	private JTextArea txtArea;

	public PanelConsole() {
		createView();
		placeComponents();
		display();
	}

	private void createView() {
		this.setLayout(new java.awt.BorderLayout(5, 5));
		this.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.BLACK) );
		txtArea = new JTextArea();
		txtArea.setFont(new Font("Arial", Font.PLAIN, 16));
		txtArea.setEditable(false);
	}

	private void placeComponents() {
		JLabel lbl = new JLabel("Affichage :");
		lbl.setFont(new Font("Verdana", Font.PLAIN | Font.BOLD, 14));
		this.add(lbl, BorderLayout.NORTH);
		this.add(new JScrollPane(txtArea), BorderLayout.CENTER);
	}

	private void display() {
		this.setVisible(true);
	}

	public void ajouterTexte(String txt) {
		this.txtArea.append(txt);
	}
	
	public void effacerTexte() {
		this.txtArea.selectAll();
		this.txtArea.replaceSelection("");
	}

}
