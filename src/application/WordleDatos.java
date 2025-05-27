package application;

public class WordleDatos {


	private int id;
	private String email;
	private String nom;
	private String cognoms;
	private String paraula;
	private boolean guanyat;
	private int intents;
	
	
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
	public String getParaula() {
		return paraula;
	}
	public void setParaula(String paraula) {
		this.paraula = paraula;
	}
	public boolean isGuanyat() {
		return guanyat;
	}
	public void setGuanyat(boolean guanyat) {
		this.guanyat = guanyat;
	}
	public int getIntents() {
		return intents;
	}
	public void setIntents(int intents) {
		this.intents = intents;
	}
	
	
	public WordleDatos(int id, String email, String nom, String cognoms, String paraula, boolean guanyat, int intents) {
		this.id = id;
		this.email = email;
		this.nom = nom;
		this.cognoms = cognoms;
		this.paraula = paraula;
		this.guanyat = guanyat;
		this.intents = intents;
	}
}
