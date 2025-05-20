package application;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class PescaMines {

    // Botons del menú principal per triar la dificultat.
    @FXML private Button idfacil, idmitja, iddificil;

    
   //Genera un GridPane que es el quadrat que dependra la mida de la dificultat
    @FXML private GridPane quadratDfacil;
    @FXML private GridPane quadratDmitja;
    @FXML private GridPane quadratDdificil;

    @FXML
    public void initialize() {
      
      //Comprobar si cada element existeix o no per a que funcione i entrem al quadrat corresponent
        if (idfacil != null && idmitja != null && iddificil != null) {
        	
            idfacil.setOnAction(e -> accedirQuadratJoc("facil.fxml", "Pescamines dificultat Fàcil"));
            idmitja.setOnAction(e -> accedirQuadratJoc("mitja.fxml", "Pescamines dificultat Mitjà"));
            
            iddificil.setOnAction(e -> accedirQuadratJoc("dificil.fxml", "Pescamines dificultat Difícil"));
        }

        
        if (quadratDfacil != null) {
            crearQuadratJoc(quadratDfacil, 9, 9);
        }
        if (quadratDmitja != null) {
        	
            crearQuadratJoc(quadratDmitja, 16, 16);
        }
        if (quadratDdificil != null) {
        	
            crearQuadratJoc(quadratDdificil, 16, 30);
        }
    }

   //Accedir a la nova finestra amb el quadrat de la dificultat elegida carregant i mostrant finestra
    private void accedirQuadratJoc(String fxml, String titol) {
        try {
        	
            Parent root = FXMLLoader.load(getClass().getResource(fxml));
            Stage escena = new Stage();
            
            escena.setTitle(titol);
            escena.setScene(new Scene(root));
            escena.show();
            
        } catch (Exception e) {
        	
            e.printStackTrace();
        }
        
    }
    

   //Creacio dels botons que cada una representa casella del joc
    private void crearQuadratJoc(GridPane taula, int files, int columnes) {
        
    //Eliminar tot el que hi haja de antes
        taula.getChildren().clear();

        //espai en horizontal entre columnes
        taula.setHgap(1);
        
        //espai en vertical entre files
        taula.setVgap(1);
        
        taula.setStyle("-fx-background-color: #222;");
        
      
   //Per cada posició del quadrat crea un boto i se afegeix
        for (int fila = 0; fila < files; fila++) {
        	
            for (int columna = 0; columna < columnes; columna++) {
            	
                Button boto = new Button();
                boto.setPrefSize(35, 35); 
                
                boto.setStyle("-fx-background-color: #d3d3d3;");
                
                taula.add(boto, columna, fila); 
            }
        }
        
        
        
    }
}