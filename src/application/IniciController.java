package application;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class IniciController {

    @FXML
    private Button btnacceptar;

    @FXML
    private ToggleGroup tamany;
    
    @FXML
    private Stage taulerStage;

    @FXML
    void paginaSeguent(ActionEvent event) {

    	
        Parent nuevaRaiz;
		try {
			TaulerController escena = new TaulerController(new VBox(), 500, 600);
			
	        Stage escenario = (Stage) ((Node) event.getSource()).getScene().getWindow();

			escenario.setTitle("Dibujando PixelArt");
			escenario.setScene(escena);
			escenario.show();
		} catch (Exception e) {
			e.printStackTrace();
		}



    }

}
