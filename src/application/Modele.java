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
	private Color[] listeCouleurs = { Color.RED, Color.BLUE, Color.GREEN, Color.YELLOW, Color.ORANGE, Color.VIOLET,
			Color.BLACK, Color.GRAY };
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

	LinkedList<Brique> ordreBrique(LinkedList<Brique> listeATrier) {
		// fonction qui tri l'ordre briques
		// Nord : Y decroissant
		// Sud : Y croissant
		// Ouest : X croissant
		// Est : X decroissant
		return null;

	}

	void sauvegarder(String nomSauvC) {
		// modifie le fichier de la construction modifiée
	}

	void ajouterBrique(int largeur, int longueur, int hauteur, int x, int y) {
		// creer brique à la première place directement au dessous possible
		// ne pas sortir des limites

		// mise à jour du fichier listeBrique de cette construction

	}

	void supprimerBrique(int x, int y) {
		// recherche la première brique dans la liste en partant du haut (z decroissant)
		// et la supprime

		// met la liste de brique a jour
	}

	boolean ajouterConstruction(String nomNouvelleC) {
		// vérifier que le nomConstruction n'existe pas déjà
		// cre un fichier vide
		// cré une nouvelle construction dans la liste des constructions
		return false; // true si on a ajouté la construction, false sinon
	}

	boolean supprimerConstruction() {
		// supprime la construction de la liste construction
		// retourne liste construction à jour
		return false; // true si on a suprimé la construction, false sinon
	}

	void changerCouleur(int i) {
		this.couleurEnCours = this.listeCouleurs[i];
		System.out.print(i);
	}

}
