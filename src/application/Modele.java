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
	private Construction constructionEnCours;

	public enum PointVue {
		N, S, E, O, DESSUS, NE, NO, SE, SO
	};

	private GraphicsContext gc;
	private PointVue pdv = PointVue.N;
	private Color[][] listeCouleurs = { { Color.web("#e40303"), Color.web("#fc3e3e"), Color.web("#a00202") }, // rouge
			{ Color.web("#ff8c00"), Color.web("#ffaf4d"), Color.web("#b36200") }, // orange
			{ Color.web("#ffed00"), Color.web("#fff24d"), Color.web("#b3a600") }, // jaune
			{ Color.web("#008026"), Color.web("#00f348"), Color.web("#005a1b") }, // vert
			{ Color.web("#004dff"), Color.web("#4d82ff"), Color.web("#0036b3") }, // bleu
			{ Color.web("#750787"), Color.web("#d00cf0"), Color.web("#52055f") }, // violet
			{ Color.web("#1e1e1f"), Color.web("#606063"), Color.web("#151516") }, // noir
	};
	private int couleurEnCours = 0; // couleur par défaut (indice)
	private LinkedList<Construction> listeConstruction;
	private LinkedList<Brique> ordreB;
	private int[] briqueEnCours = new int[4];

	private Map<PointVue, int[]> coefVue = new HashMap<PointVue, int[]>();

	public Modele(GraphicsContext graphicsContext) {
		this.gc = graphicsContext;
		int[][] coefs = { { 0, -1, 0 }, { 0, 1, 0 }, { -1, 0, 0 }, { 1, 0, 0 }, { 0, 0, 1 }, { -1, -1, 1 },
				{ 1, -1, 1 }, { -1, 1, 1 }, { 1, 1, 1 } };
		PointVue[] povs = { PointVue.N, PointVue.S, PointVue.E, PointVue.O, PointVue.DESSUS, PointVue.NE, PointVue.NO,
				PointVue.SE, PointVue.SO };
		for (int i = 0; i < coefs.length; i++) {
			coefVue.put(povs[i], coefs[i]);
		}
	}

	ArrayList<String> rechercherConstruction() {
		// recrée la linkedList listeConstruction par les fichiers (nomC)
		// on initialise constructionEnCours
		ArrayList<String> listResults = new ArrayList<>();

		Gson gson = new Gson();

		File folder = new File("BibliConstruction");
		File[] listOfFiles = folder.listFiles();
		this.listeConstruction = new LinkedList<>();

		for (int i = 0; i < listOfFiles.length; i++) {
			if (listOfFiles[i].isFile()) {
				try {
					String strFile = listOfFiles[i].getName();
					if (strFile.endsWith(".json")) {
						this.listeConstruction.add(gson.fromJson(new FileReader(listOfFiles[i]), Construction.class));
						listResults.add(strFile.substring(0, strFile.length() - 5));
					}
				} catch (JsonSyntaxException | JsonIOException | FileNotFoundException e) {
					e.printStackTrace();
				}
			}
		}
		System.out.print(this.listeConstruction.size());
		return listResults;
	}

	void afficherConstruction() {
		// afficher la construction en cours grace au fichier dans lequel sont
		// référencées toutes les briques
		// contenues dans cette construction
		// appelle une sous fonction qui tri l'ordre des briques en fonction de la vue

		// lorsque l'utilisateur clique sur une construction elle devient la
		// constructionEnCours
		// this.constructionEnCours.listeBrique.add(new Brique(1, 2, 1, 10, 0, 0, 0));
		gc.beginPath();
		gc.setFill(Color.WHITE);
		gc.rect(0, 0, 500, 500);
		gc.fill();

		if (this.constructionEnCours.listeBrique.size() > 0) {
			LinkedList<Brique> listeBriques = this.ordreBrique(this.constructionEnCours.listeBrique);

			for (Brique b : listeBriques) {
				gc.beginPath();
				gc.setFill(this.listeCouleurs[b.couleur][0]);
				for (int x = 0; x < b.largeur; x++) {
					for (int y = 0; y < b.longueur; y++) {
						for (int z = 0; z < b.hauteur; z++) {
							switch (this.pdv) {
							case DESSUS:
								gc.fillRect((b.x + x) * 25, 475 - (b.y + y) * 25, 25, 25);
								break;
							case N:
								gc.fillRect((b.x + x) * 25, 475 - (b.z + z) * 25, 25, 25);
								break;
							case O:
								gc.fillRect(475 - (b.y + y) * 25, 475 - (b.z + z) * 25, 25, 25);
								break;
							case E:
								gc.fillRect((b.y + y) * 25, 475 - (b.z + z) * 25, 25, 25);
								break;
							case S:
								gc.fillRect(475 - (b.x + x) * 25, 475 - (b.z + z) * 25, 25, 25);
								break;
							case NO:
							case NE:
							case SO:
							case SE:
								break;
							}
							gc.fill();
							// TODO ajouter iso
						}
					}
				}
			}
		}

	}

	LinkedList<Brique> ordreBrique(LinkedList<Brique> listeATrier) {
		// fonction qui trie l'ordre des briques en entrée
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
				// System.out.println(i + " " + listeATrier.size());
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

		/*
		 * Brique newB = new Brique(largeur, longueur, hauteur, this.couleurEnCours, x,
		 * y, z);
		 * 
		 * for (int i = 0; i < this.listeConstruction.size(); i++) { if
		 * (this.listeConstruction.get(i).nomConstruction == this.constructionEnCours) {
		 * this.listeConstruction.get(i).listeBrique.addLast(newB); } }
		 */

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
		if (nomNouvelleC.length() == 0) {
			return false;
		}
		for (Construction c : this.listeConstruction) {
			if (c.nomConstruction.equals(nomNouvelleC)) {
				return false;
			}
		}
		Construction newC = new Construction(nomNouvelleC);

		Gson gson = new Gson();

		try (FileWriter writer = new FileWriter("BibliConstruction/" + nomNouvelleC + ".json")) {
			gson.toJson(newC, writer);
		} catch (IOException e) {
			e.printStackTrace();
		}

		this.listeConstruction.addLast(newC);
		return true;

		// true si on a ajouté la construction, false sinon
	}

	boolean supprimerConstruction(String nomC) {
		File f = new File("BibliConstruction/" + nomC + ".json");
		return f.delete();
	}

	void changerCouleur(int i) {
		this.couleurEnCours = i;
	}

	void changerPDV(PointVue pv) {
		this.pdv = pv;
		this.afficherConstruction();
	}

	void affichage_selection(int x, int y) {
		if (this.briqueEnCours[0] != 0 && (x - this.briqueEnCours[0] / 2) >= 0 && (y - this.briqueEnCours[1] / 2) >= 0
				&& (x - this.briqueEnCours[0] / 2) + this.briqueEnCours[0] <= 20
				&& (y - this.briqueEnCours[1] / 2) + this.briqueEnCours[1] <= 20) {
			this.afficherConstruction();
			this.gc.beginPath();
			this.gc.setFill(this.listeCouleurs[this.couleurEnCours][1]);
			this.gc.rect((x - this.briqueEnCours[0] / 2) * 25, (y - this.briqueEnCours[1] / 2) * 25,
					25 * this.briqueEnCours[0], 25 * this.briqueEnCours[1]);
			this.gc.fill();
		}

	}

	void chargerConstruction(String str) {
		for (Construction c : this.listeConstruction) {
			if (c.nomConstruction.equals(str)) {
				this.constructionEnCours = c;
				break;
			}
		}
		System.out.println(str + " a ete charge"); // TODO delete later
		this.afficherConstruction();
	}

	void enregisterConstructions() {
		Gson gson = new Gson();

		try (FileWriter writer = new FileWriter(
				"BibliConstruction/" + this.constructionEnCours.nomConstruction + ".json")) {
			gson.toJson(this.constructionEnCours, writer);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	void chargerBrique(int[] is) {
		for (int i = 0; i < 3; i++) {
			this.briqueEnCours[i] = is[i];
		}
		System.out.println(is[0] + ", " + is[1] + ", " + is[2] + " à été chargé");
	}

}
