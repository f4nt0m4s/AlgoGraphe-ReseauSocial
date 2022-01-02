package fr.algographe;

import java.util.List;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Map;
import java.util.HashMap;
import java.util.Collections;
import java.util.Comparator;
import java.util.Set;
import java.util.HashSet;
import java.util.Iterator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import java.io.File;
import java.io.FileInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.nio.charset.Charset;

import fr.algographe.util.Contract;

public final class Graphe {

	// Sommet utilisateur -> (U) Nom Prénom Age
	private static final String EXPR_SOMMET_UTILISATEUR = "(\\([UP]\\))((\\s\\w+|\\W+[\\w\\W\\s]+){2})(\\d{2})"; // FAIRE LES CARACTERES SPECIAUX POUR LES AUTRE REGEX
	// Sommet page -> (P) Nom
	private static final String EXPR_SOMMET_PAGE = "(\\(P\\))([\\s\\w+\\W+]+)";
	// Liste de voisin(s) pour un utilisateur -> (U) Nom_utilisateur : (U|P) Nom, (U|P) Nom
	private static final String EXPR_VOISIN_UTILISATEUR = "(\\(U\\))(\\s[\\w\\W+]+)(\\s\\:(\\s\\([UP]\\)[\\s\\w+\\W+]\\w+,?)+)?";
	// Liste d'admin(s) pour une page -> (P) Nom_page : (U) Nom, (U) Nom
	private static final String EXPR_VOISIN_PAGE = "(\\(P\\))(\\s[\\s\\w+\\W+]+)(\\s\\:(\\s\\(U\\)[\\s\\w+\\W+]\\w+,?)+)?";
	// Si il y n'y a pas de voisins, ne pas l'indiqué dans le fichier
	private static final String FICHIER_INDIQUE_SOMMETS = "Sommets :"; // Séparateur de sommet(s) dans le fichier de configuration
	private static final String FICHIER_INDIQUE_VOISINS = "Voisins :"; // Séparateur voisin(s) dans le fichier de configuration

	private List<Sommet> lstSommets;

	public Graphe() {
		this.lstSommets = new ArrayList<Sommet>();
	}
	
	public Graphe(List<Sommet> lstSommets) {
		this.lstSommets = new ArrayList<Sommet>(lstSommets);
	}
	
	// connaitre le nombre de sommets
	public int getNbSommets() {
		return this.lstSommets.size();
	}
	
	// connaitre le nombre d’arcs
	public int getNbArcs() {
		int nbArc = 0;
		for(Sommet s : this.lstSommets) {
			List<? extends Sommet> lst = null;
			if (s instanceof Utilisateur) {
				lst = s.getLstVoisins();
			} else if (s instanceof Page) {
				lst = ((Page) s).getLstAdmins();
			}
			if (lst != null) {
				for(Sommet v : lst) {
					if (v != null) {
						nbArc++;
					}
				}
			}
		}
		return nbArc;
	}
	
	// obtenir l’ensemble des sommets
	public List<Sommet> getLstSommets() {
		return new ArrayList<Sommet>(this.lstSommets);
	}

	// obtenir l’ensemble des sommets triée par nom ✖
	public List<Sommet> getLstSommetsTrieParNom() {
		Collections.sort(this.lstSommets, new Comparator<Sommet>() {
			@Override
			public int compare(Sommet o1, Sommet o2) {
				String sO1 = o1.getNom().toLowerCase();
				String sO2 = o2.getNom().toLowerCase();
				for (int i = 0; i < sO1.length() && i < sO2.length(); i++) {
					if ((int) sO1.charAt(i) != (int) sO2.charAt(i)) {
						return (int) sO1.charAt(i) - (int) sO2.charAt(i);
					}
				}

				if (sO1.length() != sO2.length()) {
					return (sO1.length() - sO2.length());
				}

				return 0;
			}
		});
		return new ArrayList<Sommet>(this.lstSommets);
	}

