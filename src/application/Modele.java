package application;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;

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
	private int couleurEnCours = 0; // couleur par défaut (indice)
	private LinkedList<Construction> listeConstruction;
	private LinkedList<Brique> ordreB;

	private Map<PointVue, int[]> coefVue = new HashMap<PointVue, int[]>();

	public Modele() {
		int[][] coefs = { { 0, -1, 0 }, { 0, 1, 0 }, { -1, 0, 0 }, { 1, 0, 0 }, { 0, 0, 1 }, { -1, -1, 1 },
				{ 1, -1, 1 }, { -1, 1, 1 }, { 1, 1, 1 } };
		PointVue[] povs = { PointVue.N, PointVue.S, PointVue.E, PointVue.O, PointVue.DESSUS, PointVue.NE, PointVue.NO,
				PointVue.SE, PointVue.SO };
		for (int i = 0; i < coefs.length; i++) {
			coefVue.put(povs[i], coefs[i]);
		}

		Gson gson = new Gson();
		Construction obj = new Construction("hello");

		String json = gson.toJson(obj);

		System.out.println("[" + json + "]");

		try (FileWriter writer = new FileWriter("BibliConstruction/aa.json")) {
			gson.toJson(obj, writer);
		} catch (IOException e) {
			e.printStackTrace();
		}

		// this.rechercherConstruction();
	}

	ArrayList<String> rechercherConstruction() {
		// recrée la linkedList listeConstruction par les fichiers (nomC)
		// on initialise constructionEnCours
		ArrayList<String> listResults = new ArrayList<>();

		Gson gson = new Gson();

		File folder = new File("BibliConstruction");
		File[] listOfFiles = folder.listFiles();

		for (int i = 0; i < listOfFiles.length; i++) {
			this.listeConstruction = new LinkedList<>();
			if (listOfFiles[i].isFile()) {
				try {
					String strFile = listOfFiles[i].getName();
					if (strFile.endsWith(".json")) {
						this.listeConstruction.add(
								gson.fromJson(new FileReader(listOfFiles[i].getAbsoluteFile()), Construction.class));
						listResults.add(strFile.substring(0, strFile.length() - 5));
					}
				} catch (JsonSyntaxException | JsonIOException | FileNotFoundException e) {
					e.printStackTrace();
				}
			}
		}
		return listResults;
	}

	void afficherConstruction(GraphicsContext gc) {
		// afficher la construction en cours grace au fichier dans lequel sont
		// référencées toutes les briques
		// contenues dans cette construction
		// appelle une sous fonction qui tri l'ordre des briques en fonction de la vue

		// lorsque l'utilisateur clique sur une construction elle devient la
		// constructionEnCours

		LinkedList<Brique> testL = new LinkedList<>(); // TODO renplacer avec liste de construction
		/* @formatter:off
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
		@formatter:on */
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

		Brique newB = new Brique(largeur, longueur, hauteur, this.couleurEnCours, x, y, z);

		for (int i = 0; i < this.listeConstruction.size(); i++) {
			if (this.listeConstruction.get(i).nomConstruction == this.constructionEnCours) {
				this.listeConstruction.get(i).listeBrique.addLast(newB);
			}
		}

		// mise à jour du fichier listeBrique de cette construction

	}

	void supprimerBrique(int x, int y) {
		// recherche la première brique dans la liste en partant du haut (z decroissant)
		// et la supprime

		for (int i = 0; i < this.ordreB.size(); i++) {
			if (this.ordreB.get(i).x == x && this.ordreB.get(i).y == y) {
				this.ordreB.remove(i);
			}
		}

		// met la liste de brique a jour
	}

	boolean ajouterConstruction(String nomNouvelleC) {
		// vérifier que le nomConstruction n'existe pas déjà
		// crée un fichier vide
		// crée une nouvelle construction dans la liste des constructions
		for (int i = 0; i < this.listeConstruction.size(); i++) {
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
		this.couleurEnCours = i;
		System.out.print(i);
	}

}
