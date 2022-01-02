package fr.algographe;

import java.util.List;
import java.util.ListIterator;
import java.util.LinkedList;
import java.util.Iterator;

import fr.algographe.util.Contract;

public abstract class Sommet {

	private static final long serialVersionUID = 1L;
	
	private String nom;
	private List<Sommet> lstVoisins; // liste de voisin(s) sortant(s)
	private float pageRank;

	public Sommet(String nom) {
		Contract.checkCondition(nom != null && nom.length() > 0, "Le sommet doit avoir un nom");
		this.nom = nom;
		this.lstVoisins = new LinkedList<Sommet>();
		this.pageRank = 0f;
	}

	public Sommet(String nom, List<Sommet> lstVoisins) {
		this(nom);
		Contract.checkCondition(lstVoisins != null, "Le liste de voisin(s) est null");
		this.lstVoisins = new LinkedList<Sommet>(lstVoisins);
		this.pageRank = 0f;
	}

	public String getNom() {
		return this.nom;
	}


	public List<Sommet> getLstVoisins() {
		return new LinkedList<Sommet>(this.lstVoisins);
	}
	
	public Sommet getVoisin(int index) {
		Contract.checkCondition(index >= 0 && index < this.lstVoisins.size(), "getVoisin index invalide");
		return this.lstVoisins.get(index);
	}

	public float getPageRank() {
		return this.pageRank;
	}
	
	public void setNom(String nom) {
		Contract.checkCondition(nom != null && nom.length() > 0, "setNom Le sommet doit avoir un nom");
		this.nom = nom;
	}
	
	public boolean addVoisin(Sommet voisin) {
		Contract.checkCondition(voisin != null, "addVoisin voisin est null");
		if (this instanceof Page) {
			throw new UnsupportedOperationException();
		}
		return this.lstVoisins.add(voisin);
	}

	public boolean removeVoisin(Sommet voisin) {
		Contract.checkCondition(voisin != null, "addVoisin voisin est null");
		if (this instanceof Page) {
			throw new UnsupportedOperationException();
		}
		return this.lstVoisins.remove(voisin);
	}

	public void setPageRank(float pageRank) {
		Contract.checkCondition(pageRank >= 0, "pageRank >= 0");
		this.pageRank = pageRank;
	}

	@Override
	public boolean equals(Object other) {
		boolean result = false;
		if (other instanceof Sommet) {
			Sommet that = (Sommet) other;
			result = that.canEquals(this)
					&& that.getNom().equals(this.getNom())
					&& that.getPageRank() == this.getPageRank();
			/* Stackoverflow car appelle la méthode equals de Sommet pour comparer chaque élement de la liste 
				that.getLstVoisins().equals(this.getLstVoisins());
				Donc on compare chaque sommet et on ne compare pas à nouveau le voisin du voisin du sommet
				mais juste les attributs
			*/
			if (that.getLstVoisins().size() == this.getLstVoisins().size()) {
				ListIterator<Sommet> iterThat = that.getLstVoisins().listIterator();
				ListIterator<Sommet> iterThis = that.getLstVoisins().listIterator();
				while(iterThat.hasNext() && iterThis.hasNext()) {
					Sommet sThat = iterThat.next();
					Sommet sThis = iterThis.next();
					if (!(sThat.getNom().equals(sThis.getNom())) 
						|| !(sThat.getPageRank() == sThis.getPageRank())) {
						result = false;
						break;
					}
				}
			}
		}
		return result;
	}
	
	private boolean canEquals(Object obj) {
		return obj instanceof Sommet;
	}
	
	@Override
	public String toString() {
		String s = "";
		s += "(Sommet) " + this.getNom() + " : ";
		for(Sommet voisin : this.getLstVoisins()){
			s+= voisin.getNom() + "->";
		}
		s += "#";
		return s;
	}
}
