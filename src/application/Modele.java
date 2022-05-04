package application;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import javafx.scene.canvas.GraphicsContext;
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

	void afficherConstruction(GraphicsContext gc) {
		// afficher la construction en cours grace au fichier dans lequel sont
		// référencées toutes les briques
		// contenues dans cette construction
		// appelle une sous fonction qui tri l'ordre des briques en fonction de la vue

		// lorsque l'utilisateur clique sur une construction elle devient la
		// constructionEnCours

		LinkedList<Brique> testL = new LinkedList<>(); // TODO renplacer avec liste de construction
		testL.add(new Brique(2, 3, 4, Color.BLACK, 0, 3, 0));
		testL.add(new Brique(1, 1, 1, Color.RED, 9, 2, 17));
		testL.add(new Brique(10, 1, 1, Color.GREEN, 2, 0, 0));
		testL.add(new Brique(2, 2, 1, Color.BLUE, 10, 1, 15));

		testL = this.ordreBrique(testL);

		for (int i = 0; i < testL.size(); i++) {
			Brique briqueEnCours = testL.get(i);
			for (int x = 0; x < briqueEnCours.largeur; x++) {
				for (int y = 0; y < briqueEnCours.longueur; y++) {
					for (int z = 0; z < briqueEnCours.hauteur; z++) {
						gc.setFill(briqueEnCours.couleur);
						gc.fillRect((briqueEnCours.x + x) * 25, (briqueEnCours.z + z) * 25, briqueEnCours.largeur * 25,
								briqueEnCours.hauteur * 25); // TODO ordre à changer en fonction de pdv
						gc.fill();
						// TODO ajouter iso
					}
				}
			}
		}
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
			while (cpt < listeTriee.size() //
					&& X * listeATrier.get(i).x >= X * listeTriee.get(cpt).x
					&& Y * listeATrier.get(i).y >= Y * listeTriee.get(cpt).y
					&& Z * listeATrier.get(i).z >= Z * listeTriee.get(cpt).z) {
				System.out.println(i + " " + listeATrier.size());
				cpt++;
			}
			listeTriee.add(cpt, listeATrier.get(i));
		}
		return listeTriee;

	}

	void sauvegarder(String nomSauvC) {
		// modifie le fichier de la construction modifiée
		
		
		
	}

	void ajouterBrique(int largeur, int longueur, int hauteur, int x, int y, int z) {
		// creer brique à la première place directement au dessous possible
		// ne pas sortir des limites
		
		Brique newB = new Brique(largeur, longueur, hauteur,this.couleurEnCours, x, y, z);
		
		for(int i=0; i<this.listeConstruction.size(); i++) {
			if(this.listeConstruction.get(i).nomConstruction == this.constructionEnCours) {
				this.listeConstruction.get(i).listeBrique.addLast(newB);
			}
		}
		
		// mise à jour du fichier listeBrique de cette construction

	}

	void supprimerBrique(int x, int y) {
		// recherche la première brique dans la liste en partant du haut (z decroissant)
		// et la supprime
		
		for(int i=0; i<this.ordreB.size(); i++) {
			if (this.ordreB.get(i).x==x && this.ordreB.get(i).y==y) {
				this.ordreB.remove(i);
			}
		}

		// met la liste de brique a jour
	}

	boolean ajouterConstruction(String nomNouvelleC) {
		// vérifier que le nomConstruction n'existe pas déjà
		// crée un fichier vide
		// crée une nouvelle construction dans la liste des constructions
		for(int i=0; i<this.listeConstruction.size(); i++) {
			if (this.listeConstruction.get(i).nomConstruction == nomNouvelleC) {
				return false;
			}
		}
		Construction newC = new Construction(nomNouvelleC);
		this.listeConstruction.addLast(newC);
		return true;
		
		// true si on a ajouté la construction, false sinon
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
