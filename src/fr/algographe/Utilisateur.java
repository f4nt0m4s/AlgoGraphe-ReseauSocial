package fr.algographe;

import fr.algographe.util.Contract;

import java.util.LinkedList;
import java.util.List;

public final class Utilisateur extends Sommet {
	
	private static final long serialVersionUID = 1L;

	private String prenom;
	private int age;

	public Utilisateur(String nom, String prenom, int age) {
		super(nom);
		Contract.checkCondition(prenom != null && prenom.length() > 0, "L'utilisateur doit avoir un prénom");
		Contract.checkCondition(age >= 0, "L'utilisateur doit avoir un age à partir de 0");
		this.prenom = prenom;
		this.age = age;
	}
	
	public String getNom() {
		return super.getNom();
	}

	public String getPrenom() {
		return this.prenom;
	}

	public int getAge() {
		return this.age;
	}

	public void setNom(String nom) {
		super.setNom(nom);
	}

	public void setPrenom(String prenom) {
		Contract.checkCondition(prenom != null && prenom.length() > 0, "L'utilisateur doit avoir un prénom");
		this.prenom = prenom;
	}

	public void setAge(int age) {
		Contract.checkCondition(age >= 0, "L'utilisateur doit avoir un age à partir de 0");
		this.age = age;
	}

	@Override
	public boolean addVoisin(Sommet voisin) {
		Contract.checkCondition(voisin != null, "addVoisin est null");
		Contract.checkCondition(!voisin.equals(this), "addVoisin impossible d'ajouter le sommet courant en tant que voisin");
		Contract.checkCondition(this instanceof Utilisateur, "addVoisin pour Page aucune action possible");
		return super.addVoisin(voisin);
	}
	
	@Override
	public boolean equals(Object other) {
		boolean result = false;
		if (other instanceof Utilisateur) {
			Utilisateur that = (Utilisateur) other;
			result = super.equals(that)
					&& that.getNom().equals(this.getNom())
					&& that.getPrenom().equals(this.getPrenom())
					&& that.getAge() == this.getAge();
		}
		return result;
	}
	
	private boolean canEquals(Object obj) {
		return obj instanceof Utilisateur;
	}

	/*@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((this.getPrenom() == null) ? 0 : this.getNom().hashCode()) + this.age;
		return result;
	}*/

	@Override
	public String toString() {
		String s = "";
		s += "(Utilisateur) " + this.getNom() + " " + this.getPrenom() + " " + this.getAge() + " an" + (this.getAge() > 1 ? "s" : "") + "\n";
		if (this.getLstVoisins().size() > 0) {
			s += "Voisin" + (this.getLstVoisins().size() > 1 ? "s" : "") + " : \n";
			for (Sommet v : this.getLstVoisins()) {
				s += (v instanceof Utilisateur) ? "(Suit)" : (v instanceof Page) ? "(Aime)" : "";
				s += " " + v.getNom() + "\n";
			}
		}
		return s;
	}
}