package application;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.imageio.ImageIO;

import javafx.embed.swing.SwingFXUtils;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.Button;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;

public class TaulerController extends Scene {

	private VBox principal, filas;
	private HBox[] cuadradosEnFila;
	private HBox colores;
	private Button btnGuardar;
	private Button btnGuardarBD;
	private Button btnCargarBD;

	private static final double SEPARACION = 1;

	public TaulerController(Parent contenedor, double ancho, double alto, int mida) {
		super(contenedor, ancho, alto);
		principal = (VBox) contenedor;
		principal.setSpacing(30);
		principal.setPadding(new Insets(50, 0, 0, 0));
		filas = new VBox(SEPARACION);
		cuadradosEnFila = new HBox[mida];
		colores = new HBox(5);
		colores.setAlignment(Pos.CENTER);
		btnGuardar = new Button("Guardar imagen");
		btnGuardarBD = new Button("Guardar en BD");
		btnCargarBD = new Button("Cargar BD");
		montarEscena();

	}

	private void montarEscena() {

		principal.setAlignment(Pos.TOP_CENTER);

		// Preparant quadrats de pintura
		for (int i = 0; i < cuadradosEnFila.length; i++) {

			cuadradosEnFila[i] = new HBox(SEPARACION);
			cuadradosEnFila[i].setAlignment(Pos.TOP_CENTER);

			for (int j = 0; j < cuadradosEnFila.length; j++) {
				cuadradosEnFila[i].getChildren().add(new CuadradoController());
			}

			filas.getChildren().add(cuadradosEnFila[i]);

		}

		prepararColores();

		btnGuardar.setOnAction(e -> guardarImagen());
		btnGuardarBD.setOnAction(e -> guardarEnBaseDeDades(1));
		btnCargarBD.setOnAction(e -> carregarDesDeBaseDeDades(1));

		HBox buttons= new HBox(5);
		buttons.setAlignment(Pos.TOP_CENTER);
		
		buttons.getChildren().addAll(btnGuardar, btnGuardarBD, btnCargarBD);

		principal.getChildren().addAll(filas, colores, buttons);

	}

	private void guardarImagen() {

		quitarBlancos();

		WritableImage imagen = filas.snapshot(new SnapshotParameters(), null);

		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Guardar imatge PixelArt");
		fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Imatge PNG", "*.png"));

		File carpetaImatges = new File(System.getProperty("user.home"), "Pictures");
		fileChooser.setInitialDirectory(carpetaImatges);
		fileChooser.setInitialFileName("pixelart.png");

		File fitxer = fileChooser.showSaveDialog(principal.getScene().getWindow());

		if (fitxer != null) {
			try {
				ImageIO.write(SwingFXUtils.fromFXImage(imagen, null), "png", fitxer);
				System.out.println("Imatge guardada en: " + fitxer.getAbsolutePath());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		restaurarBlanco();

	}

	public void guardarEnBaseDeDades(int idDibuix) {
		try {
			Class.forName("org.mariadb.jdbc.Driver");

			String urlBaseDades = "jdbc:mariadb://192.168.14.11:3306/ProjecteDAWEquip04";
			// String urlBaseDades = "jdbc:mariadb://localhost:3306/ProjecteDAWEquip04";
			String usuariBaseDades = "root";
			String contrasenyaBaseDades = "root";
			Connection conn = DriverManager.getConnection(urlBaseDades, usuariBaseDades, contrasenyaBaseDades);

			PreparedStatement deleteStmt = conn.prepareStatement("DELETE FROM pixelart WHERE id_dibuix = ?");
			deleteStmt.setInt(1, idDibuix);
			deleteStmt.executeUpdate();

			PreparedStatement insertStmt = conn
					.prepareStatement("INSERT INTO pixelart (id_dibuix, fila, columna, color) VALUES (?, ?, ?, ?)");

			for (int fila = 0; fila < cuadradosEnFila.length; fila++) {
				List<Node> caselles = cuadradosEnFila[fila].getChildren();
				for (int columna = 0; columna < caselles.size(); columna++) {
					CuadradoController c = (CuadradoController) caselles.get(columna);
					if (c.getColoreado()) {
						insertStmt.setInt(1, idDibuix);
						insertStmt.setInt(2, fila);
						insertStmt.setInt(3, columna);
						insertStmt.setString(4, c.getStyle());
						insertStmt.addBatch();
					}
				}
			}
			insertStmt.executeBatch();
			System.out.println("Dibuix guardat correctament.");
		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	public void carregarDesDeBaseDeDades(int idDibuix) {
		try {
			Class.forName("org.mariadb.jdbc.Driver");

			String urlBaseDades = "jdbc:mariadb://192.168.14.11:3306/ProjecteDAWEquip04";
			//String urlBaseDades = "jdbc:mariadb://localhost:3306/ProjecteDAWEquip04";
			String usuariBaseDades = "root";
			String contrasenyaBaseDades = "root";
			Connection conn = DriverManager.getConnection(urlBaseDades, usuariBaseDades, contrasenyaBaseDades);

			PreparedStatement selectStmt = conn
					.prepareStatement("SELECT fila, columna, color FROM pixelart WHERE id_dibuix = ?");
			selectStmt.setInt(1, idDibuix);
			ResultSet rs = selectStmt.executeQuery();

			while (rs.next()) {
				int fila = rs.getInt("fila");
				int columna = rs.getInt("columna");
				String color = rs.getString("color");

				CuadradoController c = (CuadradoController) cuadradosEnFila[fila].getChildren().get(columna);
				c.setStyle(color);
			}

			System.out.println("Dibuix carregat correctament.");
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
	}

	private void restaurarBlanco() {

		for (int i = 0; i < cuadradosEnFila.length; i++) {

			List fila = cuadradosEnFila[i].getChildren();

			for (int j = 0; j < fila.size(); j++) {

				CuadradoController c = (CuadradoController) fila.get(j);
				if (c.getColoreado())
					fila.set(j, new CuadradoController());

			}

		}

	}

	private void quitarBlancos() {

		for (int i = 0; i < cuadradosEnFila.length; i++) {

			List fila = cuadradosEnFila[i].getChildren();

			for (int j = 0; j < fila.size(); j++) {

				CuadradoController c = (CuadradoController) fila.get(j);
				if (c.getColoreado())
					c.blanquear();

			}

		}

	}

	private void prepararColores() {

		String[] nombresColores = { "white", "black", "red", "blue", "green", "yellow", "purple", "pink" };

		for (String s : nombresColores) {
			colores.getChildren().add(new CuadradoController("-fx-background-color: " + s + ";"));
		}
	}

}
