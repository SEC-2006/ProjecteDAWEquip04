package application;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class PrincipalController {
	
	@FXML private GridPane principal;
	@FXML private TextField usuari;
	@FXML private TextField contrasenya;
	@FXML private Button registrarse;

	
	public void obrirRegistrar(ActionEvent e) {
		try {
			GridPane registre = FXMLLoader.load(getClass().getResource("Registre.fxml"));
			Scene escenaRegistre = new Scene(registre);
			Stage window = (Stage) ((Node) e.getSource()).getScene().getWindow();
			window.setScene(escenaRegistre);
			window.setTitle("Registrar nou usuari");
			window.show();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}

}
