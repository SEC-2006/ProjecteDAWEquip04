package application;

public class Usuari {

	private int id;
	private String email;
	private String nom;
	private String cognoms;
	
	public Usuari(int id, String email, String nom, String cognoms) {
		this.id = id;
		this.email = email;
		this.nom = nom;
		this.cognoms = cognoms;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getCognoms() {
		return cognoms;
	}

	public void setCognoms(String cognoms) {
		this.cognoms = cognoms;
	}
	
	
}
