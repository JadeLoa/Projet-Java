package application;

import java.util.LinkedList;

import javafx.scene.paint.Color;

public class Modele {
	/*
	 * regroupe toutes les données et les états qui caractérisent l'application
	 */
	private String constructionEnCours;

	public enum PointVue {
		N, S, E, O, DESSUS, NE, NO, SE, SO
	};

	private PointVue pdv = PointVue.N;
	private Color couleurEnCours = Color.RED;// couleur par défaut
	private LinkedList<Construction> listeConstruction;
	private LinkedList<Brique> ordreB;

	void rechercherConstruction() {
		// recré la linkedList listeConstruction par les fichiers (nomC)
		// on initialise constructionEnCours

	}

	void afficherConstruction() {
		// afficher la construction en cours grace au fichier dans lequel sont
		// référencées toutes les briques
		// contenues dans cette construction
		// appelle une sous fonction qui tri l'ordre des briques en fonction de la vue

		// lorsque l'utilisateur clique sur une construction elle devient la
		// constructionEnCours
	}

	void ordreBrique() {
		// fonction qui tri l'ordre briques

	}

	void sauvegarder() {
		// on vérifie qu'il y a une construction en cours, sinon ne fait rien
		// modifie les fichiers sur la machine par la liste construction
	}

	void ajouterBrique(int largeur, int longueur, int hauteur, int x, int y, int z) {
		// creer brique,
		// mise à jour du fichier listeBrique de cette construction

	}

	void supprimerBrique(int x, int y, int z) {
		// recherche la brique dans la liste et la supprime
		// retourne la liste de brique a jour
	}

	boolean ajouterConstruction(String nomNouvelleC) {
		// vérifier que le nomConstruction n'existe pas déjà
		// cre un fichier vide
		// cré une nouvelle construction dans la liste des constructions
		return false; // true si on a ajouté la construction, false sinon
	}

	void supprimerConstruction() {
		// supprime la construction de la liste construction
		// retourne liste construction à jour
	}

}
