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
			nuevaRaiz = FXMLLoader.load(getClass().getResource("/application/pixelart/Tauler.fxml"));
	        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
	        Scene nuevaEscena = new Scene(nuevaRaiz);
	        stage.setScene(nuevaEscena);
	        stage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}



    }

}
