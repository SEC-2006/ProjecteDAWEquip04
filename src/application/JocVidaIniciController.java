package application;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.Window;

public class JocVidaIniciController {
	
	@FXML VBox principal;
	@FXML Button xicotet;
	@FXML Button mitja;
	@FXML Button gran;
	
	public void obrirXicotet(ActionEvent e) {
		try {
			ContadorFinestres contador = ContadorFinestres.getInstance();
			VBox jocvida = FXMLLoader.load(getClass().getResource("JocVida.fxml"));
			Scene escenaJocVida = new Scene(jocvida);
			Stage window = (Stage) ((Node) e.getSource()).getScene().getWindow();
			window.setScene(escenaJocVida);
			window.setUserData("S");
			window.setTitle("Joc de la Vida");
			window.setOnCloseRequest(event -> contador.decrementJocVida());
			window.show();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}
	
	public void obrirMitja(ActionEvent e) {
		try {
			ContadorFinestres contador = ContadorFinestres.getInstance();
			VBox jocvida = FXMLLoader.load(getClass().getResource("JocVida.fxml"));
			Scene escenaJocVida = new Scene(jocvida);
			Stage window = (Stage) ((Node) e.getSource()).getScene().getWindow();
			window.setScene(escenaJocVida);
			window.setUserData("M");
			window.setTitle("Joc de la Vida");
			window.setOnCloseRequest(event -> contador.decrementJocVida());
			window.show();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}
	
	public void obrirGran(ActionEvent e) {
		try {
			ContadorFinestres contador = ContadorFinestres.getInstance();
			VBox jocvida = FXMLLoader.load(getClass().getResource("JocVida.fxml"));
			Scene escenaJocVida = new Scene(jocvida);
			Stage window = (Stage) ((Node) e.getSource()).getScene().getWindow();
			window.setScene(escenaJocVida);
			window.setUserData("L");
			window.setTitle("Joc de la Vida");
			window.setOnCloseRequest(event -> contador.decrementJocVida());
			window.show();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}

}
