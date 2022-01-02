package fr.algographe.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JMenu;
import javax.swing.SwingUtilities;

import java.io.File;

import fr.algographe.Controleur;

public class Fenetre
{
	private static final long serialVersionUID = 1L;
	
	private Controleur ctrl;

	private JFrame mainFrame;
	private JMenuBar menuBar;
	private JMenu menu;
	private JMenuItem itemOuvrirFichier;
	private JMenuItem itemEcrireFichier;
	private JMenuItem itemFermerApp;
	private JFileChooser fileChooser;

	private PanelConsole panelConsole;
	private PanelAction panelAction;
	private PanelDessin panelDessin;
	private JScrollPane scrollPane;

	public Fenetre(Controleur c)
	{
		createView(c);
		placeComponents();
		createControllers();
	}

	private void createView(Controleur c) {
		this.ctrl = c;
		final int width = 1200;
		final int height = 750;
		int 		widthScreen, heightScreen;
		Dimension 	dimScreen;
		dimScreen     = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
		heightScreen = (int) dimScreen.getHeight();
		widthScreen = (int) dimScreen.getWidth();
		double coeffWidth = (widthScreen / (double) width);
		double coeffHeight = (heightScreen / (double) height);
		int widthFrame = (int) (widthScreen / coeffWidth);
		int heightFrame = (int) (heightScreen / coeffHeight);
		mainFrame = new JFrame("Projet d’algorithmes sur les graphes : Les résaux sociaux");
		mainFrame.setSize(new Dimension(widthFrame, heightFrame));
		mainFrame.setLayout(new BorderLayout());
		menuBar = new JMenuBar();
		menu = new JMenu("Fichier");
		itemOuvrirFichier = new JMenuItem("Ouvrir un fichier");
		fileChooser = new JFileChooser();
		FileFilter filter = new FileFilter() {
			public boolean accept(File f) {
				if (f.isDirectory()) {
					return true;
				}
				else if (f.getName().endsWith(".txt")) {
					return true;
				}
				
				return false;
			}
			public String getDescription(){
				return "Fichier txt seulement";
			}
		};
		fileChooser.removeChoosableFileFilter(fileChooser.getAcceptAllFileFilter());
		fileChooser.setFileFilter(filter);
		itemEcrireFichier = new JMenuItem("Écrire le graphe");
		itemFermerApp = new JMenuItem("Fermer l'application");
		panelConsole = new PanelConsole();
		panelAction = new PanelAction(c);
		panelDessin = new PanelDessin(c);
		scrollPane = new JScrollPane(panelDessin);
	}

	private void placeComponents() {
		menu.add(itemOuvrirFichier);
		menu.add(itemEcrireFichier);
		menu.add(itemFermerApp);
		menuBar.add(menu);
		mainFrame.setJMenuBar(menuBar);
		mainFrame.add(panelConsole, BorderLayout.SOUTH);
		mainFrame.add(panelAction, BorderLayout.EAST);
		mainFrame.add(scrollPane, BorderLayout.CENTER);
	}

	private void createControllers() {
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		itemOuvrirFichier.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				fileChooser.setDialogTitle("Lecture du graphe à partir d'un fichier");
				int returnValue = fileChooser.showOpenDialog(mainFrame);
				if (returnValue == JFileChooser.APPROVE_OPTION) {
					File f = fileChooser.getSelectedFile();
					if (f.exists() && f.canRead()) {
						Fenetre.this.ctrl.refreshDessin();
						Fenetre.this.ctrl.lireFichier(f);
						Fenetre.this.ctrl.refreshDessin();
					} else {
						String msg = "Le fichier que vous avez selectionné (" + f.getName() + ") est incorrecte";
						String title = "Erreur d'ouverture d'un fichier";
						JOptionPane.showMessageDialog(mainFrame, msg, title, JOptionPane.ERROR_MESSAGE);
					}
				}
			}
		});
		itemEcrireFichier.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				fileChooser.setDialogTitle("Ecriture du graphe dans un fichier");
				int returnValue = fileChooser.showSaveDialog(mainFrame);
				if (returnValue == JFileChooser.APPROVE_OPTION) {
					File f = fileChooser.getSelectedFile();
					if (f.exists() && f.canRead() && f.canWrite()) {
						Fenetre.this.ctrl.ecrireFichier(f);
						JOptionPane.showMessageDialog(mainFrame, "Le graphe a été écrit dans le fichier " + f.getName(), "Ecriture du graphe", JOptionPane.INFORMATION_MESSAGE);
					} else {
						String msg = "Le fichier que vous avez selectionné (" + f.getName() + ") est incorrecte, veuillez choisir un fichier au format txt pour écrire dedans";
						String title = "Erreur d'ouverture d'un fichier";
						JOptionPane.showMessageDialog(mainFrame, msg, title, JOptionPane.ERROR_MESSAGE);
					}
				}
			}
		});
		itemFermerApp.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("L'application est maintenant fermé.");
				mainFrame.dispose();
			}
		});
	}

	public void display() {
		panelAction.setPreferredSize(new Dimension((int) (mainFrame.getWidth()*0.3), mainFrame.getHeight()));
		panelConsole.setPreferredSize(new Dimension(mainFrame.getWidth(), (int) (mainFrame.getHeight()*0.2)));
		panelDessin.setPreferredSize(new Dimension((int) (mainFrame.getWidth()*0.60), (int) (mainFrame.getHeight() * 0.8)));
		panelDessin.placeSommets();
		// Ne pas utiliser le gestionnaire de partition car le panelDessin a un layout à null
		//mainFrame.pack();
        mainFrame.setLocationRelativeTo(null);
		mainFrame.setVisible(true);
	}

	/*--------------------------------------------------------------*/
	/* 				MÉTHODES D'INTERACTION ENTRE LES PANELS 		*/
	/*--------------------------------------------------------------*/
	public void ajouterTexte(String txt) {
		this.panelConsole.ajouterTexte(txt);
	}

	public void refreshDessin() {
		this.panelDessin.refreshDessin();
	}

	
	public static void main(String[] args)
	{
		final Controleur c = new Controleur();
		final String[] arguments = args;
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				if (arguments.length != 0)
				{
					String className = getClass().getName();
					className = className.substring(0, className.indexOf('$'));
					System.out.println("Usage : java " + className);
				} else {
					Fenetre frame = new Fenetre(c);
					frame.display();
				}
			}
		});
	}
}