	//obtenir l’ensemble des sommets trie par degre sortant
	public List<Sommet> getLstSommetTrieParDegreSortant()
	{
		Collections.sort(this.lstSommets, new Comparator<Sommet>() {
			@Override
			public int compare(Sommet o1, Sommet o2) {
				int o1Val = (o1 instanceof Utilisateur) ? o1.getLstVoisins().size() : (o1 instanceof Page) ? ((Page) o1).getLstAdmins().size() : 0;
				int o2Val = (o2 instanceof Utilisateur) ? o2.getLstVoisins().size() : (o2 instanceof Page) ? ((Page) o2).getLstAdmins().size() : 0;
				return o1Val - o2Val;
			}
		});
		return new ArrayList<Sommet>(this.lstSommets);
	}

	//ajoute le Sommet dans la liste et ajoute ses voisins
	public void ajouterSommet(Sommet s) {
		this.lstSommets.add(s);
		for (Sommet v: s.getLstVoisins())
		{
			if (this.lstSommets.contains(s))
			{
				s.addVoisin(v);
			}
		}
	}

	//Supprime le Sommet dans la liste et ses arcs
	public void supprimerSommet(Sommet s) {
		for (Sommet v: this.lstSommets)
		{
			if (v instanceof Utilisateur)
			{
				if (v.getLstVoisins().contains(s))
				{
					v.removeVoisin(s);
				}
			}
			else if (v instanceof Page)
			{
				if (((Page) v).getLstAdmins().contains(s))
				{
					((Page) v).getLstAdmins().remove(s);
				}
			}
		}
		this.lstSommets.remove(s);
	}

	//ajouter un arc
	public void ajouterArc(Sommet s1, Sommet s2)
	{
		if (this.lstSommets.contains(s1) && this.lstSommets.contains(s2))
		{
			if (s1 instanceof Utilisateur && (s2 instanceof Utilisateur || s2 instanceof Page)) {
				this.lstSommets.get(this.lstSommets.indexOf(s1)).addVoisin(s2);
			} else if (s1 instanceof Page && s2 instanceof Utilisateur) {
				((Page) this.lstSommets.get(this.lstSommets.indexOf(s1))).addAdmin((Utilisateur) s2);
			}
		}
	}

	// Retourne une liste de sommet qui ne font pas partie des voisisn ou des admins de s1
	public List<Sommet> lstSommetSansArc(Sommet s1) {
		List<Sommet> lstSommet = new ArrayList<Sommet>();
		for (Sommet s : this.lstSommets) {
			if (s1 instanceof Utilisateur) {
				if (!s1.getLstVoisins().contains(s)) {
					lstSommet.add(s); 
				}
			} else if (s1 instanceof Page) {
				if (!((Page) s1).getLstAdmins().contains(s) && s instanceof Utilisateur) {
					lstSommet.add(s); 
				}
			}
		}
		return lstSommet;
	}

	//Supprimer un arc
	public void supprimerArc(Sommet s1, Sommet s2)
	{
		if (this.lstSommets.contains(s1))
		{
			if (s1 instanceof Utilisateur && (s2 instanceof Utilisateur || s2 instanceof Page)) {
				this.lstSommets.get(this.lstSommets.indexOf(s1)).addVoisin(s2);
				this.lstSommets.get(this.lstSommets.indexOf(s1)).removeVoisin(s2);
			} else if (s1 instanceof Page && s2 instanceof Utilisateur) {
				((Page) this.lstSommets.get(this.lstSommets.indexOf(s1))).removeAdmin((Utilisateur) s2);
			}
		}
	}

	//obtenir les informations sur un sommet via son nom
	public Sommet getSommet(String nom)
	{
		for (Sommet s: this.lstSommets)
		{
			if (s.getNom().equals(nom))
			{
				return s;
			}
		}
		return null;
	}

	//connaitre le nombre de comptes de type Page
	public int getNbPage()
	{
		int cpt = 0;
		for (Sommet s: this.lstSommets)
		{
			if (s instanceof Page)
			{
				cpt ++;
			}
		}
		return cpt;
	}

