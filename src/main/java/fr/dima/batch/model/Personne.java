package fr.dima.batch.model;


import java.io.Serializable;

public class Personne implements Serializable{

	private static final long serialVersionUID = -6402068923614583448L;
	private String firstName;
    private String lastName;
    private String email;
    private String societe;
    private String remarques;
    private String mobile;
  
   
    
    public Personne() {
	}

	public Personne(String firstName, String lastName, String email, String societe, String remarques, String mobile) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.societe = societe;
		this.remarques = remarques;
		this.mobile = mobile;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
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

	public String getRemarques() {
		return remarques;
	}

	public void setRemarques(String remarques) {
		this.remarques = remarques;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	@Override
	public String toString() {
		return "Personne [firstName=" + firstName + ", lastName=" + lastName + ", email=" + email + ", societe="
				+ societe + ", remarques=" + remarques + ", mobile=" + mobile + "]";
	}

	
}
