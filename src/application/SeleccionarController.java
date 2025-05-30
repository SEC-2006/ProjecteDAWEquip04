package application;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.Window;

public class SeleccionarController implements Initializable{
	
	@FXML private GridPane principal;
	@FXML private Button btnPixelart;
	@FXML private Button btnJocVida;
	@FXML private Button btnPescaMines;
	@FXML private Button btnWordle;
	@FXML private Label benvinguda;

	private Usuari u;

	public void setUsuari(Usuari u) {
		this.u = u;
	}

	public Usuari getUsuari() {
		return this.u;
	}
	
	public void obrirPixelart(ActionEvent e) {
		try {
			
			ContadorFinestres contador = ContadorFinestres.getInstance();
		    if (contador.getPixelArt() >= 1) {
		    	Alert alerta = new Alert(AlertType.ERROR);
			    alerta.setTitle("Error");
			    alerta.setHeaderText("Finestra ja oberta");
			    alerta.setContentText("\"PixelArt\" ja està obert");
			    alerta.showAndWait();
		        return;
		    }
			AnchorPane root = (AnchorPane) FXMLLoader.load(getClass().getResource("Inici.fxml"));
			Stage escena = new Stage();
			escena.setTitle("PixelArt");
			escena.setScene(new Scene(root));
			escena.setUserData(this.u);
			escena.show();
	        contador.incrementPixelArt();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}
	
	public void obrirJocVida(ActionEvent e) {
		try {
			ContadorFinestres contador = ContadorFinestres.getInstance();
		    if (contador.getJocVida() >= 1) {
		    	Alert alerta = new Alert(AlertType.ERROR);
			    alerta.setTitle("Error");
			    alerta.setHeaderText("Finestra ja oberta");
			    alerta.setContentText("\"Joc de la Vida\" ja està obert");
			    alerta.showAndWait();
		        return;
		    }
			VBox root = (VBox) FXMLLoader.load(getClass().getResource("JocVidaInici.fxml"));
			Stage escena = new Stage();
			escena.setTitle("Joc de la Vida");
			escena.setScene(new Scene(root));
			escena.show();
			contador.incrementJocVida();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}
	
	public void obrirPescaMines(ActionEvent e) {
		try {
			ContadorFinestres contador = ContadorFinestres.getInstance();
		    if (contador.getPescaMines() >= 1) {
		    	Alert alerta = new Alert(AlertType.ERROR);
			    alerta.setTitle("Error");
			    alerta.setHeaderText("Finestra ja oberta");
			    alerta.setContentText("\"Pesca Mines\" ja està obert");
			    alerta.showAndWait();
		        return;
		    }
			VBox root = (VBox) FXMLLoader.load(getClass().getResource("PescaMines.fxml"));
			Stage escena = new Stage();
			escena.setTitle("PescaMines");
			escena.setUserData(this.u);
			escena.setScene(new Scene(root));
			escena.show();
			contador.incrementPescaMines();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}

	public void obrirWordle(ActionEvent e) {
		try {
			ContadorFinestres contador = ContadorFinestres.getInstance();
		    if (contador.getWordle() >= 1) {
		    	Alert alerta = new Alert(AlertType.ERROR);
			    alerta.setTitle("Error");
			    alerta.setHeaderText("Finestra ja oberta");
			    alerta.setContentText("\"Wordle\" ja està obert");
			    alerta.showAndWait();
		        return;
		    }
			Pane root = (Pane) FXMLLoader.load(getClass().getResource("Wordle.fxml"));
			Stage escena = new Stage();
			escena.setTitle("Wordle");
			escena.setUserData(this.u);
			escena.setScene(new Scene(root));
			escena.show();
			contador.incrementWordle();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		Platform.runLater(()->{
			Window window = (Stage) principal.getScene().getWindow();
			this.u = (Usuari) window.getUserData();
			benvinguda.setText("Benvingut "+this.u.getNom()+" "+this.u.getCognoms()+"!");
		});
	}

}
