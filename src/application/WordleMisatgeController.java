package application;

import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.Window;

public class WordleMisatgeController implements Initializable {

	@FXML
	private VBox VBoxRoot;
	@FXML
	private Label misatgePrincipal;
	@FXML
	private Label paraula;
	@FXML
	private Label NumIntentos;

	private WordleDatos wd;

	public void setWordleDatos(WordleDatos wd) {
		this.wd = wd;
	}

	public WordleDatos getWordleDatos() {
		return this.wd;
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		Platform.runLater(() -> {
			Window window = (Stage) VBoxRoot.getScene().getWindow();
			this.wd = (WordleDatos) window.getUserData();

			String felicitatsONo = "Has pergut ";
			String estil = "-fx-text-fill: #ff0000;";
			if (this.wd.isGuanyat()) {
				felicitatsONo = "Felicitats ";
				estil = "-fx-text-fill: #28a745;";
			}
			misatgePrincipal.setStyle(estil);
			misatgePrincipal.setText(felicitatsONo + this.wd.getNom() + " " + this.wd.getCognoms());
			paraula.setText("Paraula: \"" + this.wd.getParaula() + "\"");
			NumIntentos.setText("Nº Intentos: " + consultaIntentos());
		});

	}

	private int consultaIntentos() {
		try {
			Class.forName("org.mariadb.jdbc.Driver");
			String urlBaseDades = "jdbc:mariadb://192.168.14.11:3306/ProjecteDAWEquip04";
			String usuari = "root";
			String contrasenya = "root";
			Connection c = DriverManager.getConnection(urlBaseDades, usuari, contrasenya);

			String sentencia = "SELECT victoria1, victoria2, victoria3, victoria4, victoria5, victoria6 FROM PartidesWordle WHERE id = 999";
			PreparedStatement s = c.prepareStatement(sentencia);
			ResultSet rs = s.executeQuery();
			int contador = 0;
			if (rs.next()) {
				contador += rs.getInt("victoria1");
				contador += rs.getInt("victoria2");
				contador += rs.getInt("victoria3");
				contador += rs.getInt("victoria4");
				contador += rs.getInt("victoria5");
				contador += rs.getInt("victoria6");
			}
			return contador;

		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}

}