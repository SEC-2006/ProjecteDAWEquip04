package application;

import javafx.scene.control.Button;
import javafx.scene.shape.Rectangle;

public class CuadradoController extends Button {

	private double lado = 20;
	private String colorCSS;
	private static String colorEscogido = "-fx-background-color: black;";
	private static CuadradoController colorAntiguo = null;
	private boolean coloreado = false;

	public CuadradoController() {
		this.setShape(new Rectangle(lado, lado));
		this.setMinSize(lado, lado);
		this.setMaxSize(lado, lado);
		// this.setOnAction(e -> pintar());


	    this.setOnMousePressed(e -> {
	        if (e.isPrimaryButtonDown()) {
	            pintar();
	        } else if (e.isSecondaryButtonDown()) {
	            netejar();
	        }
	    });

	    this.setOnMouseEntered(e -> {
	        if (e.isPrimaryButtonDown()) {
	            pintar();
	        } else if (e.isSecondaryButtonDown()) {
	            netejar();
	        }
	    });

	}

	private void pintar() {
		this.setStyle(colorEscogido);
		coloreado = true;
	}
	
	private void netejar() {
	    this.setStyle(null);
	    coloreado = false;
	}


	public CuadradoController(String colorCSS) {

		this.setShape(new Rectangle(lado, lado));
		this.setMinSize(lado, lado);
		this.setMaxSize(lado, lado);
		this.setStyle(colorCSS + " -fx-border-color: black; -fx-border-width: 1px;");
		this.colorCSS = colorCSS;
		this.setOnAction(e -> establecerColor());
		

	}

	private void establecerColor() {

		colorEscogido = colorCSS;
		if (colorAntiguo != null)
			colorAntiguo.setDisable(false);
		colorAntiguo = this;
		this.setDisable(true);

	}

	public boolean getColoreado() {

		return coloreado;
	}

	public void blanquear() {

		this.setBackground(null);

	}

}
