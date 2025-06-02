package application;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.Scanner;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.Window;

public class WordleController implements Initializable {

	// gridpane
	@FXML
	private GridPane panellJoc;
	@FXML
	private Pane paneRoot;

	// botons -------------------------
	@FXML
	private Button BtnQ;
	@FXML
	private Button BtnW;
	@FXML
	private Button BtnE;
	@FXML
	private Button BtnR;
	@FXML
	private Button BtnT;
	@FXML
	private Button BtnY;
	@FXML
	private Button BtnU;
	@FXML
	private Button BtnI;
	@FXML
	private Button BtnO;
	@FXML
	private Button BtnP;
	// --
	@FXML
	private Button BtnA;
	@FXML
	private Button BtnS;
	@FXML
	private Button BtnD;
	@FXML
	private Button BtnF;
	@FXML
	private Button BtnG;
	@FXML
	private Button BtnH;
	@FXML
	private Button BtnJ;
	@FXML
	private Button BtnK;
	@FXML
	private Button BtnL;
	@FXML
	private Button BtnÑ;
	// --
	@FXML
	private Button BtnZ;
	@FXML
	private Button BtnX;
	@FXML
	private Button BtnC;
	@FXML
	private Button BtnV;
	@FXML
	private Button BtnB;
	@FXML
	private Button BtnN;
	@FXML
	private Button BtnM;
	// -- especials --
	@FXML
	private Button BtnEnviar;
	@FXML
	private Button BtnEliminar;

	// posicions----------------------
	// fila 0
	@FXML
	private HBox Pos00;
	@FXML
	private HBox Pos10;
	@FXML
	private HBox Pos20;
	@FXML
	private HBox Pos30;
	@FXML
	private HBox Pos40;
	// fila 1
	@FXML
	private HBox Pos01;
	@FXML
	private HBox Pos11;
	@FXML
	private HBox Pos21;
	@FXML
	private HBox Pos31;
	@FXML
	private HBox Pos41;
	// fila 2
	@FXML
	private HBox Pos02;
	@FXML
	private HBox Pos12;
	@FXML
	private HBox Pos22;
	@FXML
	private HBox Pos32;
	@FXML
	private HBox Pos42;
	// fila 3
	@FXML
	private HBox Pos03;
	@FXML
	private HBox Pos13;
	@FXML
	private HBox Pos23;
	@FXML
	private HBox Pos33;
	@FXML
	private HBox Pos43;
	// fila 4
	@FXML
	private HBox Pos04;
	@FXML
	private HBox Pos14;
	@FXML
	private HBox Pos24;
	@FXML
	private HBox Pos34;
	@FXML
	private HBox Pos44;
	// fila 5
	@FXML
	private HBox Pos05;
	@FXML
	private HBox Pos15;
	@FXML
	private HBox Pos25;
	@FXML
	private HBox Pos35;
	@FXML
	private HBox Pos45;

	private boolean guanyat = false;
	public final static String RUTA_PARAULES = "WordleParaules.txt";
	public final static int LONG_H = 5;
	public final static int LONG_V = 6;
	private char paraulaActual[] = { '-', '-', '-', '-', '-' };
	private int filaActual = 0;
	private int columnaActual = 0;
	private String paraulaAdivinar = "";
	private HBox[][] posicions = new HBox[LONG_V][LONG_H];
	private Map<Character, String> prioritatsColors = new HashMap<>();
	List<String> llistatParaulesYaEstan = new ArrayList<>();

	private Usuari u;

	public void setUsuari(Usuari u) {
		this.u = u;
	}

