package fr.algographe.gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Dimension;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.JFrame;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;

import java.text.NumberFormat;
import javax.swing.text.NumberFormatter;
import javax.swing.JFormattedTextField;

import java.util.Iterator;
import java.util.ListIterator;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;

import fr.algographe.Controleur;
import fr.algographe.Page;
import fr.algographe.Sommet;
import fr.algographe.Utilisateur;


public class PanelAction extends JPanel {
	
	private static final long serialVersionUID = 1L;

	private Controleur ctrl;

	private JButton btnNbSommet;
	private JButton btnNbArc;
	private JButton btnEnsSommets;
	private JButton btnEnsSommetsTrieParNom;
	private JButton btnEnsSommetsTrieParDegreSortant;
	private JButton btnEnsArcs;
	private JButton btnAjoutSommetUtilisateur;
	private JButton btnAjoutSommetPage;
	private JButton btnSupprimerSommet;
	private JButton btnAjoutArc;
	private JButton btnSupprimerArc;
	private JButton btnNomSommet;
	private JButton btnNbComptesPage;
	private JButton btnNbComptesUtilisateur;
	private JButton btnAgeMoyenUtilisateur;
	private JButton btnCompteAdminDePage;
	private JButton btnPageRank;
	private JButton btnDistance;
	
	public PanelAction(Controleur c) {
		this.ctrl = c;
		createView();
		placeComponents();
		createControllers();
		display();
	}

