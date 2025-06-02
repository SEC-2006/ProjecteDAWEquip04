package application;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class IniciController {

	@FXML
	private Button btnacceptar;

	@FXML
	private ToggleGroup tamany;

	@FXML
	private Stage taulerStage;

	@FXML
	void paginaSeguent(ActionEvent event) {
		Toggle selectedToggle = tamany.getSelectedToggle();

		if (selectedToggle != null) {
			int mida = Integer.parseInt(selectedToggle.getUserData().toString());

			try {
				TaulerController escena = new TaulerController(new VBox(), 500, 550, mida);

				Stage escenario = (Stage) ((Node) event.getSource()).getScene().getWindow();
				escenario.setTitle("Dibuixant PixelArt");
				escenario.setScene(escena);
				escenario.show();
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			System.out.println("Has de seleccionar un tamany abans de continuar.");
		}
	}

}