	public Usuari getUsuari() {
		return this.u;
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

		Platform.runLater(() -> {
			paneRoot.requestFocus();
			Window window = (Stage) paneRoot.getScene().getWindow();
			this.u = (Usuari) window.getUserData();
			File fitxerParaules = new File(RUTA_PARAULES);
			List<String> llistatParaules = new ArrayList<>();
			Random aleatori = new Random();

			guardarParaules(fitxerParaules, llistatParaules);

			boolean paraulaGastada = true;
			while (paraulaGastada) {
				paraulaAdivinar = llistatParaules.get(aleatori.nextInt(llistatParaules.size()));
				if (llistatParaulesYaEstan.contains(paraulaAdivinar)) {
				} else {
					paraulaGastada = false;
				}
			}
			System.out.println(paraulaAdivinar);

		});

		// posibles posicions per a la graella
		// fila 0
		posicions[0][0] = Pos00;
		posicions[0][1] = Pos10;
		posicions[0][2] = Pos20;
		posicions[0][3] = Pos30;
		posicions[0][4] = Pos40;
		// fila 1
		posicions[1][0] = Pos01;
		posicions[1][1] = Pos11;
		posicions[1][2] = Pos21;
		posicions[1][3] = Pos31;
		posicions[1][4] = Pos41;
		// fila 2
		posicions[2][0] = Pos02;
		posicions[2][1] = Pos12;
		posicions[2][2] = Pos22;
		posicions[2][3] = Pos32;
		posicions[2][4] = Pos42;
		// fila 3
		posicions[3][0] = Pos03;
		posicions[3][1] = Pos13;
		posicions[3][2] = Pos23;
		posicions[3][3] = Pos33;
		posicions[3][4] = Pos43;
		// fila 4
		posicions[4][0] = Pos04;
		posicions[4][1] = Pos14;
		posicions[4][2] = Pos24;
		posicions[4][3] = Pos34;
		posicions[4][4] = Pos44;
		// fila 5
		posicions[5][0] = Pos05;
		posicions[5][1] = Pos15;
		posicions[5][2] = Pos25;
		posicions[5][3] = Pos35;
		posicions[5][4] = Pos45;

	}

	// teclat
	// --------------------------------------------------------------------------------------------------
	public void detectaTecla(KeyEvent ke) {
		try {
			if (ke.getCode() == KeyCode.BACK_SPACE) {
				eliminar();
			} else if (ke.getCode() == KeyCode.ENTER) {
				enviar();
			} else {
				char aux = ke.getText().charAt(0);
				char auxMayus = Character.toUpperCase(aux);
				if ((auxMayus >= 'A' && auxMayus <= 'Z') || auxMayus == 'Ñ') {
					añadirLletra(auxMayus);
				}
			}

		} catch (Exception e2) {
		}
	}

	// obrir pantalla de guanyar
	// ------------------------------------------------------------------------------------------
	public void obrirMisatge() {
		ButtonType boto01 = new ButtonType("Tornar a intentar");
		ButtonType boto02 = new ButtonType("Tornar al menù");
		Alert alert = new Alert(AlertType.NONE, "Missatge Wordle", boto01, boto02);
		alert.setTitle("Missatge");
		alert.setHeaderText("Resultat de: " + this.u.getNom() + " " + this.u.getCognoms());
		String missatge = "Paraula: " + paraulaAdivinar + "\n\nIntentos: " + (filaActual + 1)
				+ "\n\nPuntuacio general: " + consultaIntentos();
		alert.setContentText(missatge);

		alert.setResizable(true);
		alert.getDialogPane().setPrefSize(400, 300);
		alert.getDialogPane().setStyle("-fx-background-color: #FF9999; -fx-font-size: 14px; -fx-font-weight: bold;");
		if (guanyat) {
			alert.getDialogPane()
					.setStyle("-fx-background-color: #99FF99; -fx-font-size: 14px; -fx-font-weight: bold;");
		}

		Node header = alert.getDialogPane().lookup(".header-panel");
		if (header != null) {
			if (guanyat) {
				header.setStyle("-fx-background-color: #66CC66;");
			} else {
				header.setStyle("-fx-background-color: #FF6666;");
			}
		}

		Optional<ButtonType> resultat = alert.showAndWait();
		ContadorFinestres contador = ContadorFinestres.getInstance();
		if (resultat.get() == boto01) {
			try {
				FXMLLoader loader = new FXMLLoader(getClass().getResource("Wordle.fxml"));
				Pane root = loader.load();
				Stage stageActual = (Stage) paneRoot.getScene().getWindow();
				stageActual.setScene(new Scene(root));
				stageActual.setOnCloseRequest(event -> contador.decrementWordle());
				stageActual.setTitle("Wordle");
				stageActual.setUserData(this.u);

			} catch (IOException e1) {
				e1.printStackTrace();
			}
		} else if (resultat.get() == boto02) {
			contador.decrementWordle();
			Stage finestraActual = (Stage) paneRoot.getScene().getWindow();
			finestraActual.close();
		}

	}

