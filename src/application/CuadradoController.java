package application;

import javafx.scene.control.Button;
import javafx.scene.shape.Rectangle;

public class CuadradoController extends Button {

	private double lado = 20;
	private String colorCSS;
	private static String colorEscogido = "-fx-background-color: black;";
	private static CuadradoController colorAntiguo = null;
	
	public CuadradoController() {
		this.setShape(new Rectangle(lado, lado));
		this.setMinSize(lado, lado);
		this.setMaxSize(lado, lado);
		this.setOnAction(e-> pintar());
	}
	
	private void pintar() {
		
		this.setStyle(colorEscogido);
		
	}

	public CuadradoController(String colorCSS) {
		
		this.setShape(new Rectangle(lado, lado));
		this.setMinSize(lado, lado);
		this.setMaxSize(lado, lado);
		this.setStyle(colorCSS + " -fx-border-color: black; -fx-border-width: 1px;");
		this.colorCSS = colorCSS;
		this.setOnAction(e-> establecerColor());
		
	}

	private void establecerColor() {
		
		colorEscogido = colorCSS;
		if(colorAntiguo!=null) colorAntiguo.setDisable(false);
		colorAntiguo = this;
		this.setDisable(true);
		
	}
	
}
