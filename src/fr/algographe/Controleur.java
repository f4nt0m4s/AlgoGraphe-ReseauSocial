package fr.algographe;

import java.util.List;
import java.util.ArrayList;
import java.util.Random;

import java.io.IOException;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Map;

import javax.swing.SwingUtilities;

import fr.algographe.Graphe.BadSyntaxException;
import fr.algographe.gui.Fenetre;

public class Controleur {

	private static final long serialVersionUID = 1L;
	
	private Graphe graphe;
	private Fenetre fenetre;
	
	public Controleur() {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				Controleur.this.fenetre = new Fenetre(Controleur.this);
				Controleur.this.fenetre.display();
			}
		});
		this.graphe = new Graphe();
	}

	/*----------------------------------------------------------*/
	/* 							MODÈLE							*/
	/*----------------------------------------------------------*/

	public int getNbSommets() {
		return this.graphe.getNbSommets();
	}

	public int getNbArcs() {
		return this.graphe.getNbArcs();
	}

	public List<Sommet> getLstSommets() {
		return this.graphe.getLstSommets();
	}

	public List<Utilisateur> getLstUtilisateurs() {
		return this.graphe.getLstUtilisateurs();
	}
	
	public List<Page> getLstPages() {
		return this.graphe.getLstPages();
	}

	public List<Sommet> getLstSommetsTrieParNom() {
		return this.graphe.getLstSommetsTrieParNom();
	}

	public List<Sommet> getLstSommetTrieParDegreSortant() {
		return this.graphe.getLstSommetTrieParDegreSortant();
	}

	public Sommet getSommet(String nom) {
		return this.graphe.getSommet(nom);
	}

	public void ajouterSommet(Sommet s) {
		this.graphe.ajouterSommet(s);
	}

	public void supprimerSommet(Sommet s) {
		this.graphe.supprimerSommet(s);
	}

	public void ajouterArc(Sommet s1, Sommet s2) {
		this.graphe.ajouterArc(s1, s2);
	}

	public List<Sommet> lstSommetSansArc(Sommet s1) {
		return this.graphe.lstSommetSansArc(s1);
	}

	public void supprimerArc(Sommet s1, Sommet s2) {
		this.graphe.supprimerArc(s1, s2);
	}

	public int getNbPage() {
		return this.graphe.getNbPage();
	}

	public int getNbUtilisateur() {
		return this.graphe.getNbUtilisateur();
	}

	public int getAgeMoyen() {
		return this.graphe.getAgeMoyen();
	}

	public List<Utilisateur> getUtilisateurAdministrateur() {
		return this.graphe.getUtilisateurAdministrateur();
	}

	public void pageRank() {
		this.graphe.pageRank();
	}

	public List<Sommet> getLstSommetTrieParPageRank() {
		return this.graphe.getLstSommetTrieParPageRank();
	}

	public Map<Sommet, Integer> plusCourteDistance(Sommet s) {
		return this.graphe.plusCourteDistance(s);
	}
	
	public void lireFichier(java.io.File f) {
		try {
			this.graphe.lireFichier(f);
		} catch(Exception e) {}
	}

	public void ecrireFichier(java.io.File f) {
		try {
			this.graphe.ecrireFichier(f);
		} catch(IOException e) {}
	}
	
	public String toString() {
		return this.graphe.toString();
	}

	// Initialisation du graphe avec un fichier
	public void initGrapheAvecFichier() {
		try {
			File dir = new File("../fr/algographe/ressources/");
			File[] listFichiers = dir.listFiles();
			Random random = new Random();
			String fichier = listFichiers[random.nextInt(listFichiers.length)].toString();
			java.io.File f = new java.io.File(fichier);
			this.graphe.lireFichier(f); // FileNotFoundException
		} catch (Exception e) { e.printStackTrace(); }
	}

	// Ecriture d'un fichier
	public void ecrireFichier() {
		try {
			String fichier = "../fr/algographe/ecriture.txt";
			//fichier = "./src/fr/algographe/ecriture.txt";//pour ceux qui le lancent depuis un IDE
			java.io.File f = new java.io.File(fichier);
			this.graphe.ecrireFichier(f);
		} catch (IOException  e) { e.printStackTrace(); }
	}

	/*--------------------------------------------------------------*/
	/* 							GRAPHIQUE							*/
	/*--------------------------------------------------------------*/

	public void ajouterTexte(String txt) {
		this.fenetre.ajouterTexte(txt);
	}

	public void refreshDessin() {
		this.fenetre.refreshDessin();
	}

	public static void main(String[] args) {
		System.out.println("================================");
		System.out.println("== LANCEMENT DE L'APPLICATION ==");
		System.out.println("================================");
		// Lancement de l'application
		Controleur c = new Controleur();
		// Initialisation du graphe
		c.initGrapheAvecFichier();
	}
}
