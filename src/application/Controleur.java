package application;

import java.net.URL;
import java.util.ResourceBundle;

import application.Modele.PointVue;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.TextFlow;

public class Controleur implements Initializable {
	Modele modele;

	@FXML
	private Button bouton, bAjout, bSuppr, bSupprBrique;
	@FXML
	private Canvas canvas;
	@FXML
	private ChoiceBox<String> couleurInput, pv_input;
	@FXML
	private TextField largeurInput, longueurInput, hauteurInput, consNomInput;
	@FXML
	private ListView<String> listeConstructions, listeBriques;
	@FXML
	private TextFlow guide;

	private String[] listColors = { "Rouge", "Orange", "Jaune", "Vert", "Bleu", "Violet", "Noir" };
	private String[] listPV = { "Nord", "Ouest", "Est", "Sud", "Dessus", "Nord-Ouest" };
	private int[] coordonneesCanvas = { -1, -1 };
	private boolean supprMode = false;

	public void bouton_action() {
		if (this.listeConstructions.getSelectionModel().getSelectedItem() != null || this.listeBriques.isVisible()) {
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
			this.guide.setVisible(!this.guide.isVisible());
			this.pv_input.setVisible(!this.pv_input.isVisible());
			if (this.listeBriques.isVisible()) {
				this.modele.changerPDV(PointVue.DESSUS);
			} else {
				this.modele.enregisterConstructions();
				this.modele.changerPDV(PointVue.N);
				this.supprMode = false;
			}
		}
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

	public void canvas_mouv(@SuppressWarnings("exports") MouseEvent mEvent) {
		if (this.listeBriques.isVisible()) {
			double x = mEvent.getX();
			double y = mEvent.getY();
			int x2 = (int) Math.floor(mEvent.getX() / 25);
			int y2 = (int) Math.floor(mEvent.getY() / 25);
			if (x < 500 && y < 500 && (x2 != this.coordonneesCanvas[0] || y2 != this.coordonneesCanvas[1])) {
				this.coordonneesCanvas[0] = x2;
				this.coordonneesCanvas[1] = y2;
				if (this.supprMode) {
					this.modele.affichage_suppression(x2, y2);
				} else {
					this.modele.affichage_selection(x2, y2);
				}
			}
		}

	}

	public void ajout_b() {
		if (this.listeBriques.getSelectionModel().getSelectedItem() != null) {
			this.modele
					.chargerBrique(ListeBriques.briques.get(this.listeBriques.getSelectionModel().getSelectedItem()));
		}
	}

	public void clic_canvas(@SuppressWarnings("exports") MouseEvent mEvent) {
		if (this.listeBriques.isVisible()) {
			double x = mEvent.getX();
			double y = mEvent.getY();
			int x2 = (int) Math.floor(mEvent.getX() / 25);
			int y2 = (int) Math.floor(mEvent.getY() / 25);
			if (mEvent.getButton() == MouseButton.PRIMARY) {
				if (this.supprMode) {
					this.modele.supprimerBrique(x2, 19 - y2);
					this.modele.afficherConstruction();
				} else {
					this.modele.ajouterBrique(x2, 19 - y2);
				}
			} else if (mEvent.getButton() == MouseButton.SECONDARY) {
				this.modele.tourner();
				if (x < 500 && y < 500) {
					this.modele.affichage_selection(x2, y2);
				}
			}
		}
	}

	public void suppr_b() {
		this.supprMode ^= true;
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		this.modele = new Modele(this.canvas.getGraphicsContext2D()); // Creation classe modèle

		// - - - - - LISTE DES CONSTRUCTIONS - - - - -
		listeConstructions.getItems().addAll(this.modele.rechercherConstruction()); // Obtention via fichiers
		listeConstructions.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> arg0, String arg1, String arg2) {
				modele.chargerConstruction(listeConstructions.getSelectionModel().getSelectedItem());
			}
		});

		// - - - - - LISTE DES BRIQUES (MODIFICATION) - - - - -
		listeBriques.getItems().addAll(ListeBriques.briques.keySet());

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
			this.listeBriques.getItems().clear();
			this.listeBriques.getItems().addAll(ListeBriques.filtre(this.largeurInput.getText(),
					this.longueurInput.getText(), this.hauteurInput.getText()));
		});
		this.longueurInput.textProperty().addListener((observable, oldValue, newValue) -> {
			this.listeBriques.getItems().clear();
			this.listeBriques.getItems().addAll(ListeBriques.filtre(this.largeurInput.getText(),
					this.longueurInput.getText(), this.hauteurInput.getText()));
		});
		this.hauteurInput.textProperty().addListener((observable, oldValue, newValue) -> {
			this.listeBriques.getItems().clear();
			this.listeBriques.getItems().addAll(ListeBriques.filtre(this.largeurInput.getText(),
					this.longueurInput.getText(), this.hauteurInput.getText()));
		});

		// - - - - - POINT DE VUE - - - - -
		this.pv_input.getItems().addAll(listPV);
		pv_input.getSelectionModel().select(0);
		this.pv_input.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> arg0, String arg1, String arg2) {
				PointVue[] povs = { PointVue.N, PointVue.O, PointVue.E, PointVue.S, PointVue.DESSUS, PointVue.NO };
				modele.changerPDV(povs[pv_input.getSelectionModel().getSelectedIndex()]);
			}
		});

	}
}
