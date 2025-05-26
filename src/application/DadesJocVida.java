package application;

public class DadesJocVida {

	private String tamanyString;
	private int tamany;
	private int celules;
	private int totalCreades;
	private int totalMortes;
	private int totalGeneracions;
	
	public String getTamanyString() {
		return tamanyString;
	}
	public void setTamanyString(String tamanyString) {
		this.tamanyString = tamanyString;
	}
	public int getTamany() {
		return tamany;
	}
	public void setTamany(int tamany) {
		this.tamany = tamany;
	}
	public int getCelules() {
		return celules;
	}
	public void setCelules(int celules) {
		this.celules = celules;
	}
	public int getTotalCreades() {
		return totalCreades;
	}
	public void setTotalCreades(int totalCreades) {
		this.totalCreades = totalCreades;
	}
	public int getTotalMortes() {
		return totalMortes;
	}
	public void setTotalMortes(int totalMortes) {
		this.totalMortes = totalMortes;
	}
	public int getTotalGeneracions() {
		return totalGeneracions;
	}
	public void setTotalGeneracions(int totalGeneracions) {
		this.totalGeneracions = totalGeneracions;
	}
	
	public DadesJocVida(String tamanyString, int tamany, int celules, int totalCreades, int totalMortes,
			int totalGeneracions) {
		super();
		this.tamanyString = tamanyString;
		this.tamany = tamany;
		this.celules = celules;
		this.totalCreades = totalCreades;
		this.totalMortes = totalMortes;
		this.totalGeneracions = totalGeneracions;
	}
	
}
