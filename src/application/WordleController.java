package application;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.Scanner;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;

public class WordleController implements Initializable {

	// gridpane
	@FXML
	private GridPane panellJoc;

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

	public final static String RUTA_PARAULES = "WordleParaules.txt";
	private char paraulaActual[] = { '-', '-', '-', '-', '-' };
	private int filaActual = 0;
	private int columnaActual = 0;
	private String paraulaAdivinar = "";
	private HBox[][] posicions = new HBox[6][5];

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

		File fitxerParaules = new File(RUTA_PARAULES);
		List<String> llistatParaules = new ArrayList<>();
		Random aleatori = new Random();

		guardarParaules(fitxerParaules, llistatParaules);

		paraulaAdivinar = llistatParaules.get(aleatori.nextInt(llistatParaules.size()));

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

	// traurer la paraula aleatoria
	// ------------------------------------------------------------------------------------------
	private void guardarParaules(File fitxerParaules, List<String> llistatParaules) {
		try {
			Scanner lectorFitxer = new Scanner(fitxerParaules);
			while (lectorFitxer.hasNextLine()) {
				llistatParaules.add(lectorFitxer.nextLine().trim());
			}
			lectorFitxer.close();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	// restableixer array
	// ----------------------------------------------------------------------------------------------------
	private void restableixerArray() {
		for (int i = 0; i < 5; i++) {
			paraulaActual[i] = '-';
		}
	}

	// comprobar si estan y ficar els colors a la graella
	// ---------------------------------------------------------------------
	private void comprobarSiEstan(String paraulaComprovar) {
		String estil = "-fx-border-color: black; -fx-border-width: 3; -fx-border-radius: 5; -fx-background-radius: 7;";

		// falta arreglar que no estiguen en groc cuant no estan mes repetides
		for (int i = 0; i < 5; i++) {
			char lletraJugador = paraulaComprovar.charAt(i);
			HBox casilla = obtindrerPosicio(filaActual, i);

			if (lletraJugador == paraulaAdivinar.charAt(i)) {
				// Vert
				casilla.setStyle(estil + "-fx-background-color: #6aaa64;");
			} else if (paraulaAdivinar.indexOf(lletraJugador) != -1) {
				// Groc
				casilla.setStyle(estil + "-fx-background-color: #c9b458;");
			} else {
				// Gris
				casilla.setStyle(estil + "-fx-background-color: #787c7e;");
			}
		}
	}

	// añadir lletra
	// ------------------------------------------------------------------------------------------
	private void añadirLletra(char lletra) {
		if (columnaActual < 5 && columnaActual >= 0) {
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
		if (fila >= 0 && fila < 6 && columna >= 0 && columna < 5) {
			return posicions[fila][columna];
		}
		return null;
	}

	// click
	// --------------------------------------------------------------------------------------------------
	public void Click_Q(ActionEvent e) {
		añadirLletra('Q');
	}

	public void Click_W(ActionEvent e) {
		añadirLletra('W');
	}

	public void Click_E(ActionEvent e) {
		añadirLletra('E');
	}

	public void Click_R(ActionEvent e) {
		añadirLletra('R');
	}

	public void Click_T(ActionEvent e) {
		añadirLletra('T');
	}

	public void Click_Y(ActionEvent e) {
		añadirLletra('Y');
	}

	public void Click_U(ActionEvent e) {
		añadirLletra('U');
	}

	public void Click_I(ActionEvent e) {
		añadirLletra('I');
	}

	public void Click_O(ActionEvent e) {
		añadirLletra('O');
	}

	public void Click_P(ActionEvent e) {
		añadirLletra('P');
	}

	// --
	public void Click_A(ActionEvent e) {
		añadirLletra('A');
	}

	public void Click_S(ActionEvent e) {
		añadirLletra('S');
	}

	public void Click_D(ActionEvent e) {
		añadirLletra('D');
	}

	public void Click_F(ActionEvent e) {
		añadirLletra('F');
	}

	public void Click_G(ActionEvent e) {
		añadirLletra('G');
	}

	public void Click_H(ActionEvent e) {
		añadirLletra('H');
	}

	public void Click_J(ActionEvent e) {
		añadirLletra('J');
	}

	public void Click_K(ActionEvent e) {
		añadirLletra('K');
	}

	public void Click_L(ActionEvent e) {
		añadirLletra('L');
	}

	public void Click_Ñ(ActionEvent e) {
		añadirLletra('Ñ');
	}

	// --
	public void Click_Z(ActionEvent e) {
		añadirLletra('Z');
	}

	public void Click_X(ActionEvent e) {
		añadirLletra('X');
	}

	public void Click_C(ActionEvent e) {
		añadirLletra('C');
	}

	public void Click_V(ActionEvent e) {
		añadirLletra('V');
	}

	public void Click_B(ActionEvent e) {
		añadirLletra('B');
	}

	public void Click_N(ActionEvent e) {
		añadirLletra('N');
	}

	public void Click_M(ActionEvent e) {
		añadirLletra('M');
	}

	// -- especials
	// -------------------------------------------------------------------------------------------
	// boto de enviar
	public void Click_Enviar(ActionEvent e) {
		int contador = 0;
		String paraulaComprovar = "";
		for (int i = 0; i < 5; i++) {
			if (paraulaActual[i] != '-') {
				contador++;
			}
		}
		if (contador == 5) {
			for (int i = 0; i < 5; i++) {
				paraulaComprovar += paraulaActual[i] + "";
			}
			if (paraulaComprovar.toUpperCase().equals(paraulaAdivinar.toUpperCase())) {
				comprobarSiEstan(paraulaComprovar);
			} else {
				// colorets i tot el rollo
				comprobarSiEstan(paraulaComprovar);

				restableixerArray();
				if (filaActual < 5) {
					columnaActual = 0;
					filaActual++;
				} else {
					System.out.println("Has pergut");
				}
			}
		}
	}

	// boto de eliminar
	public void Click_Eliminar(ActionEvent e) {
		if (columnaActual <= 5 && columnaActual > 0) {
			columnaActual--;
			HBox casilla = obtindrerPosicio(filaActual, columnaActual);
			casilla.getChildren().clear();
		} else if (columnaActual == 0) {
			HBox casilla = obtindrerPosicio(filaActual, columnaActual);
			casilla.getChildren().clear();
		}
		paraulaActual[columnaActual] = '-';
	}

}
