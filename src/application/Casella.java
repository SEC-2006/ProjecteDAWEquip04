package application;

public class Casella {
	
    boolean teMina;
    boolean descoberta;
    boolean teBandera;
    int minesalrededor;

    public Casella() {
        teMina = false;
        descoberta = false;
        teBandera = false;
        minesalrededor = 0;
    }
}
