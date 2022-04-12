package application;

import javafx.scene.paint.Color;

public class Modele {
	/*
	 * regroupe toutes les données et les états qui caractérisent l'application
	 */
	
	void rechercherConstruction(){
		//qui retrourne la liste des construction dans un fichier liste construction
		
	}
	
	void afficherConstruction(String nomConstruction) {
		//afficher la construction grace au fichier dans lequel sont référencées toutes les briques
		//contenues dans cette construction
		
		
	}
	
	void ajouterBrique(int largeur, int longueur, int hauteur, Color couleur, int x, int y, int z) {
		//creer brique, mise à jour du fichier
		//retourne la liste de brique à jour
		
	}
	
	void supprimerBrique(int x, int y, int z) {
		//recherche la brique  dans la liste et la supprime
		//retourne la liste de brique a jour
	}
	
	void ajouterConstruction(String nomC) {
		//cre un fichier vide
		//retourne l'objet construction qui n'a pas de brique pour l'intant
	}
	
	void supprimerConstruction(String nomC) {
		//supprime la construction de la liste construction
		//retourne liste construction à jour
	}
	
}
