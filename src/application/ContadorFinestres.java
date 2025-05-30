package application;

public class ContadorFinestres {
    private static ContadorFinestres instance;
    private int pixelArt;
    private int jocVida;
    private int pescaMines;
    private int wordle;

    private ContadorFinestres() {}

    public static ContadorFinestres getInstance() {
        if (instance == null) {
            instance = new ContadorFinestres();
        }
        return instance;
    }

    public int getPixelArt() { return pixelArt; }
    public void incrementPixelArt() { pixelArt++; }
    public void decrementPixelArt() { if (pixelArt > 0) pixelArt--; }

    public int getJocVida() { return jocVida; }
    public void incrementJocVida() { jocVida++; }
    public void decrementJocVida() { if (jocVida > 0) jocVida--; }

    public int getPescaMines() { return pescaMines; }
    public void incrementPescaMines() { pescaMines++; }
    public void decrementPescaMines() { if (pescaMines > 0) pescaMines--; }

    public int getWordle() { return wordle; }
    public void incrementWordle() { wordle++; }
    public void decrementWordle() { if (wordle > 0) wordle--; }
}
