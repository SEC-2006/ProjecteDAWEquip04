package application;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class PrincipalController {
	
	@FXML private GridPane principal;
	@FXML private TextField usuari;
	@FXML private PasswordField contrasenya;
	@FXML private Button registrarse;
	@FXML private Button iniciarSesio;

	
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
	
	public void processarIniciarSesio(ActionEvent e) {
		try {
			Class.forName("org.mariadb.jdbc.Driver");

			String urlBaseDades = "jdbc:mariadb://localhost:3306/ProjecteDAWEquip04";
			String usuariBaseDades = "root";
			String contrasenyaBaseDades = "";
			Connection c = DriverManager.getConnection(urlBaseDades, usuariBaseDades, contrasenyaBaseDades);
			
			
		} catch (ClassNotFoundException | SQLException e1) {
			System.out.println(e1);;
		}
		

	}

}
