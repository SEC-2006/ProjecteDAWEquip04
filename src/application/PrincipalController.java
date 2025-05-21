package application;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Base64;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

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
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class PrincipalController {
	
	@FXML private GridPane principal;
	@FXML private TextField email;
	@FXML private PasswordField contrasenya;
	@FXML private Button registrarse;
	@FXML private Button iniciarSesio;
	
	//Objecte a compartir amb l'altra escena
	private Usuari u;

	public void setUsuari(Usuari u) {
		this.u = u;
	}

	public Usuari getUsuari() {
		return this.u;
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

			String urlBaseDades = "jdbc:mariadb://192.168.14.11:3306/ProjecteDAWEquip04";
			String usuariBaseDades = "root";
			String contrasenyaBaseDades = "root";
			Connection c = DriverManager.getConnection(urlBaseDades, usuariBaseDades, contrasenyaBaseDades);
			
			String sentenciaSelect = "SELECT * FROM Usuaris WHERE email=?";
			PreparedStatement sSelect = c.prepareStatement(sentenciaSelect);
			sSelect.setString(1, email.getText());
			ResultSet rSelect = sSelect.executeQuery();
			
			if(!rSelect.next())
			{
				Alert alerta = new Alert(AlertType.ERROR);
			    alerta.setTitle("Error");
			    alerta.setHeaderText("Dades incorrectes");
			    alerta.setContentText("El correu introduït no està registrat, introdueix un altre email o entra al formulari de registre per a crear un nou usuari");
			    alerta.showAndWait();
			}
			else
			{
				//verificar contrasenya
				String valorContrasenya = contrasenya.getText();
				int fortalesa = 65536;
				int longitudHash = 64*8;
				
				String valorContrasenyaHash = rSelect.getString("contrasenya");
		        String valorSalt = rSelect.getString("salt");

	            // Decodifica salt de Base64 a bytes
	            byte[] salt = Base64.getDecoder().decode(valorSalt);

	            // Calcula hash PBKDF2 con la contraseña introducida y el salt recuperado
	            KeySpec spec = new PBEKeySpec(valorContrasenya.toCharArray(), salt, fortalesa, longitudHash);
	            SecretKeyFactory factory;
				try {
					factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
					byte[] hash = factory.generateSecret(spec).getEncoded();
		            String valorContrasenyaHashCalculat = Base64.getEncoder().encodeToString(hash);
		            
		            if(valorContrasenyaHash.equals(valorContrasenyaHashCalculat))
		            {
		            	//redirigir a la seleccio de joc
						try {
							VBox registre = FXMLLoader.load(getClass().getResource("Seleccionar.fxml"));
							Scene escenaRegistre = new Scene(registre);
							Stage window = (Stage) ((Node) e.getSource()).getScene().getWindow();
							window.setUserData(new Usuari(email.getText(), rSelect.getString("Nom"), rSelect.getString("Cognoms")));
							window.setScene(escenaRegistre);
							window.setTitle("Selecciona un joc");
							window.show();
						} catch (IOException e1) {
							e1.printStackTrace();
						}
		            }
		            else
		            {
		            	
		            }
				} catch (NoSuchAlgorithmException | InvalidKeySpecException e1) {
					System.out.println(e1);
					
					Alert alerta = new Alert(AlertType.ERROR);
					alerta.setTitle("Error");
				    alerta.setHeaderText("Error");
				    alerta.setContentText("Ha ocurrit un error inesperat");
				    alerta.showAndWait();
				}
				
			}
		} catch (ClassNotFoundException | SQLException e1) {
			System.out.println(e1);
			
			Alert alerta = new Alert(AlertType.ERROR);
			alerta.setTitle("Error");
		    alerta.setHeaderText("Error");
		    alerta.setContentText("Ha ocurrit un error inesperat");
		    alerta.showAndWait();
		}
		

	}

}
