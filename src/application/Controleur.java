package application;

import java.net.URL;
import java.util.LinkedList;
import java.util.ResourceBundle;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;

public class Controleur implements Initializable {
	Modele modele;

	@FXML
	private Button bouton, bAjout, bSuppr, bSupprBrique;
	@FXML
	private Canvas canvas;
	@FXML
	private ChoiceBox<String> couleurInput;
	@FXML
	private TextField largeurInput, longeurInput, hauteurInput;
	@FXML
	private ListView<String> listeConstructions, listeBriques;

	private String[] temporaire_listView = { "Construction 1", "Construction 2", "Construction 3" };
	private String[] temporaire_listView2 = { "Brique 1", "Brique 2", "Brique 3" };
	private String[] listColors = { "Rouge", "Bleu", "Vert", "Jaune", "Noir", "Gris", "Orange" };

	public void bouton_action() {
		this.listeConstructions.setVisible(!this.listeConstructions.isVisible());
		this.listeBriques.setVisible(!this.listeBriques.isVisible());
		this.couleurInput.setVisible(!this.couleurInput.isVisible());
		this.hauteurInput.setVisible(!this.hauteurInput.isVisible());
		this.largeurInput.setVisible(!this.largeurInput.isVisible());
		this.longeurInput.setVisible(!this.longeurInput.isVisible());
		this.bAjout.setVisible(!this.bAjout.isVisible());
		this.bSuppr.setVisible(!this.bSuppr.isVisible());
		this.bSupprBrique.setVisible(!this.bSupprBrique.isVisible());
	}

	public void ajout_cons() {

	}

	public void suppr_cons() {

	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		this.modele = new Modele();

		// - - - - - LISTE DES CONSTRUCTIONS - - - - -
		listeConstructions.getItems().addAll(temporaire_listView);
		listeConstructions.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> arg0, String arg1, String arg2) {
				System.out.println(listeConstructions.getSelectionModel().getSelectedItem());
			}
		});

		// - - - - - LISTE DES BRIQUES (MODIFICATION) - - - - -
		listeBriques.getItems().addAll(temporaire_listView2);
		listeBriques.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> arg0, String arg1, String arg2) {
				System.out.println(listeBriques.getSelectionModel().getSelectedItem());
			}
		});

		// - - - - - LISTE DES COULEURS POUR LES BRIQUES - - - - -
		couleurInput.getItems().addAll(listColors);
		couleurInput.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> arg0, String arg1, String arg2) {
				modele.changerCouleur(couleurInput.getSelectionModel().getSelectedIndex());
			}
		});
		couleurInput.getSelectionModel().select(0);

		GraphicsContext gc = this.canvas.getGraphicsContext2D();

		gc.setFill(Color.BLACK);
		gc.fillRect(0, 5, 25, 25);
		gc.fill();

		LinkedList<Brique> testL = new LinkedList<>();
		testL.add(new Brique(0, 0, 0, Color.BLACK, 0, 3, 0));
		testL.add(new Brique(0, 0, 0, Color.BLACK, 1, 2, 3));
		testL.add(new Brique(0, 0, 0, Color.BLACK, 2, 0, 0));
		testL.add(new Brique(0, 0, 0, Color.BLACK, 0, 1, 0));
		System.out.print(modele.ordreBrique(testL));

		this.modele.afficherConstruction(gc);
	}
}
