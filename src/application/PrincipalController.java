package application;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class PrincipalController {
	
	@FXML private GridPane principal;
	@FXML private TextField email;
	@FXML private PasswordField contrasenya;
	@FXML private Button registrarse;
	@FXML private Button iniciarSesio;
	
	private Usuari user;

	public void setUsuari(Usuari user) {
		this.user = user;
	}

	public Usuari getUsuari() {
		return this.user;
	}
	
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
			
			String sentenciaSelect = "SELECT * FROM Usuaris WHERE email=? AND contrasenya=?";
			PreparedStatement sSelect = c.prepareStatement(sentenciaSelect);
			sSelect.setString(1, email.getText());
			sSelect.setString(2, contrasenya.getText());
			ResultSet rSelect = sSelect.executeQuery();
			
			int i = 0;
			while (rSelect.next()) i++;
			if(i==0)
			{
				Alert alerta = new Alert(AlertType.ERROR);
			    alerta.setTitle("Error");
			    alerta.setHeaderText("Dades incorrectes");
			    alerta.setContentText("Les dades que s'han introduït no coincideixen. Verifica l'usuari i la contrasenya i torna-ho a intentar");
			    alerta.showAndWait();
			}
			else
			{
				setUsuari(new Usuari(email.getText()));

				try {
					GridPane registre = FXMLLoader.load(getClass().getResource("Seleccionar.fxml"));
					Scene escenaRegistre = new Scene(registre);
					Stage window = (Stage) ((Node) e.getSource()).getScene().getWindow();
					window.setUserData(user);
					window.setScene(escenaRegistre);
					window.setTitle("Selecciona un joc");
					window.show();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			
			}
		} catch (ClassNotFoundException | SQLException e1) {
			System.out.println(e1);;
		}
		

	}

}
