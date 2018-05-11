package fr.dima.batch.model;


import java.io.Serializable;

public class Personne implements Serializable{

	private static final long serialVersionUID = -6402068923614583448L;
	private String prenom;
    private String nom;
    private String email;
    private String societe;
    private String tel;
  
   
    
    public Personne() {
	}

	public Personne(String prenom, String nom, String email, String societe, String tel) {
		super();
		this.prenom = prenom;
		this.nom = nom;
		this.email = email;
		this.societe = societe;
		this.tel = tel;
	}



	public String getPrenom() {
		return prenom;
	}

	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSociete() {
		return societe;
	}

	public void setSociete(String societe) {
		this.societe = societe;
	}


	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	@Override
	public String toString() {
		return "Personne [prenom=" + prenom + ", nom=" + nom + ", email=" + email + ", societe=" + societe + ", tel="
				+ tel + "]";
	}

	

	
}
