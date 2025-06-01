package application;

import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class TaulerController extends Scene {
	
	private VBox principal, filas;
	private HBox[] cuadradosEnFila;
	private HBox colores;
	
	private static final  double SEPARACION = 1;

	public TaulerController(Parent contenedor, double ancho, double alto) {
		super(contenedor, ancho, alto);
		principal = (VBox) contenedor;
		principal.setSpacing(30);
		filas = new VBox(SEPARACION);
		cuadradosEnFila = new HBox[20];
		colores = new HBox(5);
		colores.setAlignment(Pos.CENTER);
		montarEscena();
		
	}

	private void montarEscena() {
		
		principal.setAlignment(Pos.TOP_CENTER);
		
		//Preparant quadrats de pintura
		for(int i= 0;i<cuadradosEnFila.length;i++) {
			
			cuadradosEnFila[i] = new HBox(SEPARACION);
			cuadradosEnFila[i].setAlignment(Pos.TOP_CENTER);
			
			for(int j=0; j<cuadradosEnFila.length; j++) {
				cuadradosEnFila[i].getChildren().add(new CuadradoController());
			}
			
			filas.getChildren().add(cuadradosEnFila[i]);
			
		}
		
		prepararColores();
		
		principal.getChildren().addAll(filas, colores);
	
	}

	private void prepararColores() {
		
		String[] nombresColores = {"white", "black", "red", "blue", "green", "yellow", "purple", "pink"};
		
		for(String s : nombresColores) {
			colores.getChildren().add(new CuadradoController("-fx-background-color: "
					+ s + ";"));
		}
	}

}
