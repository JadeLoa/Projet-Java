package application;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

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

	private Map<PointVue, int[]> coefVue = new HashMap<PointVue, int[]>();

	public Modele() {
		int[] a1 = { 0, -1, 0 };
		coefVue.put(PointVue.N, a1);
		int[] a2 = { 0, 1, 0 };
		coefVue.put(PointVue.S, a2);
		int[] a3 = { -1, 0, 0 };
		coefVue.put(PointVue.E, a3);
		int[] a4 = { 1, 0, 0 };
		coefVue.put(PointVue.O, a4);
		int[] a5 = { 0, 0, 1 };
		coefVue.put(PointVue.DESSUS, a5);
		int[] a6 = { -1, -1, 1 };
		coefVue.put(PointVue.NE, a6);
		int[] a7 = { 1, -1, 1 };
		coefVue.put(PointVue.NO, a7);
		int[] a8 = { -1, 1, 1 };
		coefVue.put(PointVue.SE, a8);
		int[] a9 = { 1, 1, 1 };
		coefVue.put(PointVue.SO, a9);
	}

	void rechercherConstruction() {
		// recrée la linkedList listeConstruction par les fichiers (nomC)
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
		// renvoie une liste triée
		LinkedList<Brique> listeTriee = new LinkedList<Brique>();
		int X = coefVue.get(pdv)[0];
		int Y = coefVue.get(pdv)[1];
		int Z = coefVue.get(pdv)[2];
		listeTriee.add(listeATrier.get(0));
		for (int i = 1; i < listeATrier.size(); i++) {
			int cpt = 0;
			while (X * listeATrier.get(i).x > X * listeTriee.get(cpt).x
					&& Y * listeATrier.get(i).y > Y * listeTriee.get(cpt).y
					&& Z * listeATrier.get(i).z > Z * listeTriee.get(cpt).z //
					&& cpt < listeTriee.size()) {
				cpt++;
			}
			listeTriee.add(cpt, listeATrier.get(i));
		}
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
		// crée un fichier vide
		// crée une nouvelle construction dans la liste des constructions
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
