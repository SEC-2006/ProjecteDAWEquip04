package application;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class RegistreController {
	
	@FXML private GridPane registre;
	@FXML private PasswordField contrasenya;
	@FXML private TextField nom;
	@FXML private TextField cognoms;
	@FXML private ImageView imatgeSeleccionada;
	@FXML private Button imatgeBtn;
	@FXML private TextField poblacio;
	@FXML private TextField email;
	@FXML private Button registrarse;
	@FXML private Button iniciarSesio;
	private File imgFile;

	public void seleccionarImatge(ActionEvent e) {
		FileChooser fileChooser = new FileChooser();
	    fileChooser.setTitle("Seleccionar imagen");
	    fileChooser.getExtensionFilters().addAll(
	        new FileChooser.ExtensionFilter("Imatges (.png .jpg .jpeg)", "*.png", "*.jpg", "*.jpeg")
	    );
	    imgFile = fileChooser.showOpenDialog(imatgeBtn.getScene().getWindow());
	    if (imgFile != null) {
	        Image imatge = new Image(imgFile.toURI().toString());
	        imatgeSeleccionada.setImage(imatge);
	    }
	}
	
	public void obrirPrincipal(ActionEvent e) {
		try {
			GridPane principal = FXMLLoader.load(getClass().getResource("Principal.fxml"));
			Scene escenaPrincipal = new Scene(principal);
			Stage window = (Stage) ((Node) e.getSource()).getScene().getWindow();
			window.setScene(escenaPrincipal);
			window.setTitle("Iniciar sesió");
			window.show();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}
	
	public void processarRegistre(ActionEvent e) {
		try {
			Class.forName("org.mariadb.jdbc.Driver");

			String urlBaseDades = "jdbc:mariadb://192.168.14.11:3306/ProjecteDAWEquip04?useSSL=false";
			String usuariBaseDades = "root";
			String contrasenyaBaseDades = "root";
			Connection c = DriverManager.getConnection(urlBaseDades, usuariBaseDades, contrasenyaBaseDades);
			//Confirmar que no existeix un usuari que tinga el mateix email
			String sentenciaSelect = "SELECT * FROM Usuaris WHERE email=?";
			PreparedStatement sSelect = c.prepareStatement(sentenciaSelect);
			sSelect.setString(1, email.getText());
			ResultSet rSelect = sSelect.executeQuery();
			sSelect.close();
			int i = 0;
			while (rSelect.next()) i++;
			if(i==0) {
				
				String errors = "";
				
				String valorContrasenya = contrasenya.getText();
				String valorNom = nom.getText();
				String valorCognoms = cognoms.getText();
				Image  valorImatgeSeleccionada = imatgeSeleccionada.getImage();
				String valorPoblacio = poblacio.getText();
				String valorEmail = email.getText();
				
				if (valorContrasenya.length()<8) errors += "La contrasenya és molt curta\n";
				else if (valorContrasenya.length()>255) errors += "S'ha introduït una contrasenya massa llarga";
				if (valorNom.length()==0) errors += "No s'ha introduït un nom\n";
				else if (valorNom.length()>100) errors += "S'ha introduït un nom massa llarg";
				if (valorCognoms.length()==0) errors += "No s'ha introduït un/s cognom/s\n";
				else if (valorCognoms.length()>150) errors += "S'ha/n introduït un/s cognom/s massa llarg/s";
				if (valorImatgeSeleccionada == null) errors += "No s'ha introduït una imatge\n";
				if (valorPoblacio.length()==0) errors += "No s'ha introduït una població\n";
				else if (!valorPoblacio.matches("^[A-za-z ÀÈÌÒÙÁÉÍÓÚÑàèìòùáéíóúñ]+$")) errors += "No s'ha introduït una població vàlida\n";
				else if (valorPoblacio.length()>255) errors += "S'ha introduït una població massa llarga";
				if (!valorEmail.matches("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")) errors += "No s'ha introduït un email vàlid\n";
				else if (valorEmail.length()>255) errors += "S'ha introduït un email massa llarg";

				if (errors.equals(""))
				{
					//encriptar contrasenya
					int longitudSalt = 16;
					SecureRandom random = new SecureRandom();
					byte[] salt = new byte[longitudSalt];
					random.nextBytes(salt);

					int fortalesa = 65536;
					int longitudHash = 64*8;

					KeySpec spec = new PBEKeySpec(valorContrasenya.toCharArray(), salt, fortalesa, longitudHash);
					SecretKeyFactory factory;
					
					try {
						factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
						byte[] hash = factory.generateSecret(spec).getEncoded();
						String valorSalt = Base64.getEncoder().encodeToString(salt);
						String valorContrasenyaHash = Base64.getEncoder().encodeToString(hash);
						
						//insertar dades
						String sentencia = "INSERT INTO Usuaris(nom, cognoms, poblacio, email, contrasenya, salt, imatge) "
								+ "VALUES (?, ?, ?, ?, ?, ?, ?);";
						PreparedStatement s = c.prepareStatement(sentencia);
						s.setString(1, valorNom);
						s.setString(2, valorCognoms);
						s.setString(3, valorPoblacio);
						s.setString(4, valorEmail);
						s.setString(5, valorContrasenyaHash);
						s.setString(6, valorSalt);
						try (FileInputStream inputStream = new FileInputStream(imgFile.getAbsolutePath())) {
			                s.setBinaryStream(7, inputStream, inputStream.available());
			                s.executeUpdate();
			            } catch (IOException e1) {
			                e1.printStackTrace();
			            }
						ResultSet r = s.executeQuery();						
					} catch (NoSuchAlgorithmException | InvalidKeySpecException e1) {
						System.out.println(e1);
						
						Alert alerta = new Alert(AlertType.ERROR);
						alerta.setTitle("Error");
					    alerta.setHeaderText("Error");
					    alerta.setContentText("Ha ocurrit un error inesperat");
					    alerta.showAndWait();
					} catch (SQLException e2){
						System.out.println(e2);
						/* nota: esta separat este error dels altres perquè aunque 
						   l'usuari se registra correctament dona "Socket error" */
					} finally {
						Alert alerta = new Alert(AlertType.INFORMATION);
						alerta.setTitle("Registre");
					    alerta.setHeaderText("Registre completat");
					    alerta.setContentText("L'usuari amb l'email "+valorEmail+" s'ha registrat correctament");
					    alerta.showAndWait();
						
						obrirPrincipal(e);
					}				
				}
				else
				{
					Alert alerta = new Alert(AlertType.ERROR);
					alerta.setTitle("Error");
				    alerta.setHeaderText("Error en els camps de registre");
				    alerta.setContentText(errors);
				    alerta.showAndWait();
				}
				
			} else {
				Alert alerta = new Alert(AlertType.ERROR);
			    alerta.setTitle("Error");
			    alerta.setHeaderText("Error en el camp \"Email\"");
			    alerta.setContentText("Aquest email ja està registrat");
			    alerta.showAndWait();
			}

		} catch (ClassNotFoundException | SQLException e1) {
			System.out.println(e1);;
		}
	}
}