	//connaitre le nombre de comptes de type Utilisateur
	public int getNbUtilisateur()
	{
		int cpt = 0;
		for (Sommet s: this.lstSommets)
		{
			if (s instanceof Utilisateur)
			{
				cpt ++;
			}
		}
		return cpt;
	}

	//Connaitre l’age moyen des Utilisateur
	public int getAgeMoyen()
	{
		int cpt = 0;
		for (Sommet s: this.lstSommets)
		{
			if (s instanceof Utilisateur)
			{
				cpt += ((Utilisateur) s).getAge();
			}
		}
		if (cpt != 0)
		{
			return cpt / this.getNbUtilisateur();
		}
		return cpt;
	}

	// Retourne tous les sommets qui sont des utilisateurs
	public List<Utilisateur> getLstUtilisateurs() {
		List<Utilisateur> lstUtilisateurs = new ArrayList<Utilisateur>();
		for(Sommet s : this.getLstSommets()) {
			if (s instanceof Utilisateur) {
				lstUtilisateurs.add(((Utilisateur) s));
			}
		}
		return lstUtilisateurs;
	}

	// Retourne tous les sommets qui sont des pages
	public List<Page> getLstPages() {
		List<Page> lstPages = new ArrayList<Page>();
		for(Sommet s : this.getLstSommets()) {
			if (s instanceof Page) {
				lstPages.add(((Page) s));
			}
		}
		return lstPages;
	}

	// Connaitre tous les comptes Utilisateur qui sont des administrateurs de Page
	public List<Utilisateur> getUtilisateurAdministrateur() {
		ArrayList<Utilisateur> utilisateurs = new ArrayList<Utilisateur>();
		for (Sommet s : this.lstSommets) {
			if (s instanceof Page)
			{
				for (Utilisateur u : ((Page) s).getLstAdmins())
				{
					if (!utilisateurs.contains(u))
					{
						utilisateurs.add(u);
					}
				}
			}
		}
		return utilisateurs;
	}


	// Liste de sommets des sommets entrants du sommet u
	public List<Sommet> getSommetsEntrants(Sommet s) {
		List<Sommet> sEntrants = new ArrayList<Sommet>();
		for(Sommet u : this.lstSommets) {
			if (!s.equals(u)) {
				if (u instanceof Utilisateur && u.getLstVoisins().contains(s)) {
					sEntrants.add(u);
				} else if (u instanceof Page && ((Page) u).getLstAdmins().contains(s)) {
					sEntrants.add(u);
				}
			}
		}
		return sEntrants;
	}

	//  Calcul du Pagerank
	public void pageRank() {
		// Initialisation du PageRank `a 1 pour tous les sommets
		for(Sommet s : this.lstSommets) {
			s.setPageRank(1);
		}
		// Calcul du PageRank
		int i = 0;
		final int max = 100;
		while (i < max) {
			// Pour tous les sommets du graphe
			for(Sommet v : this.lstSommets) {
				float somme = 0;
				for(Sommet u : this.getSommetsEntrants(v)) {
					if (u.getLstVoisins().size() > 0) {
						somme += (u.getPageRank() / u.getLstVoisins().size());
					}
				}
				float pageRank = (float) ((0.15 / this.lstSommets.size()) + 0.85 * somme);
				v.setPageRank(pageRank);
			}
			i++;
		}
	}
	
	public List<Sommet> getLstSommetTrieParPageRank() {
		List<Sommet> lst = new ArrayList<Sommet>(this.lstSommets);
		Collections.sort(lst, new Comparator<Sommet>() {
			@Override
			public int compare(Sommet o1, Sommet o2) {
				return Float.compare(o1.getPageRank(), o2.getPageRank());
			}
		});
		return lst;
	}

	public class BadSyntaxException extends Exception {
		private static final long serialVersionUID = 1L;
		public BadSyntaxException(String message) {
			super(message);
		}
	}