	// traurer la paraula aleatoria
	// ------------------------------------------------------------------------------------------
	private void guardarParaules(File fitxerParaules, List<String> llistatParaules) {

		try {
			Class.forName("org.mariadb.jdbc.Driver");
			String urlBaseDades = "jdbc:mariadb://192.168.14.11:3306/ProjecteDAWEquip04";
			String usuari = "root";
			String contrasenya = "root";
			Connection c = DriverManager.getConnection(urlBaseDades, usuari, contrasenya);

			String sentencia = "SELECT paraula FROM ParaulesWordle WHERE id = " + u.getId();
			PreparedStatement s = c.prepareStatement(sentencia);
			ResultSet rs = s.executeQuery();

			while (rs.next()) {
				String paraula = rs.getString("paraula");
				llistatParaulesYaEstan.add(paraula);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			Scanner lectorFitxer = new Scanner(fitxerParaules);
			while (lectorFitxer.hasNextLine()) {
				llistatParaules.add(lectorFitxer.nextLine().trim());
			}
			lectorFitxer.close();

			if (llistatParaules.size() <= llistatParaulesYaEstan.size()) {
				Class.forName("org.mariadb.jdbc.Driver");
				String urlBaseDades = "jdbc:mariadb://192.168.14.11:3306/ProjecteDAWEquip04";
				String usuari = "root";
				String contrasenya = "root";
				Connection c = DriverManager.getConnection(urlBaseDades, usuari, contrasenya);
				String sentencia2 = "DELETE FROM ParaulesWordle WHERE id = " + u.getId();
				PreparedStatement s = c.prepareStatement(sentencia2);
				s.executeUpdate();
				System.out.println("eliminat");
			}

		} catch (FileNotFoundException | SQLException | ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	// traurer els intentos totals (puntuacio)
	// ------------------------------------------------------------------------------------------
	private int consultaIntentos() {
		try {
			Class.forName("org.mariadb.jdbc.Driver");
			String urlBaseDades = "jdbc:mariadb://192.168.14.11:3306/ProjecteDAWEquip04";
			String usuari = "root";
			String contrasenya = "root";
			Connection c = DriverManager.getConnection(urlBaseDades, usuari, contrasenya);

			String sentencia = "SELECT victoria1, victoria2, victoria3, victoria4, victoria5, victoria6 FROM PartidesWordle WHERE id = "
					+ u.getId();
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

	// restableixer array
	// ----------------------------------------------------------------------------------------------------
	private void restableixerArray() {
		for (int i = 0; i < LONG_H; i++) {
			paraulaActual[i] = '-';
		}
	}

	// comprobar si estan y ficar els colors a la graella
	// ---------------------------------------------------------------------
	private void comprobarSiEstan(String paraulaComprovar) {
		Map<Character, Integer> contador = new HashMap<>();
		String estil = "-fx-border-color: black; -fx-border-width: 3; -fx-border-radius: 5; -fx-background-radius: 7;";
		String estilBotons = "-fx-border-color: black; -fx-border-width: 1; -fx-border-radius: 2; -fx-background-radius: 3;";
		String verd = "-fx-background-color: #6aaa64;";
		String groc = "-fx-background-color: #c9b458;";
		String gris = "-fx-background-color: #787c7e;";

		boolean yaComprobat[] = new boolean[LONG_H];

		// contar paraules
		for (int i = 0; i < LONG_H; i++) {
			char lletraContador = Character.toUpperCase(paraulaAdivinar.charAt(i));
			contador.put(lletraContador, contador.getOrDefault(lletraContador, 0) + 1);
		}

		// vert
		for (int i = 0; i < LONG_H; i++) {
			char lletraComprovar = Character.toUpperCase(paraulaComprovar.charAt(i));
			char lletraContador = Character.toUpperCase(paraulaAdivinar.charAt(i));
			if (lletraComprovar == lletraContador) {
				HBox casilla = obtindrerPosicio(filaActual, i);
				casilla.setStyle(estil + verd);
				contador.put(lletraComprovar, contador.get(lletraComprovar) - 1);
				yaComprobat[i] = true;
				colorsBotons(lletraComprovar, estilBotons + verd, "verd");
			}
		}

		// groc
		for (int i = 0; i < LONG_H; i++) {
			if (yaComprobat[i] == false) {
				char lletraComprovar = Character.toUpperCase(paraulaComprovar.charAt(i));
				if (contador.getOrDefault(lletraComprovar, 0) > 0) {
					yaComprobat[i] = true;
					HBox casilla = obtindrerPosicio(filaActual, i);
					casilla.setStyle(estil + groc);
					contador.put(lletraComprovar, contador.get(lletraComprovar) - 1);
					colorsBotons(lletraComprovar, estilBotons + groc, "groc");
				}
			}
		}

		// gris
		for (int i = 0; i < LONG_H; i++) {
			if (yaComprobat[i] == false) {
				char lletraComprovar = Character.toUpperCase(paraulaComprovar.charAt(i));
				HBox casilla = obtindrerPosicio(filaActual, i);
				casilla.setStyle(estil + gris);
				colorsBotons(lletraComprovar, estilBotons + gris, "gris");
			}
		}
	}

	// ficar color als botons
	// ------------------------------------------------------------------------------------------
	private void colorsBotons(char lletraComprovar, String estil, String opcioColor) {
		int prioritatNova = obtindrePrioritat(opcioColor);
		String colorActual = prioritatsColors.get(lletraComprovar);
		int prioritatActual = obtindrePrioritat(colorActual);

		if (prioritatNova > prioritatActual) {
			prioritatsColors.put(lletraComprovar, opcioColor);

			switch (lletraComprovar) {
			case 'Q': {

				BtnQ.setStyle(estil);
				break;
			}
			case 'W': {
				BtnW.setStyle(estil);
				break;
			}
			case 'E': {
				BtnE.setStyle(estil);
				break;
			}
			case 'R': {
				BtnR.setStyle(estil);
				break;
			}
			case 'T': {
				BtnT.setStyle(estil);
				break;
			}
			case 'Y': {
				BtnY.setStyle(estil);
				break;
			}
			case 'U': {
				BtnU.setStyle(estil);
				break;
			}
			case 'I': {
				BtnI.setStyle(estil);
				break;
			}
			case 'O': {
				BtnO.setStyle(estil);
				break;
			}
			case 'P': {
				BtnP.setStyle(estil);
				break;
			}
			case 'A': {
				BtnA.setStyle(estil);
				break;
			}
			case 'S': {
				BtnS.setStyle(estil);
				break;
			}
			case 'D': {
				BtnD.setStyle(estil);
				break;
			}
			case 'F': {
				BtnF.setStyle(estil);
				break;
			}
			case 'G': {
				BtnG.setStyle(estil);
				break;
			}
			case 'H': {
				BtnH.setStyle(estil);
				break;
			}
			case 'J': {
				BtnJ.setStyle(estil);
				break;
			}
			case 'K': {
				BtnK.setStyle(estil);
				break;
			}
			case 'L': {
				BtnL.setStyle(estil);
				break;
			}
			case 'Ñ': {
				BtnÑ.setStyle(estil);
				break;
			}
			case 'Z': {
				BtnZ.setStyle(estil);
				break;
			}
			case 'X': {
				BtnX.setStyle(estil);
				break;
			}
			case 'C': {
				BtnC.setStyle(estil);
				break;
			}
			case 'V': {
				BtnV.setStyle(estil);
				break;
			}
			case 'B': {
				BtnB.setStyle(estil);
				break;
			}
			case 'N': {
				BtnN.setStyle(estil);
				break;
			}
			case 'M': {
				BtnM.setStyle(estil);
				break;
			}
			default:

			}
		}

	}

	// per a que els ya encertats no es solapen
	// ------------------------------------------------------------------------------------------
	private int obtindrePrioritat(String opcioColor) {
		if (opcioColor == null) {
			return 0;
		} else if (opcioColor == "gris") {
			return 1;
		} else if (opcioColor == "groc") {
			return 2;
		} else if (opcioColor == "verd") {
			return 3;
		} else {
			return 0;
		}
	}

	// añadir lletra
	// ------------------------------------------------------------------------------------------
	private void añadirLletra(char lletra) {
		if (columnaActual < LONG_H && columnaActual >= 0) {
			HBox casilla = obtindrerPosicio(filaActual, columnaActual);
			casilla.getChildren().clear();
			casilla.getChildren().add(new Text(String.valueOf(lletra)));
			paraulaActual[columnaActual] = (char) lletra;
			columnaActual++;
		}
	}

	// obtindrer posicio
	// ---------------------------------------------------------------------------------------
	private HBox obtindrerPosicio(int fila, int columna) {
		if (fila >= 0 && fila < LONG_V && columna >= 0 && columna < LONG_H) {
			return posicions[fila][columna];
		}
		return null;
	}

	// funcio per a eliminar
	// ---------------------------------------------------------------------------------------
	private void eliminar() {
		if (columnaActual <= LONG_H && columnaActual > 0) {
			columnaActual--;
			HBox casilla = obtindrerPosicio(filaActual, columnaActual);
			casilla.getChildren().clear();
		} else if (columnaActual == 0) {
			HBox casilla = obtindrerPosicio(filaActual, columnaActual);
			casilla.getChildren().clear();
		}
		paraulaActual[columnaActual] = '-';
	}

	// funcio per a enviar resposta
	// ---------------------------------------------------------------------------------------
	private void enviar() {
		int contador = 0;
		String paraulaComprovar = "";
		for (int i = 0; i < LONG_H; i++) {
			if (paraulaActual[i] != '-') {
				contador++;
			}
		}
		if (contador == LONG_H) {
			for (int i = 0; i < LONG_H; i++) {
				paraulaComprovar += paraulaActual[i] + "";
			}
			if (paraulaComprovar.toUpperCase().equals(paraulaAdivinar.toUpperCase())) {
				comprobarSiEstan(paraulaComprovar);
				System.out.println("Has guanyat");
				guanyat = true;
				guardarEnBBDD(guanyat);
				obrirMisatge();
			} else {
				comprobarSiEstan(paraulaComprovar);

				restableixerArray();
				if (filaActual < LONG_H) {
					columnaActual = 0;
					filaActual++;
				} else {
					guardarEnBBDD(guanyat);
					obrirMisatge();
					System.out.println("Has pergut");

				}
			}
		}
	}

	// funcio per a enviar datos de la partida a la base de datos
	// ---------------------------------------------------------------------------------------
	private void guardarEnBBDD(boolean victoriaDerrota) {
		try {
			Class.forName("org.mariadb.jdbc.Driver");
			String urlBaseDades = "jdbc:mariadb://192.168.14.11:3306/ProjecteDAWEquip04";
			String usuari = "root";
			String contrasenya = "root";
			Connection c = DriverManager.getConnection(urlBaseDades, usuari, contrasenya);

			boolean existis = false;

			String sentencia = "INSERT INTO ParaulesWordle (id, paraula) VALUES (?, ?)";
			PreparedStatement s = c.prepareStatement(sentencia);
			s.setInt(1, u.getId());
			s.setString(2, paraulaAdivinar);
			s.executeUpdate();

			sentencia = "SELECT id FROM PartidesWordle WHERE id = " + u.getId();
			s = c.prepareStatement(sentencia);
			ResultSet rs = s.executeQuery();

			// esto es si existis
			if (rs.next()) {
				existis = true;
			}

			if (existis) {
				String columnaTabla = "";
				switch (filaActual) {
				case 0: {
					columnaTabla = "victoria1";
					break;
				}
				case 1: {
					columnaTabla = "victoria2";
					break;
				}
				case 2: {
					columnaTabla = "victoria3";
					break;
				}
				case 3: {
					columnaTabla = "victoria4";
					break;
				}
				case 4: {
					columnaTabla = "victoria5";
					break;
				}
				case 5: {
					if (!victoriaDerrota) {
						columnaTabla = "derrotes";
						break;
					}
					columnaTabla = "victoria6";
					break;
				}
				}

				if (victoriaDerrota) {
					sentencia = "UPDATE PartidesWordle SET " + columnaTabla + " = " + columnaTabla + " + 1 WHERE id = "
							+ u.getId();
					s = c.prepareStatement(sentencia);
					s.executeUpdate();
				} else {
					sentencia = "UPDATE PartidesWordle SET derrotes = derrotes + 1 WHERE id = " + u.getId();
					s = c.prepareStatement(sentencia);
					s.executeUpdate();
				}

			} else {
				int Añadir1victoriaODerrota[] = new int[7];
				if (victoriaDerrota) {
					Añadir1victoriaODerrota[filaActual] = 1;
				} else {
					Añadir1victoriaODerrota[6] = 1;
				}

				sentencia = "INSERT INTO PartidesWordle (id, victoria1, victoria2, victoria3, victoria4, victoria5, victoria6, derrotes) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
				s = c.prepareStatement(sentencia);
				s.setInt(1, u.getId());
				s.setInt(2, Añadir1victoriaODerrota[0]);
				s.setInt(3, Añadir1victoriaODerrota[1]);
				s.setInt(4, Añadir1victoriaODerrota[2]);
				s.setInt(5, Añadir1victoriaODerrota[3]);
				s.setInt(6, Añadir1victoriaODerrota[4]);
				s.setInt(7, Añadir1victoriaODerrota[5]);
				s.setInt(8, Añadir1victoriaODerrota[6]);
				s.executeUpdate();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	// click
	// --------------------------------------------------------------------------------------------------
	public void Click_Q(ActionEvent e) {
		añadirLletra('Q');
		paneRoot.requestFocus();
	}

	public void Click_W(ActionEvent e) {
		añadirLletra('W');
		paneRoot.requestFocus();
	}

	public void Click_E(ActionEvent e) {
		añadirLletra('E');
		paneRoot.requestFocus();
	}

	public void Click_R(ActionEvent e) {
		añadirLletra('R');
		paneRoot.requestFocus();
	}

	public void Click_T(ActionEvent e) {
		añadirLletra('T');
		paneRoot.requestFocus();
	}

	public void Click_Y(ActionEvent e) {
		añadirLletra('Y');
		paneRoot.requestFocus();
	}

	public void Click_U(ActionEvent e) {
		añadirLletra('U');
		paneRoot.requestFocus();
	}

	public void Click_I(ActionEvent e) {
		añadirLletra('I');
		paneRoot.requestFocus();
	}

	public void Click_O(ActionEvent e) {
		añadirLletra('O');
		paneRoot.requestFocus();
	}

	public void Click_P(ActionEvent e) {
		añadirLletra('P');
		paneRoot.requestFocus();
	}

	// --
	public void Click_A(ActionEvent e) {
		añadirLletra('A');
		paneRoot.requestFocus();
	}

	public void Click_S(ActionEvent e) {
		añadirLletra('S');
		paneRoot.requestFocus();
	}

	public void Click_D(ActionEvent e) {
		añadirLletra('D');
		paneRoot.requestFocus();
	}

	public void Click_F(ActionEvent e) {
		añadirLletra('F');
		paneRoot.requestFocus();
	}

	public void Click_G(ActionEvent e) {
		añadirLletra('G');
		paneRoot.requestFocus();
	}

	public void Click_H(ActionEvent e) {
		añadirLletra('H');
		paneRoot.requestFocus();
	}

	public void Click_J(ActionEvent e) {
		añadirLletra('J');
		paneRoot.requestFocus();
	}

	public void Click_K(ActionEvent e) {
		añadirLletra('K');
		paneRoot.requestFocus();
	}

	public void Click_L(ActionEvent e) {
		añadirLletra('L');
		paneRoot.requestFocus();
	}

	public void Click_Ñ(ActionEvent e) {
		añadirLletra('Ñ');
		paneRoot.requestFocus();
	}

	// --
	public void Click_Z(ActionEvent e) {
		añadirLletra('Z');
		paneRoot.requestFocus();
	}

	public void Click_X(ActionEvent e) {
		añadirLletra('X');
		paneRoot.requestFocus();
	}

	public void Click_C(ActionEvent e) {
		añadirLletra('C');
		paneRoot.requestFocus();
	}

	public void Click_V(ActionEvent e) {
		añadirLletra('V');
		paneRoot.requestFocus();
	}

	public void Click_B(ActionEvent e) {
		añadirLletra('B');
		paneRoot.requestFocus();
	}

	public void Click_N(ActionEvent e) {
		añadirLletra('N');
		paneRoot.requestFocus();
	}

	public void Click_M(ActionEvent e) {
		añadirLletra('M');
		paneRoot.requestFocus();
	}

	// -- especials
	// -------------------------------------------------------------------------------------------
	// boto de enviar
	public void Click_Enviar(ActionEvent e) {
		enviar();
	}

	// boto de eliminar
	public void Click_Eliminar(ActionEvent e) {
		eliminar();
	}

}
