package application;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class RankingController {

    @FXML private Label primer;
    @FXML private Label tempsPrimer;
    @FXML private Label segon;
    @FXML private Label tempsSegon;
    @FXML private Label tercer;
    @FXML private Label tempsTercer;

    //Mostrar els noms dels tres primers jugadores en el top 
    public void top3(String nom1, int temps1, String nom2, int temps2, String nom3, int temps3) {
        
    	primer.setText(nom1 != null ? nom1 : "-");
    	
        tempsPrimer.setText(formatTemps(temps1));
        
        segon.setText(nom2 != null ? nom2 : "-");
        
        tempsSegon.setText(formatTemps(temps2));
        
        tercer.setText(nom3 != null ? nom3 : "-");
        
        tempsTercer.setText(formatTemps(temps3));
    }


    
    private String formatTemps(int tempsEnSegons) {
    	
        if (tempsEnSegons <= 0) return "-";
        
        //Calcul de hores dividint segons totals entre 3600
        int hores = tempsEnSegons / 3600;
        
        //Calcul de minuts despres de llevar les hores
        int minuts = (tempsEnSegons % 3600) / 60;
        
        //Calcul dels segons despres de llevar hores y minuts
        int segons = tempsEnSegons % 60;
        
        
        //Construir el text que mostra el temps total
        StringBuilder sb = new StringBuilder("temps total: ");
        
        if (hores > 0) sb.append(hores).append("h ");
        
        if (minuts > 0) sb.append(minuts).append("m ");
        
        sb.append(segons).append("s");
        
        
        return sb.toString();
        
    }
}