package application;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.Window;

public class JocVidaFinalController implements Initializable{
	
	@FXML VBox principal;
	@FXML Label tamanyLabel;
	@FXML Label celulesLabel;
	@FXML Label generacionsLabel;
	@FXML Label totalCreadesLabel;
	@FXML Label totalMortesLabel;
	
	private DadesJocVida jv;

	public void setDadesJocVida(DadesJocVida jv) {
		this.jv = jv;
	}

	public DadesJocVida getDadesJocVida() {
		return this.jv;
	}

	public void initialize(URL arg0, ResourceBundle arg1)
	{
		Platform.runLater(()->{
			Window window = (Stage) principal.getScene().getWindow();
			this.jv = (DadesJocVida) window.getUserData();
			
			switch(jv.getTamanyString())
			{
				case "S": {jv.setTamanyString("Xicotet");break;}
				case "M": {jv.setTamanyString("Mitjà");break;}
				case "L": {jv.setTamanyString("Gran");break;}
			}
			
			tamanyLabel.setText("Tamany seleccionat: "+jv.getTamanyString()+" ("+jv.getTamany()+"x"+jv.getTamany()+" caselles)");
			celulesLabel.setText("nº de cèl·lules finals: "+jv.getCelules());
			generacionsLabel.setText("nº de generacions totals: "+jv.getTotalGeneracions());
			totalCreadesLabel.setText("nº de cèl·lules creades: "+jv.getTotalCreades());
			totalMortesLabel.setText("nº de cèl·lules mortes: "+jv.getTotalMortes());
		});		
	}
	public void tornarClick(ActionEvent e)
	{
		try {
			VBox jocvida = FXMLLoader.load(getClass().getResource("JocVidaInici.fxml"));
			Scene escenaJocVida = new Scene(jocvida);
			Stage window = (Stage) ((Node) e.getSource()).getScene().getWindow();
			window.setScene(escenaJocVida);
			window.setTitle("Joc de la Vida");
			window.show();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}
	
}
