package fr.algographe;

import java.util.LinkedList;
import java.util.List;
import java.util.ArrayList;
import fr.algographe.util.Contract;

public final class Page  extends Sommet {
	
	private static final long serialVersionUID = 1L;

	private List<Utilisateur> lstAdmins;

	public Page(String nom) {
		super(nom);
		this.lstAdmins = new ArrayList<Utilisateur>();
	}

	public Page(String nom, List<Utilisateur> lstAdmin) {
		super(nom);
		Contract.checkCondition(lstAdmin != null, "lstAdmin est null");
		this.lstAdmins = new ArrayList<Utilisateur>(lstAdmins);
	}

	public List<Utilisateur> getLstAdmins() {
		return this.lstAdmins;
	}

	public Utilisateur getAdmin(int index) {
		Contract.checkCondition(index >= 0 && index < this.lstAdmins.size(), "getAdmin index invalide");
		return this.lstAdmins.get(index);
	}
	
	public void setAdmin(int index, Utilisateur u) {
		Contract.checkCondition(u instanceof Utilisateur, "utilisateur instanceof Utilisateur");
		Contract.checkCondition(index >= 0 && index < this.lstAdmins.size(), "setAdmin index invalide");
		Contract.checkCondition(u != null, "utilisateur est nulle");
		this.lstAdmins.set(index, u);
	}

	/* à mettre pour la plus courte distance
	@Override
	public List<Sommet> getLstVoisins() {
		return new LinkedList<Sommet>(this.lstAdmins);
	}
	 */


	public void addAdmin(Utilisateur u) {
		Contract.checkCondition(u instanceof Utilisateur, "utilisateur instanceof Utilisateur");
		if (!this.lstAdmins.add(u)) {
			throw new InternalError("Erreur lors de l'ajout de l'administrateur " + u.getNom() + " à " + this.getNom());
		}
	}


	public void removeAdmin(Utilisateur u) {
		Contract.checkCondition(u instanceof Utilisateur, "utilisateur instanceof Utilisateur");
		if (!this.lstAdmins.remove(u)) {
			throw new InternalError("Erreur lors de la suppresion de l'administrateur " + u.getNom() + " à " + this.getNom());
		}
	}

	@Override
	public boolean addVoisin(Sommet voisin) {
		throw new UnsupportedOperationException();
	}
	
	@Override
	public boolean equals(Object other) {
		boolean result = false;
		if (other instanceof Page) {
			Page that = (Page) other;
			result = super.equals(that)
					&& that.getLstAdmins().equals(this.getLstAdmins());
		}
		return result;
	}
	
	private boolean canEquals(Object obj) {
		return obj instanceof Page;
	}

	/*@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((this.lstAdmins == null) ? 0 : lstAdmins.hashCode());
		return result;
	}*/
	
	@Override
	public String toString() {
		String s = "";
		s += "(Page) : " + this.getNom() + " \n";
		if (this.getLstAdmins().size() > 0) {
			s += "Administrateur" + (this.getLstAdmins().size() > 1 ? "s" : "") + " : " + "\n";
			for(Utilisateur admin : this.lstAdmins) {
				s += "\t\t\t" + admin.getNom() + " " + admin.getPrenom() + " " + admin.getAge() + " an" + (admin.getAge() > 1 ? "s" : "") + "\n";
			}
		}
		return s;
	}
}
