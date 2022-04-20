package application;

import javafx.scene.paint.Color;

public class Brique {

	public int hauteur, largeur, longueur, x, y, z;
	public Color couleur;

	public Brique(int hauteur, int largeur, int longueur, Color couleur, int x, int y, int z) {
		this.hauteur = hauteur;
		this.largeur = largeur;
		this.longueur = longueur;
		this.couleur = couleur;
		this.x = x;
		this.y = y;
		this.z = z;

	}

}