	public Map<Sommet, Integer> plusCourteDistance(Sommet s)
	{
		int distance = 0;
		Map <Sommet, Integer> map = new HashMap<Sommet, Integer>(this.lstSommets.size());
		List<Sommet> sommetsOuvert = new ArrayList<Sommet>();
		List<Sommet> sommetsTrouve = new ArrayList<Sommet>();
		for (Sommet s1: lstSommets)
		{
			if (!s.equals(s1))
			{
				map.put(s1, 10000000);
			}
			else
			{
				map.put(s1, distance);
				sommetsOuvert.add(s1);
			}
		}
		while (sommetsOuvert.size() != 0)
		{
			distance ++;
			for (Sommet sommetO: sommetsOuvert)
			{
				if (sommetO instanceof Page)
				{
					for (Sommet sommetV: ((Page) sommetO).getLstAdmins())
					{
						if (distance <= map.get(sommetV))
						{
							map.put(sommetV, distance);
							sommetsTrouve.add(sommetV);
						}
					}
				}
				else
				{
					for (Sommet sommetV: sommetO.getLstVoisins())
					{
						if (distance <= map.get(sommetV))
						{
							map.put(sommetV, distance);
							sommetsTrouve.add(sommetV);
						}
					}
				}
			}
			sommetsOuvert.removeAll(sommetsOuvert);
			sommetsOuvert.addAll(sommetsTrouve);
			sommetsTrouve.removeAll(sommetsTrouve);
		}
		return map;
	}

