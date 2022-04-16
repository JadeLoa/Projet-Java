package application;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

public class Controleur implements Initializable {
	@FXML
	private Button bouton, bAjout, bSuppr;
	@FXML
	private Canvas canvas;
	@FXML
	private ChoiceBox<String> couleurInput;
	@FXML
	private TextField largeurInput, longeurInput, hauteurInput;
	@FXML
	private Label titre;

	@FXML
	private ListView<String> listeConstructions, listeBriques;

	private String[] temporaire_listView = { "Construction 1", "Construction 2", "Construction 3" };
	private String[] temporaire_listView2 = { "Brique 1", "Brique 2", "Brique 3" };

	public void bouton_action() {
		this.listeConstructions.setVisible(!this.listeConstructions.isVisible());
		this.listeBriques.setVisible(!this.listeBriques.isVisible());
		this.couleurInput.setVisible(!this.couleurInput.isVisible());
		this.hauteurInput.setVisible(!this.hauteurInput.isVisible());
		this.largeurInput.setVisible(!this.largeurInput.isVisible());
		this.longeurInput.setVisible(!this.longeurInput.isVisible());
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
		listeBriques.getItems().addAll(temporaire_listView2);
		listeBriques.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> arg0, String arg1, String arg2) {
				System.out.println(listeBriques.getSelectionModel().getSelectedItem());
			}
		});
	}
}
