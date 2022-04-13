package application;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;

public class Controleur implements Initializable {
	@FXML
	private Button bouton;
	@FXML
	private Button bAjout;
	@FXML
	private Button bSuppr;
	@FXML
	private Canvas canvas;

	@FXML
	private ListView<String> listeConstructions;

	private String[] temporaire_listView = { "Construction 1", "Construction 2", "Construction 3" };

	public void bouton_action() {
		listeConstructions.setVisible(!listeConstructions.isVisible());
		System.out.println("test ");
	}

	public void ajout_cons() {

	}

	public void suppr_cons() {

	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		listeConstructions.getItems().addAll(temporaire_listView);
		listeConstructions.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> arg0, String arg1, String arg2) {
				System.out.println(listeConstructions.getSelectionModel().getSelectedItem());
			}
		});
	}
}