	private void createView() {
		this.setLayout( new java.awt.GridLayout(18, 1, 5, 5) );
		this.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.BLACK));

		this.btnNbSommet = new JButton("Nombre de sommets");
		btnNbSommet.setToolTipText("Connaître le nombre de sommet(s)");

		this.btnNbArc = new JButton("Nombre d'arcs");
		btnNbArc.setToolTipText("Connaître le nombre d'arc(s)");

		this.btnEnsSommets = new JButton("Ensemble des sommets");
		btnEnsSommets.setToolTipText("Obtenir l’ensemble des sommet(s)");

		this.btnEnsSommetsTrieParNom = new JButton("Ensemble des sommets trié par nom");
		btnEnsSommetsTrieParNom.setToolTipText("Obtenir l'ensemble des sommets trié par nom");

		this.btnEnsSommetsTrieParDegreSortant = new JButton("Ensemble des sommets trié par degré sortant");
		btnEnsSommetsTrieParDegreSortant.setToolTipText("Obtenir l'ensemble des sommets trié par degré sortant");

		this.btnEnsArcs = new JButton("Ensemble d'arcs");
		btnEnsArcs.setToolTipText("Obtenir l'ensemble des arcs");

		this.btnAjoutSommetUtilisateur = new JButton("Ajouter un sommet utilisateur");
		btnAjoutSommetUtilisateur.setToolTipText("Ajouter un sommet de type utilisateur");

		this.btnAjoutSommetPage = new JButton("Ajouter un sommet page");
		btnAjoutSommetPage.setToolTipText("Ajouter un sommet de type page");

		this.btnSupprimerSommet = new JButton("Supprimer un sommet");
		btnSupprimerSommet.setToolTipText("Supprimer un sommet");

		this.btnAjoutArc = new JButton("Ajouter un arc");
		btnAjoutArc.setToolTipText("Ajouter un arc");

		this.btnSupprimerArc = new JButton("Supprimer un arc");
		btnSupprimerArc.setToolTipText("Supprimer un arc");

		this.btnNomSommet = new JButton("Sommet via son nom");
		btnNomSommet.setToolTipText("Obtenir les informations sur un sommet via son nom");

		this.btnNbComptesPage = new JButton("Nombre de comptes type Page");
		btnNbComptesPage.setToolTipText("Connaître le nombre de comptes de type Page");

		this.btnNbComptesUtilisateur = new JButton("Nombre de comptes type Utilisateur");
		btnNbComptesUtilisateur.setToolTipText("Connaître le nombre de comptes de type Utilisateur");

		this.btnAgeMoyenUtilisateur = new JButton("Âge moyen des utilisateurs");
		btnAgeMoyenUtilisateur.setToolTipText("Connaître l'âge moyen des comptes de type Utilisateur");

		this.btnCompteAdminDePage = new JButton("Comptes Utilisateur administrateurs de Page");
		btnCompteAdminDePage.setToolTipText("Connaître tous les comptes Utilisateur qui sont des administrateurs de Page");

		this.btnPageRank = new JButton("Calcul du page rank");

		this.btnDistance = new JButton("Calcul de la plus courte distance");
		this.setToolTipText("Calcul de la plus courte distance entre un sommet source et l'ensemble de sommets");
	}

	private void placeComponents() {
		this.add(this.btnNbSommet);
		this.add(this.btnNbArc);
		this.add(this.btnEnsSommets);
		this.add(this.btnEnsSommetsTrieParNom);
		this.add(this.btnEnsSommetsTrieParDegreSortant);
		this.add(this.btnEnsArcs);
		this.add(this.btnAjoutSommetUtilisateur);
		this.add(this.btnAjoutSommetPage);
		this.add(this.btnSupprimerSommet);
		this.add(this.btnAjoutArc);
		this.add(this.btnSupprimerArc);
		this.add(this.btnNomSommet);
		this.add(this.btnNbComptesPage);
		this.add(this.btnNbComptesUtilisateur);
		this.add(this.btnAgeMoyenUtilisateur);
		this.add(this.btnCompteAdminDePage);
		this.add(this.btnPageRank);
		this.add(this.btnDistance);
	}

	private void createControllers() {
		this.btnNbSommet.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				PanelAction.this.ctrl.ajouterTexte(btnNbSommet.getText() + " : " + PanelAction.this.ctrl.getNbSommets() + "\n");
			}
		});
		this.btnNbArc.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				PanelAction.this.ctrl.ajouterTexte(btnNbArc.getText() + " : " + PanelAction.this.ctrl.getNbArcs() + "\n");
			}
		});
		this.btnEnsSommets.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String s = btnEnsSommets.getText() + " : ";
				ListIterator<Sommet> it = PanelAction.this.ctrl.getLstSommets().listIterator();
				while (it.hasNext()) {
					s += it.next().getNom() + " ";
				}
				s += "\n";
				PanelAction.this.ctrl.ajouterTexte(s);
			}
		});
		this.btnEnsSommetsTrieParNom.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String s = btnEnsSommetsTrieParNom.getText() + " : ";
				ListIterator<Sommet> it = PanelAction.this.ctrl.getLstSommetsTrieParNom().listIterator();
				while (it.hasNext()) {
					s += it.next().getNom();
					if (it.hasNext()) {
						s += " - ";
					}
				}
				s += "\n";
				PanelAction.this.ctrl.ajouterTexte(s);
			}
		});
		this.btnEnsSommetsTrieParDegreSortant.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String s = btnEnsSommetsTrieParDegreSortant.getText() + " : ";
				ListIterator<Sommet> it = PanelAction.this.ctrl.getLstSommetTrieParDegreSortant().listIterator();
				Sommet sommetTrie = null;
				while (it.hasNext()) { 
					sommetTrie = it.next();
					s += sommetTrie.getNom() + "(";
					s += (sommetTrie instanceof Utilisateur) ? ((Utilisateur) sommetTrie).getLstVoisins().size() : (sommetTrie instanceof Page) ? ((Page) sommetTrie).getLstAdmins().size() : 0;
					s += ") ";
					if (it.hasNext()) {
						s += " - ";
					}
				}
				s += "\n";
				PanelAction.this.ctrl.ajouterTexte(s);
			}
		});
		this.btnEnsArcs.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String ensArcs = "";
				for (Sommet s : PanelAction.this.ctrl.getLstSommets()) {
					List<? extends Sommet> lst = (s instanceof Utilisateur) ? ((Utilisateur) s).getLstVoisins() : (s instanceof Page) ? ((Page) s).getLstAdmins() : new ArrayList<Sommet>();
					for (Sommet dest : lst) {
						ensArcs += s.getNom() + "->" + dest.getNom() + "\n";
					}
				}
				PanelAction.this.ctrl.ajouterTexte(ensArcs);
			}
		});
		this.btnAjoutSommetUtilisateur.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				PanelAction.this.ajoutSommetUtilisateurJOptionPane("", "", 0, new ArrayList<Utilisateur>(), new ArrayList<Page>());
			}
		});
		this.btnAjoutSommetPage.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				PanelAction.this.ajoutSommetPageJOptionPane("", new ArrayList<Utilisateur>());
			}
		});
		this.btnSupprimerSommet.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				PanelAction.this.supprimerSommetJOptionPane();	
			}
		});
		this.btnAjoutArc.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				PanelAction.this.ajouterArc(null, null);
			}
		});
		this.btnSupprimerArc.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				PanelAction.this.supprimerArc(null, null);
			}
		});
		this.btnNomSommet.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				PanelAction.this.chercherSommetNom();
			}
		});
		this.btnNbComptesPage.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int nbComptePage = PanelAction.this.ctrl.getNbPage();
				String msg = "Le nombre de compte" + ((nbComptePage > 0) ? "s" : "") + " de type page est de " + nbComptePage + "\n";
				PanelAction.this.ctrl.ajouterTexte(msg);
			}
		});
		this.btnNbComptesUtilisateur.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int nbComptePage = PanelAction.this.ctrl.getNbUtilisateur();
				String msg = "Le nombre de compte" + ((nbComptePage > 0) ? "s" : "") + " de type utilisateur est de " + nbComptePage + "\n";
				PanelAction.this.ctrl.ajouterTexte(msg);
			}
		});
		this.btnAgeMoyenUtilisateur.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int btnAgeMoyenUtilisateur = PanelAction.this.ctrl.getAgeMoyen();
				String msg = "L'age moyen d'utilisateur est de " + btnAgeMoyenUtilisateur + " an" + ((btnAgeMoyenUtilisateur > 1) ? "s" : "") + "\n";
				PanelAction.this.ctrl.ajouterTexte(msg);
			}
		});
		this.btnCompteAdminDePage.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				List<Utilisateur> lstUtilisateur = PanelAction.this.ctrl.getUtilisateurAdministrateur();
				String msg = "";
				for (Utilisateur u : lstUtilisateur) {
					msg += u.getNom() + " administre la page ";
					int i = 0;
					for (Iterator<Page> iter = PanelAction.this.ctrl.getLstPages().iterator(); iter.hasNext();) {
						Page p = iter.next();
						if (p.getLstAdmins().contains(u)) {
							msg += p.getNom() + " ";
							i++;
						}
					}
					if (i > 1) {
						msg = msg.replace("administre la page", "administrent les pages");
					}
					msg += "\n";
				}
				PanelAction.this.ctrl.ajouterTexte(msg);
			}
		});
		this.btnPageRank.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				PanelAction.this.ctrl.pageRank();
				Object[] cols = {
					"Type", "Nom", "Page Rank"
				};
				Object[][] rows = new Object[PanelAction.this.ctrl.getLstSommetTrieParPageRank().size()][3];
				int i = 0;
				for (Sommet s : PanelAction.this.ctrl.getLstSommetTrieParPageRank()) {
					rows[i][0] = s instanceof Utilisateur ? "Utilisateur" : s instanceof Page ? "Page" : "Sommet";
					rows[i][1] = s.getNom();
					rows[i][2] = s.getPageRank();
					i++;
				}
				JTable table = new JTable(rows, cols);
				table.setEnabled(false);
				JOptionPane.showMessageDialog(PanelAction.this.getParent(), new JScrollPane(table), "Page rank", JOptionPane.INFORMATION_MESSAGE);
			}
		});
		this.btnDistance.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				PanelAction.this.plusCourtDistanceJOptionPane(null);
			}
		});
	}

	private void display() {
		this.setVisible(true);
	}
	
	private String nom;
	private String prenom;
	private int age;
	private JTextField txtFldNomSommet;
	private JTextField txtFldPrenomSommet;
	private JFormattedTextField txtFldAge;
	private void ajoutSommetUtilisateurJOptionPane(String ancienNom, String ancienPrenom, int ancienAge, List<Utilisateur> lstVoisinsUtilisateur, List<Page> lstVoisinsPage) {
		txtFldNomSommet = null;
		txtFldPrenomSommet = null;
		txtFldAge = null;
		JCheckBox[] chBoxVoisinsUtilisateur = null;
		JCheckBox[] chBoxVoisinsPage = null;

		this.nom = (ancienNom == null) ? "" : ancienNom;
		this.prenom = (ancienPrenom == null) ? "" : ancienPrenom;
		this.age = (ancienAge <= 0) ? 0 : ancienAge;

		lstVoisinsUtilisateur = (lstVoisinsUtilisateur == null) ? new ArrayList<Utilisateur>() : lstVoisinsUtilisateur;
		lstVoisinsPage = (lstVoisinsPage == null) ? new ArrayList<Page>() : lstVoisinsPage;
		
		JPanel p = new JPanel(new GridLayout(5, 1)); {
			JPanel r = new JPanel(new FlowLayout(FlowLayout.LEFT)); {
				r.add(new JLabel("Nom :"));
				PanelAction.this.txtFldNomSommet = new JTextField(10);
				PanelAction.this.txtFldNomSommet.setText(ancienNom);
				PanelAction.this.txtFldNomSommet.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						PanelAction.this.nom = PanelAction.this.txtFldNomSommet.getText();
					}
				});
				PanelAction.this.txtFldNomSommet.addKeyListener(new KeyAdapter() {
					@Override
					public void keyReleased(KeyEvent e) {
						JTextField txtFld = (JTextField) e.getSource();
						PanelAction.this.nom = txtFld.getText();
					}
					@Override
					public void keyTyped(KeyEvent e) {}
					@Override
					public void keyPressed(KeyEvent e) {}
				});
				r.add(PanelAction.this.txtFldNomSommet);
			}
			p.add(r);
			r = new JPanel(new FlowLayout(FlowLayout.LEFT)); {
				r.add(new JLabel("Prénom :"));
				PanelAction.this.txtFldPrenomSommet = new JTextField(10);
				PanelAction.this.txtFldPrenomSommet.setText(prenom);
				PanelAction.this.txtFldPrenomSommet.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						PanelAction.this.prenom = PanelAction.this.txtFldPrenomSommet.getText();
					}
				});
				PanelAction.this.txtFldPrenomSommet.addKeyListener(new KeyAdapter() {
					@Override
					public void keyReleased(KeyEvent e) {
						JTextField txtFld = (JTextField) e.getSource();
						PanelAction.this.prenom = txtFld.getText();
					}
					@Override
					public void keyTyped(KeyEvent e) {}
					@Override
					public void keyPressed(KeyEvent e) {}
				});
				r.add(PanelAction.this.txtFldPrenomSommet);
			}
			p.add(r);
			r = new JPanel(new FlowLayout(FlowLayout.LEFT)); {
				r.add(new JLabel("Age :"));
				NumberFormat format = NumberFormat.getInstance();
				NumberFormatter formatter = new NumberFormatter(format);
				formatter.setValueClass(Integer.class);
				formatter.setMinimum(0);
				formatter.setMaximum(1000);
				formatter.setAllowsInvalid(false);
				formatter.setCommitsOnValidEdit(true);
				PanelAction.this.txtFldAge = new JFormattedTextField(formatter);
				PanelAction.this.txtFldAge.setColumns(10);
				PanelAction.this.txtFldAge.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						if (PanelAction.this.txtFldAge.getText().matches("\\d+")) {
							PanelAction.this.age = Integer.parseInt(PanelAction.this.txtFldAge.getText());
						}
					}
				});
				PanelAction.this.txtFldAge.addKeyListener(new KeyAdapter() {
					@Override
					public void keyReleased(KeyEvent e) {
						JTextField txtFld = (JTextField) e.getSource();
						if (txtFld.getText().matches("\\d+")) {
							PanelAction.this.age = Integer.parseInt(txtFld.getText());
						}
					}
					@Override
					public void keyTyped(KeyEvent e) {}
					@Override
					public void keyPressed(KeyEvent e) {}
				});
				r.add(PanelAction.this.txtFldAge);
			}
			p.add(r);
			r = new JPanel(new  FlowLayout(FlowLayout.LEFT)); {
				r.add(new JLabel("Liste d'utilisateur à suivre : "));
				chBoxVoisinsUtilisateur = new JCheckBox[PanelAction.this.ctrl.getLstUtilisateurs().size()];
				for (int i = 0; i < chBoxVoisinsUtilisateur.length; i++) {
					chBoxVoisinsUtilisateur[i] = new JCheckBox(PanelAction.this.ctrl.getLstUtilisateurs().get(i).getNom());
					r.add(chBoxVoisinsUtilisateur[i]);
					if (lstVoisinsUtilisateur != null & lstVoisinsUtilisateur.size() > 0) {
						for (Sommet voisin : lstVoisinsUtilisateur) {
							if (voisin.getNom().equals(PanelAction.this.ctrl.getLstUtilisateurs().get(i).getNom())) {
								chBoxVoisinsUtilisateur[i].setSelected(true);
							}
						}
					}
				}
			}
			r.setPreferredSize(new Dimension(400, 80));
			JScrollPane sp = new JScrollPane(r);
			p.add(sp);
			r = new JPanel(new  FlowLayout(FlowLayout.LEFT)); {
				r.add(new JLabel("Liste de pages à aimer : "));
				chBoxVoisinsPage = new JCheckBox[PanelAction.this.ctrl.getLstPages().size()];
				for (int i = 0; i < chBoxVoisinsPage.length; i++) {
					chBoxVoisinsPage[i] = new JCheckBox(PanelAction.this.ctrl.getLstPages().get(i).getNom());
					r.add(chBoxVoisinsPage[i]);
					if (lstVoisinsPage != null & lstVoisinsPage.size() > 0) {
						for (Sommet voisin : lstVoisinsPage) {
							if (voisin.getNom().equals(PanelAction.this.ctrl.getLstPages().get(i).getNom())) {
								chBoxVoisinsPage[i].setSelected(true);
							}
						}
					}
				}
			}
			r.setPreferredSize(new Dimension(400, 80));
			sp = new JScrollPane(r);
			p.add(sp);
			p.setPreferredSize(new Dimension(400, 400));
		}

		JFrame mainFrame = (JFrame) SwingUtilities.getWindowAncestor(PanelAction.this);
		int returnValue = JOptionPane.showConfirmDialog(mainFrame, p, "Ajout d'un sommet utilisateur", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
		if (returnValue == JOptionPane.YES_OPTION) {
			boolean isError = false;
			if (txtFldNomSommet.getText() != null && txtFldNomSommet.getText().length() > 0) {
				nom = txtFldNomSommet.getText();
			} else {
				isError = true;
				JOptionPane.showMessageDialog(mainFrame, "Le champ nom ne peut pas être vide", "Erreur champ du texte nom", JOptionPane.ERROR_MESSAGE);
			}

			Sommet sDuplication = PanelAction.this.ctrl.getSommet(nom);
			if (sDuplication != null) {
				isError = true;
				JOptionPane.showMessageDialog(mainFrame, "Le sommet " + nom + " existe déja", "Erreur duplication de sommet", JOptionPane.ERROR_MESSAGE);
			}
			
			lstVoisinsUtilisateur.clear();
			for (int i = 0; i < chBoxVoisinsUtilisateur.length; i++) {
				if (chBoxVoisinsUtilisateur[i].isSelected()) {
					Sommet u = PanelAction.this.ctrl.getSommet(chBoxVoisinsUtilisateur[i].getText());
					u = (u instanceof Utilisateur) ? u : null;
					if (u != null) {
						lstVoisinsUtilisateur.add(PanelAction.this.ctrl.getLstUtilisateurs().get(i));
					} else {
						isError = true;
						String msg = "Le sommet utilisateur " + chBoxVoisinsUtilisateur[i].getText() + " n'a pas été trouvé ";
						JOptionPane.showMessageDialog(mainFrame, msg, "Erreur voisin utilisateur", JOptionPane.ERROR_MESSAGE);
					}
				}
			}

			lstVoisinsPage.clear();
			for (int i = 0; i < chBoxVoisinsPage.length; i++) {
				if (chBoxVoisinsPage[i].isSelected()) {
					Sommet u = PanelAction.this.ctrl.getSommet(chBoxVoisinsPage[i].getText());
					u = (u instanceof Page) ? u : null;
					if (u != null) {
						lstVoisinsPage.add(PanelAction.this.ctrl.getLstPages().get(i));
					} else {
						isError = true;
						String msg = "Le sommet page " + chBoxVoisinsPage[i].getText() + " n'a pas été trouvé ";
						JOptionPane.showMessageDialog(mainFrame, msg, "Erreur voisin utilisateur", JOptionPane.ERROR_MESSAGE);
					}
				}
			}

			if (!isError) {
				Sommet s = new Utilisateur(nom, prenom, age);
				for (Sommet utilsateur : lstVoisinsUtilisateur) {
					s.addVoisin(utilsateur);
				}
				for (Sommet page : lstVoisinsPage) {
					s.addVoisin(page);
				}
				PanelAction.this.ctrl.ajouterSommet(s);
				PanelAction.this.ctrl.refreshDessin();
				PanelAction.this.ctrl.ajouterTexte("Le sommet " + s.getNom() + " a été ajouté au graphe\n");

			} else {
				// Réaffichage de la JOptionPane en cas d'erreur
				ajoutSommetUtilisateurJOptionPane(nom, prenom, age, lstVoisinsUtilisateur, lstVoisinsPage);
			}
		}
	}

	private void ajoutSommetPageJOptionPane(String ancienNom, List<Utilisateur> lstAdmins) {
		txtFldNomSommet = null;
		JCheckBox[] chBoxAdmins = null;

		this.nom = (ancienNom == null) ? "" : ancienNom;
		lstAdmins = (lstAdmins == null) ? new ArrayList<Utilisateur>() : lstAdmins;

		JPanel p = new JPanel(new GridLayout(2, 1)); {
			JPanel r = new JPanel(new FlowLayout(FlowLayout.LEFT)); {
				r.add(new JLabel("Nom :"));
				PanelAction.this.txtFldNomSommet = new JTextField(10);
				PanelAction.this.txtFldNomSommet.setText(ancienNom);
				PanelAction.this.txtFldNomSommet.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						PanelAction.this.nom = PanelAction.this.txtFldNomSommet.getText();
					}
				});
				PanelAction.this.txtFldNomSommet.addKeyListener(new KeyAdapter() {
					@Override
					public void keyReleased(KeyEvent e) {
						JTextField txtFld = (JTextField) e.getSource();
						PanelAction.this.nom = txtFld.getText();
					}
					@Override
					public void keyTyped(KeyEvent e) {}
					@Override
					public void keyPressed(KeyEvent e) {}
				});
				r.add(PanelAction.this.txtFldNomSommet);
			}
			p.add(r);
			r = new JPanel(new  FlowLayout(FlowLayout.LEFT)); {
				r.add(new JLabel("Liste d'administrateur(s) : "));
				chBoxAdmins = new JCheckBox[PanelAction.this.ctrl.getLstUtilisateurs().size()];
				for (int i = 0; i < chBoxAdmins.length; i++) {
					chBoxAdmins[i] = new JCheckBox(PanelAction.this.ctrl.getLstUtilisateurs().get(i).getNom());
					r.add(chBoxAdmins[i]);
					if (lstAdmins!= null & lstAdmins.size() > 0) {
						for (Sommet voisin : lstAdmins) {
							if (voisin.getNom().equals(PanelAction.this.ctrl.getLstUtilisateurs().get(i).getNom())) {
								chBoxAdmins[i].setSelected(true);
							}
						}
					}
				}
			}
			r.setPreferredSize(new Dimension(400, 80));
			JScrollPane sp = new JScrollPane(r);
			p.add(sp);
		}

		JFrame mainFrame = (JFrame) SwingUtilities.getWindowAncestor(PanelAction.this);
		int returnValue = JOptionPane.showConfirmDialog(mainFrame, p, "Ajout d'un sommet page", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
		if (returnValue == JOptionPane.YES_OPTION) {
			boolean isError = false;
			if (txtFldNomSommet.getText() != null && txtFldNomSommet.getText().length() > 0) {
				nom = txtFldNomSommet.getText();
			} else {
				isError = true;
				JOptionPane.showMessageDialog(mainFrame, "Le champ nom ne peut pas être vide", "Erreur champ du texte nom", JOptionPane.ERROR_MESSAGE);
			}

			if (PanelAction.this.ctrl.getSommet(nom) != null) {
				isError = true;
				JOptionPane.showMessageDialog(mainFrame, "Le sommet " + nom + " existe déja", "Erreur duplication de sommet", JOptionPane.ERROR_MESSAGE);
			}

			lstAdmins.clear();
			for (int i = 0; i < chBoxAdmins.length; i++) {
				if (chBoxAdmins[i].isSelected()) {
					Sommet u = PanelAction.this.ctrl.getSommet(chBoxAdmins[i].getText());
					u = (u instanceof Utilisateur) ? u : null;
					if (u != null) {
						lstAdmins.add(PanelAction.this.ctrl.getLstUtilisateurs().get(i));
					} else {
						isError = true;
						String msg = "Le sommet utilisateur " + chBoxAdmins[i].getText() + " n'a pas été trouvé ";
						JOptionPane.showMessageDialog(mainFrame, msg, "Erreur voisin utilisateur", JOptionPane.ERROR_MESSAGE);
					}
				}
			}

			if (!isError) {
				Sommet s = new Page(nom);
				for (Utilisateur utilsateur : lstAdmins) {
					((Page) s).addAdmin(utilsateur);
				}
				PanelAction.this.ctrl.ajouterSommet(s);
				PanelAction.this.ctrl.refreshDessin();
				PanelAction.this.ctrl.ajouterTexte("Le sommet " + s.getNom() + " a été ajouté au graphe");

			} else {
				// Réaffichage de la JOptionPane en cas d'erreur
				ajoutSommetPageJOptionPane(nom, lstAdmins);
			}
		}
	}

	private JComboBox cmbSupprimerSommet;
	private void supprimerSommetJOptionPane() {
		JPanel p = new JPanel(new GridLayout(2, 1)); {
			p.add(new JLabel("Séléctionner le sommet à supprimer :"));
			cmbSupprimerSommet = new JComboBox();
			for (Sommet s : PanelAction.this.ctrl.getLstSommets()) {
				cmbSupprimerSommet.addItem(s);
			}
			cmbSupprimerSommet.setSelectedIndex(-1);
			p.add(cmbSupprimerSommet);
		}
		JFrame mainFrame = (JFrame) SwingUtilities.getWindowAncestor(PanelAction.this);
		int returnValue = JOptionPane.showConfirmDialog(mainFrame, p, "Ajout d'un sommet page", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
		if (returnValue == JOptionPane.YES_OPTION) {
			Sommet s = (Sommet) cmbSupprimerSommet.getSelectedItem();
			if (cmbSupprimerSommet != null && s != null) {
				PanelAction.this.ctrl.supprimerSommet(s);
				PanelAction.this.ctrl.refreshDessin();
			}
		}
	}

	private JComboBox cmbArcSrc;
	private JComboBox cmbArcDest;
	private void ajouterArc(JComboBox cmbSrc, JComboBox cmbDest) {
		JPanel p = new JPanel(new GridLayout(4, 1)); {
			if (cmbSrc == null || cmbSrc.getItemCount() == 0) {
				cmbArcSrc = new JComboBox();
				for (Sommet s : PanelAction.this.ctrl.getLstSommets()) {
					this.cmbArcSrc.addItem(s);
				}
				this.cmbArcSrc.setSelectedIndex(-1);
			}
			if (cmbDest == null || cmbDest.getItemCount() == 0) {
				cmbArcDest = new JComboBox();
				for (Sommet s : PanelAction.this.ctrl.getLstSommets()) {
					this.cmbArcDest.addItem(s);
				}
				this.cmbArcDest.setSelectedIndex(-1);
			}

			this.cmbArcSrc.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					Sommet sSrc = (Sommet) cmbArcSrc.getSelectedItem();
					Sommet sDest = (Sommet) cmbArcDest.getSelectedItem();
					if (sSrc != null) {
						cmbArcDest.removeAllItems();
						for (Sommet s : PanelAction.this.ctrl.lstSommetSansArc(sSrc)) {
							if (!sSrc.equals(s)) {
								cmbArcDest.addItem(s);
							}
						}
						cmbArcDest.setSelectedIndex(-1);
						if (sDest == null) {
							if (sSrc instanceof Utilisateur) {
								cmbArcDest.removeAllItems();
								for (Sommet s : PanelAction.this.ctrl.lstSommetSansArc(sSrc)) {
									if (!sSrc.equals(s)) {
										cmbArcDest.addItem(s);
									}
								}
								cmbArcDest.setSelectedIndex(-1);
							} else if (sSrc instanceof Page) {
								cmbArcDest.removeAllItems();
								for (Sommet s : PanelAction.this.ctrl.lstSommetSansArc(sSrc)) {
									cmbArcDest.addItem(s);
								}
								cmbArcDest.setSelectedIndex(-1);
							}
						} else {
							if (sSrc instanceof Page && sDest instanceof Page) {
								cmbArcDest.removeAllItems();
								for (Sommet s : PanelAction.this.ctrl.lstSommetSansArc(sSrc)) {
									if (!sSrc.equals(s)) {
										cmbArcDest.addItem(s);
									}
								}
								cmbArcDest.setSelectedIndex(-1);
								JOptionPane.showMessageDialog(null, "Le sommet source étant une page, il ne peut avoir que une liste d'administrateurs", "Ajout d'arc", JOptionPane.INFORMATION_MESSAGE);
							} else if (sSrc.equals(sDest)) {
								JOptionPane.showMessageDialog(null, "Le sommet source ne peut pas s'ajouter lui même un arc", "Ajout d'arc", JOptionPane.ERROR_MESSAGE);
							}
						}
					}
				}
			});
			p.add(new JLabel("Séléctionner le sommet source :"));
			p.add(cmbArcSrc);
			p.add(new JLabel("Séléctionner le sommet de destination :"));
			p.add(cmbArcDest);
		}


		JFrame mainFrame = (JFrame) SwingUtilities.getWindowAncestor(PanelAction.this);
		int returnValue = JOptionPane.showConfirmDialog(mainFrame, p, "Ajout d'un arc utilisateur", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
		if (returnValue == JOptionPane.YES_OPTION) {
			Sommet sSrc = (Sommet) cmbArcSrc.getSelectedItem();
			Sommet sDest = (Sommet) cmbArcDest.getSelectedItem();
			if (sSrc != null && sDest != null) {
				PanelAction.this.ctrl.ajouterArc(sSrc, sDest);
				PanelAction.this.ctrl.refreshDessin();
				PanelAction.this.ctrl.ajouterTexte("L'arc allant de " + sSrc.getNom() + " à " + sDest.getNom() + " a été ajouté\n");
			} else {
				String msg = "Le sommet " + ((sSrc == null) ? "source" : "destination") + " n 'est pas séléctionné";
				JOptionPane.showMessageDialog(mainFrame, msg, "Erreur de sélection", JOptionPane.ERROR_MESSAGE);
				this.ajouterArc(this.cmbArcSrc, this.cmbArcDest);
			}
		}
	}

	private void supprimerArc(JComboBox cmbSrc, JComboBox cmbDest) {
		JPanel p = new JPanel(new GridLayout(4, 1)); {
			if (cmbSrc == null || cmbSrc.getItemCount() == 0) {
				cmbArcSrc = new JComboBox();
				for (Sommet s : PanelAction.this.ctrl.getLstSommets()) {
					this.cmbArcSrc.addItem(s);
				}
				this.cmbArcSrc.setSelectedIndex(-1);
			}
			if (cmbDest == null || cmbDest.getItemCount() == 0) {
				cmbArcDest = new JComboBox();
				for (Sommet s : PanelAction.this.ctrl.getLstSommets()) {
					this.cmbArcDest.addItem(s);
				}
				this.cmbArcDest.setSelectedIndex(-1);
			}

			this.cmbArcSrc.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					Sommet sSrc = (Sommet) cmbArcSrc.getSelectedItem();
					Sommet sDest = (Sommet) cmbArcDest.getSelectedItem();
					if (sSrc != null) {
						cmbArcDest.removeAllItems();
						List<? extends Sommet> lstSommet = PanelAction.this.ctrl.getSommet(sSrc.getNom()) instanceof Utilisateur ? PanelAction.this.ctrl.getSommet(sSrc.getNom()).getLstVoisins() 
						: PanelAction.this.ctrl.getSommet(sSrc.getNom()) instanceof Page ? ((Page) PanelAction.this.ctrl.getSommet(sSrc.getNom())).getLstAdmins() : new ArrayList<Sommet>();
						for (Sommet s : lstSommet) {
							if (!sSrc.equals(s)) {
								cmbArcDest.addItem(s);
							}
						}
						cmbArcDest.setSelectedIndex(-1);
						if (sDest == null) {
							if (sSrc instanceof Utilisateur) {
								cmbArcDest.removeAllItems();
								for (Sommet s : ((Utilisateur) PanelAction.this.ctrl.getSommet(sSrc.getNom())).getLstVoisins()) {
									if (!sSrc.equals(s)) {
										cmbArcDest.addItem(s);
									}
								}
								cmbArcDest.setSelectedIndex(-1);
							} else if (sSrc instanceof Page) {
								cmbArcDest.removeAllItems();
								for (Sommet s : ((Page) PanelAction.this.ctrl.getSommet(sSrc.getNom())).getLstAdmins()) {
									cmbArcDest.addItem(s);
								}
								cmbArcDest.setSelectedIndex(-1);
							}
						} else {
							if (sSrc instanceof Page && sDest instanceof Page) {
								cmbArcDest.removeAllItems();
								for (Sommet s : ((Page) PanelAction.this.ctrl.getSommet(sSrc.getNom())).getLstAdmins()) {
									if (!sSrc.equals(s)) {
										cmbArcDest.addItem(s);
									}
								}
								cmbArcDest.setSelectedIndex(-1);
								JOptionPane.showMessageDialog(null, "Le sommet source étant une page, il ne peut que supprimer une liste d'administrateurs", "Suppresion d'arc", JOptionPane.INFORMATION_MESSAGE);
							} else if (sSrc.equals(sDest)) {
								JOptionPane.showMessageDialog(null, "Le sommet source ne peut pas supprimer lui même son arc", "Suppresion d'arc", JOptionPane.ERROR_MESSAGE);
							}
						}
					}
				}
			});
			p.add(new JLabel("Séléctionner le sommet source :"));
			p.add(cmbArcSrc);
			p.add(new JLabel("Séléctionner le sommet de destination :"));
			p.add(cmbArcDest);
		}

		JFrame mainFrame = (JFrame) SwingUtilities.getWindowAncestor(PanelAction.this);
		int returnValue = JOptionPane.showConfirmDialog(mainFrame, p, "Suppresion d'un arc", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
		if (returnValue == JOptionPane.YES_OPTION) {
			Sommet sSrc = (Sommet) cmbArcSrc.getSelectedItem();
			Sommet sDest = (Sommet) cmbArcDest.getSelectedItem();
			if (sSrc != null && sDest != null) {
				PanelAction.this.ctrl.supprimerArc(sSrc, sDest);
				PanelAction.this.ctrl.refreshDessin();
				PanelAction.this.ctrl.ajouterTexte("L'arc allant de " + sSrc.getNom() + " à " + sDest.getNom() + " a été supprimé\n");
			} else {
				String msg = "Le sommet " + ((sSrc == null) ? "source" : "destination") + " n 'est pas séléctionné";
				JOptionPane.showMessageDialog(mainFrame, msg, "Erreur de sélection", JOptionPane.ERROR_MESSAGE);
				this.supprimerArc(cmbSrc, cmbDest);
			}
		}
	}

	private void chercherSommetNom() {
		String nomSommet = JOptionPane.showInputDialog("Entrer le nom du sommet à chercher : ");
		if (nomSommet != null && nomSommet.length() != 0) {
			Sommet s = PanelAction.this.ctrl.getSommet(nomSommet);
			if (s != null) {
				PanelAction.this.ctrl.ajouterTexte(s.toString() + "\n");
			} else {
				JOptionPane.showMessageDialog(null, "Le sommet " + nomSommet + " n'existe pas", "Erreur recherche d'un sommet par nom", JOptionPane.ERROR_MESSAGE);
				PanelAction.this.chercherSommetNom();
			}
		}
	}
	

	private JComboBox cmbSommetSrc;
	private JTable tableDistance;
	private void plusCourtDistanceJOptionPane(JComboBox anciencmbSommetSrc) {
		JPanel p = new JPanel(new GridLayout(3, 1, 5, 5)); {
			if (anciencmbSommetSrc == null || anciencmbSommetSrc.getItemCount() == 0) {
				this.cmbSommetSrc = new JComboBox();
				for (Sommet s : PanelAction.this.ctrl.getLstSommets()) {
					this.cmbSommetSrc.addItem(s);
				}
				this.cmbSommetSrc.setSelectedIndex(-1);
			}
			this.cmbSommetSrc.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					Sommet sSrc = (Sommet) PanelAction.this.cmbSommetSrc.getSelectedItem();
					if (sSrc != null) {
						Map<Sommet, Integer> mapDistance = PanelAction.this.ctrl.plusCourteDistance(sSrc);
						DefaultTableModel modelTable = (DefaultTableModel) new DefaultTableModel();
						modelTable.addColumn("Type");
						modelTable.addColumn("Nom");
						modelTable.addColumn("Distance");
						for (Map.Entry<Sommet, Integer> map : mapDistance.entrySet()) {
							Sommet s = map.getKey();
							Integer d = map.getValue();
							String sType = s instanceof Utilisateur ? "Utilisateur" : s instanceof Page ? "Page" : "Sommet";
							modelTable.addRow(new Object[]{sType, s.getNom(), d});
						}
						PanelAction.this.tableDistance.setModel(modelTable);
						DefaultTableCellRenderer render = new DefaultTableCellRenderer();
						render.setHorizontalAlignment(SwingUtilities.CENTER);
						PanelAction.this.tableDistance.getColumnModel().getColumn(0).setCellRenderer(render);
						PanelAction.this.tableDistance.getColumnModel().getColumn(1).setCellRenderer(render); 
						PanelAction.this.tableDistance.getColumnModel().getColumn(2).setCellRenderer(render); 
					}
				}
			});
			JPanel q = new JPanel(new FlowLayout()); {
				q.add(new JLabel("Séléctionner le sommet source :"));
				q.add(this.cmbSommetSrc);
			}
			p.add(q, BorderLayout.NORTH);

			Object[] cols = {
				"Type", "Nom", "Distance"
			};
			Object[][] rows = new Object[0][3];
			this.tableDistance = new JTable(rows, cols);
			this.tableDistance.setEnabled(true);
			DefaultTableCellRenderer render = new DefaultTableCellRenderer();
			render.setHorizontalAlignment(SwingUtilities.CENTER);
			this.tableDistance.getColumnModel().getColumn(0).setCellRenderer(render);
			this.tableDistance.getColumnModel().getColumn(1).setCellRenderer(render); 
			this.tableDistance.getColumnModel().getColumn(2).setCellRenderer(render);
			p.add(new JScrollPane(this.tableDistance), BorderLayout.CENTER);
			p.setPreferredSize(new Dimension(800, 600));
		}

		JFrame mainFrame = (JFrame) SwingUtilities.getWindowAncestor(PanelAction.this);
		JOptionPane.showConfirmDialog(mainFrame, p, "Plus courte distance", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
	}

}
