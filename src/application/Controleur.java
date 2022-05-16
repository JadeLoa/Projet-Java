package application;

import java.net.URL;
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
import javafx.scene.input.MouseEvent;

public class Controleur implements Initializable {
	Modele modele;

	@FXML
	private Button bouton, bAjout, bSuppr, bSupprBrique;
	@FXML
	private Canvas canvas;
	@FXML
	private ChoiceBox<String> couleurInput;
	@FXML
	private TextField largeurInput, longueurInput, hauteurInput, consNomInput;
	@FXML
	private ListView<String> listeConstructions, listeBriques;

	private String[] listColors = { "Rouge", "Bleu", "Vert", "Jaune", "Noir", "Gris", "Orange" };
	private int[] coordonneesCanvas = { -1, -1 };

	public void bouton_action() {
		this.listeConstructions.setVisible(!this.listeConstructions.isVisible());
		this.listeBriques.setVisible(!this.listeBriques.isVisible());
		this.couleurInput.setVisible(!this.couleurInput.isVisible());
		this.hauteurInput.setVisible(!this.hauteurInput.isVisible());
		this.largeurInput.setVisible(!this.largeurInput.isVisible());
		this.longueurInput.setVisible(!this.longueurInput.isVisible());
		this.consNomInput.setVisible(!this.consNomInput.isVisible());
		this.bAjout.setVisible(!this.bAjout.isVisible());
		this.bSuppr.setVisible(!this.bSuppr.isVisible());
		this.bSupprBrique.setVisible(!this.bSupprBrique.isVisible());
	}

	public void ajout_cons() {
		String nomC = this.consNomInput.getText();
		if (this.modele.ajouterConstruction(nomC)) {
			this.listeConstructions.getItems().add(nomC);
		}
	}

	public void suppr_cons() {
		String nomC = this.listeConstructions.getSelectionModel().getSelectedItem();
		if (this.modele.supprimerConstruction(nomC)) {
			this.listeConstructions.getItems().remove(nomC);
		}
	}

	@SuppressWarnings("exports")
	public void canvas_mouv(MouseEvent mEvent) {
		if (this.listeBriques.isVisible()) {
			double x = mEvent.getX();
			double y = mEvent.getY();
			int x2 = (int) Math.floor(mEvent.getX() / 25);
			int y2 = (int) Math.floor(mEvent.getY() / 25);
			if (x < 500 && y < 500 && (x2 != this.coordonneesCanvas[0] || y2 != this.coordonneesCanvas[1])) {
				this.coordonneesCanvas[0] = x2;
				this.coordonneesCanvas[1] = y2;
				// DELETE LATER
				System.out.println(x2 + ", " + y2);
				GraphicsContext ctx = this.canvas.getGraphicsContext2D();
				ctx.rect(x2 * 25, y2 * 25, 25, 25);
				ctx.fill();
			}
		}

	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		this.modele = new Modele(); // Creation classe modèle

		// - - - - - LISTE DES CONSTRUCTIONS - - - - -
		listeConstructions.getItems().addAll(this.modele.rechercherConstruction()); // Obtention via fichiers
		listeConstructions.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> arg0, String arg1, String arg2) {
				System.out.println(listeConstructions.getSelectionModel().getSelectedItem());
			}
		});

		// - - - - - LISTE DES BRIQUES (MODIFICATION) - - - - -
		listeBriques.getItems().addAll(ListeBriques.briques.keySet());
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

		// - - - - - FILTRAGE DES BRIQUES - - - - -
		this.largeurInput.textProperty().addListener((observable, oldValue, newValue) -> {
			System.out.println("nouvelle largeur : " + newValue);
		});
		this.longueurInput.textProperty().addListener((observable, oldValue, newValue) -> {
			System.out.println("nouvelle longueur : " + newValue);
		});
		this.hauteurInput.textProperty().addListener((observable, oldValue, newValue) -> {
			System.out.println("nouvelle hauteur : " + newValue);
		});
	}
}