	public void lireFichier(File f) throws FileNotFoundException, IOException, BadSyntaxException {
		InputStreamReader is;
		BufferedReader br;
		Charset charset = Charset.forName("UTF-8");
		try {
			is = new InputStreamReader(new FileInputStream(f), charset);
			br = new BufferedReader(is);
		} catch (java.io.FileNotFoundException fe) {
			fe.printStackTrace();
			throw new FileNotFoundException("Le fichier " + f.getName() + " est introuvable");
		}
		
		// La liste des nouveaux sommets
		Set<Sommet> setSommet = new HashSet<Sommet>();

		try {
			String line;
			int numLine = 1;

			boolean estCreationSommets = false; // Indique si c'est le séparateur pour informer que c'est la création de sommet(s)
			boolean estCreationVoisins = false; // Indique si c'est le séparateur pour informer que c'est la création de voisin(s)

			Pattern pUtilisateur;
			Matcher mUtilisateur;
			Pattern pPage;
			Matcher mPage;
			Matcher match;

			while ((line = br.readLine()) != null) {
				if (line.equals(FICHIER_INDIQUE_SOMMETS)) {
					estCreationSommets = true;
					estCreationVoisins = false;
				} else if (line.equals(FICHIER_INDIQUE_VOISINS)) {
					estCreationSommets = false;
					estCreationVoisins = true;
				} else if (!line.equals(FICHIER_INDIQUE_SOMMETS) && !line.equals(FICHIER_INDIQUE_VOISINS)) {
					//Rentre tout le temps dedans meme quand la ligne est fausse à corriger
					// Traitement de la création des sommets
					if (estCreationSommets && !estCreationVoisins) {
						pUtilisateur = Pattern.compile(EXPR_SOMMET_UTILISATEUR);
						mUtilisateur = pUtilisateur.matcher(line);
						pPage = Pattern.compile(EXPR_SOMMET_PAGE);
						mPage = pPage.matcher(line);
						match = mUtilisateur.matches() ? mUtilisateur : mPage.matches() ? mPage : null;

						if (match != null && match.matches()) {
							Sommet sommet = null;
							String type = match.group(1).replace("(", "").replace(")", "").trim(); // Enlève les parenthèse de (U) (P)
							String nom = null;
							// Traitement de la regex Utilisateur
							if (type.equals("U")) {
								String[] nomEtPrenom = match.group(2).trim().split(" ");
								nom = nomEtPrenom[0];
								String prenom = nomEtPrenom[1];
								int age = Integer.parseInt(match.group(4).trim());
								sommet = new Utilisateur(nom, prenom, age);
								//System.out.println("[Création Sommet] Utilisateur : " + nom + " " + prenom + " " + age + " an" + ((age > 1) ? "s": ""));
							} else if (type.equals("P")) {
								nom = match.group(2).trim();
								sommet = new Page(nom);
								//System.out.println("[Création Sommet] Page : " + nom);
							}

							// Vérifie que le sommet n'existe pas
							boolean estSommetExistant = false;
							for(Sommet s : setSommet) {
								estSommetExistant = s.getNom().equals(nom);
								break;
							}

							if (!estSommetExistant) {
								setSommet.add(sommet);
							} else {
								String sError  = "Le sommet à la ligne " + numLine + " (" + nom + ") a un nom déja existant." + "\n";
								sError += "Veuillez choisir un autre nom." + "\n";
								br.close();
								throw new BadSyntaxException(sError);
							}
						}
						else {
							String sError = "La ligne " + numLine + " est incorrecte pour la création d'un sommet, plus précisement :\n";
							line = (line.length() == 0) ? "La ligne " + numLine + " est vide\n" : line + "\n";
							sError += line;
							sError += "Veuillez faire en sorte qu'elle respecte le bon format fournit dans la documentation.\n";
							br.close();
							throw new BadSyntaxException(sError);
						}
					} else if (!estCreationSommets && estCreationVoisins) {
						// Traitement des voisins
						pUtilisateur = Pattern.compile(EXPR_VOISIN_UTILISATEUR);
						mUtilisateur = pUtilisateur.matcher(line);
						pPage = Pattern.compile(EXPR_VOISIN_PAGE);
						mPage = pPage.matcher(line);
						match = mUtilisateur.matches() ? mUtilisateur : mPage.matches() ? mPage : null;
						
						if (match != null && match.matches()) {
							String type = match.group(1).replace("(", "").replace(")", "").trim();
							String nom = match.group(2).trim();
							// Enlève le caractère ':' pour un ligne où il y est précisé un ou plusieurs voisins
							if (nom.indexOf(':') != -1) {
								nom = nom.substring(0, nom.indexOf(':')).trim();
							}
							
							// Vérifie que le sommet a été déclaré précédemment
							Sommet sommet = null;
							boolean estSommetExistant = false;
							boolean estBonType = false;
							for(Sommet s : setSommet) {
								if (s.getNom().equals(nom)) {
									estSommetExistant = true;
									if ((type.equals("U") && s instanceof Utilisateur) || (type.equals("P") && s instanceof Page)) {
										estBonType = true;
										sommet = s;
										break;
									}
								}
							}
							
							if (estSommetExistant && estBonType) {
								String sVoisins = (match.group(2) != null) ? match.group(2).trim() : "";
								System.out.println(sVoisins);
								final String PATTERN_SEPARE_VOISINS = "(\\([UP]\\))(\\s\\w+[\\w\\s]+),?";
								Matcher mVoisins = Pattern.compile(PATTERN_SEPARE_VOISINS).matcher(sVoisins);
								String typeVoisin = null;
								String nomVoisin = null;
								// Traitement de chaque voisin renseigné
								while (mVoisins.find()) {
									// Récupère le type et le nom du voisin
									typeVoisin = mVoisins.group(1).replace("(", "").replace(")", "").trim();
									nomVoisin = mVoisins.group(2).trim();

									boolean estLuiMemeVoisin = false;
									boolean estBonFormatVoisin = false;
									boolean estDoublon = false;

									// TRAITER LE CAS O% DEUX FOIS MEME VOISIN RENSEIGNE
									// ex : (P) KFC : (U) Bonnet, (U) Bonnet
									if (sommet.getNom().equals(nomVoisin)) {
										estLuiMemeVoisin = true;
									} else {
										estSommetExistant = false;
										estBonType = false;
										estBonFormatVoisin = false; // Indique si l'utilisateur a bien une page ou un autre utilisateur, si une page a bien une liste d'administrateur(s)
										
										// Vérifie si il existe un sommet correspondant au voisin que l'on est entrain de traiter
										for(Sommet sommetVoisin : setSommet) {
											// Si le nom correspond
											if (sommetVoisin.getNom().equals(nomVoisin)) {
												estSommetExistant = true;
												
												// Si le type correspond
												if ((typeVoisin.equals("U") && sommetVoisin instanceof Utilisateur) || (typeVoisin.equals("P") && sommetVoisin instanceof Page)) {														
													estBonType = true;
													if (sommet instanceof Utilisateur && (sommetVoisin instanceof Page || sommetVoisin instanceof Utilisateur)) {
														estBonFormatVoisin = true;
														// N'ajoute pas les doublons
														for(Sommet voisin : sommet.getLstVoisins()) {
															if (voisin.getNom().equals(sommetVoisin.getNom())) {
																estDoublon = true;
															}
														}
														// Ajout d'utilisateur ou du page s'il existe pas déja
														if (!estDoublon) {
															((Utilisateur) sommet).addVoisin(sommetVoisin);
														}
													} else if (sommet instanceof Page && sommetVoisin instanceof Utilisateur) {
														estBonFormatVoisin = true;
														// N'ajoute pas les doublons
														for(Utilisateur admin : ((Page) sommet).getLstAdmins()) {
															if (admin.getNom().equals(sommetVoisin.getNom())) {
																estDoublon = true;
															}
														}
														// Ajout uniquement d'utilisateur admin(s) pour un page s'il n'existe pas déja
														if (!estDoublon) {
															((Page) sommet).addAdmin((Utilisateur) sommetVoisin);
														}
													}
												}
											}
										}
									}
									
									String sError = (!estSommetExistant || !estBonType) ? "Le sommet à la ligne " + numLine + " (" + nomVoisin + ") n'existe pas, plus précisement :" + "\n" : "";
									if (estLuiMemeVoisin) {
										sError += "Le sommet " + nom + " ayant comme voisin " + nomVoisin + " ne peut pas s'ajouter lui même comme voisin" + "\n";
									}
									else if (!estSommetExistant) {
										sError += "Pour ajouter des voisins à " + nom + ", il faut le déclarer après \"Sommets : \" dans le fichier de configuration." + "\n";
									} else if (!estBonType) {
										sError += "Le type entre parenthèse (" + typeVoisin + ") à " + nomVoisin + " pour le sommet " + nom + " ne correspond pas à son type déclaré dans \"Sommets : \"." + "\n";
									} else if (!estBonFormatVoisin) {
										sError += "Un utilisateur peut suivre un autre utilisateur ou aimer une page" + "\n";
										sError += "Une page peut avoir que des administrateurs" + "\n";
									} else if (estDoublon) {
										sError += "Le voisin " + nomVoisin + " est déja présent dans la liste des voisins informés pour " + nom + "\n";
									}

									if (sError.length() != 0) {
										br.close();
										throw new BadSyntaxException(sError);
									}

								}
							} else {
								String sError  = "Le sommet à la ligne " + numLine + " (" + nom + ") n'existe pas, plus précisement :" + "\n";
								if (!estSommetExistant) {
									sError += "Pour ajouter des voisins à " + nom + ", il faut le déclarer après \"Sommets : \" dans le fichier de configuration." + "\n";
								} else if (!estBonType) {
									sError += "Le type entre parenthèse (" + type + ") à " + nom + " ne correspond pas à son type déclaré dans \"Sommets : \"." + "\n";
								}
								br.close();
								throw new BadSyntaxException(sError);
							}

						} else {
							String sError = "La ligne " + numLine + " ne respecte pas le format de la liste de voisin(s), plus précisement :\n";
							sError += line + "\n";
							sError += "Format à respecter pour les voisins : \n";
							sError += String.format("%s", "Type Utilisateur -> (U) NOM : (U|P) Nom, (U|P) Nom, ..., (U|P) Nom" + "\n");
							sError += "Type Page -> (P) NOM : (U) Nom, (U) Nom, ..., (U) Nom" + "\n";
							br.close();
							throw new BadSyntaxException(sError);
						}
					}
				} else {
					String sError = "La ligne " + numLine + " ne respecte ni le format de la création d'un sommet ni le format de la liste de voisin(s), plus précisement :\n";
					line = (line.length() == 0) ? "La ligne " + numLine + " est vide\n" : line + "\n";
					sError += line + "\n";
					br.close();
					throw new BadSyntaxException(sError);
				}
				numLine++;
			}

		} catch(IOException io) {
			io.printStackTrace();
			br.close();
			throw new IOException();
		}
		br.close();

		// Modification des sommets par le fichier lu
		if (setSommet.size() > 0) {
			this.lstSommets.clear();
			this.lstSommets.addAll(setSommet);
		} else {
			throw new InternalError("Aucun sommet n'a été ajouté avec le fichier lu car le format du fichier est incorrect");
		}
	}

	// Pour ordonnée le fichier selon un ordre lexicale
	private void triSommets() {
		Collections.sort(this.lstSommets, new Comparator<Sommet>() {
			@Override
			public int compare(Sommet o1, Sommet o2) {
				if (o1.getClass().getSimpleName().equals(o2.getClass().getSimpleName())) {
					return o1.getNom().compareTo(o2.getNom());
				}
				return o1.getClass().getSimpleName().compareTo(o2.getClass().getSimpleName());
			}
		});
	}

	public void ecrireFichier(File f) throws IOException {
		OutputStreamWriter os;
		BufferedWriter br;
		Charset charset = Charset.forName("UTF-8");
		try {
			os = new OutputStreamWriter(new FileOutputStream(f), charset);
			br = new BufferedWriter(os);
		} catch (IOException io) {
			io.printStackTrace();
			throw new IOException("Le fichier " + f.getName() + " n'a pas être ouvert");
		}

		try {
			triSommets();
			// Écriture des sommets
			br.write(FICHIER_INDIQUE_SOMMETS + "\n");
			String sSommet = "";
			for(Sommet s : this.lstSommets) {
				sSommet = "(" + Character.toString(s.getClass().getSimpleName().charAt(0)).toUpperCase() + ")" + " ";
				sSommet += s.getNom();
				if (s instanceof Utilisateur) {
					Utilisateur u = (Utilisateur) s;
					sSommet += " " + u.getPrenom() + " " + u.getAge();
				}
				sSommet += "\n";
				br.write(sSommet);
			}
			// Écriture des voisins
			br.write(FICHIER_INDIQUE_VOISINS + "\n");
			sSommet = "";
			for(Sommet s : this.lstSommets) {
				sSommet = "(" + Character.toString(s.getClass().getSimpleName().charAt(0)).toUpperCase() + ")" + " ";
				sSommet += s.getNom();
				// Parcourt de la liste des voisins pour les écrires
				Iterator<Sommet> it = s.getLstVoisins().iterator();
				sSommet += (it.hasNext() ? " : " : "");
				Sommet v = null;
				while(it.hasNext()) {
					v = it.next();
					sSommet += "(" + Character.toString(v.getClass().getSimpleName().charAt(0)).toUpperCase() + ")" + " ";
					sSommet += v.getNom() + (it.hasNext() ? ", " : "");
				}
				sSommet += "\n";
				br.write(sSommet);
			}

		} catch(IOException io) {
			io.printStackTrace();
			br.close();
			throw new IOException();
		}
		br.close();
	}


	@Override
	public String toString() {
		String s = "";
		for(Sommet sommet : this.lstSommets) {
			s += "\t" + sommet.toString() + "\n";
			if (sommet.getLstVoisins().size() > 0) {
				s += "\t\tVoisin";
				if (sommet.getLstVoisins().size() > 1) {
					s += "s";
				}
				s += " : ";
			}
			for(Sommet voisin : sommet.getLstVoisins()) {
				s += voisin.getNom() + " ";
			}
			s += "\n";
		}
		return s;
	}
}
