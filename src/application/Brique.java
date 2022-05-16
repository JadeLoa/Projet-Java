package application;

public class Brique {

	public int largeur, longueur, hauteur, x, y, z, couleur;
	// public Color couleur;

	public Brique(int largeur, int longueur, int hauteur, int x, int y, int z, int couleur) {
		this.largeur = largeur;
		this.longueur = longueur;
		this.hauteur = hauteur;
		this.couleur = couleur;
		this.x = x;
		this.y = y;
		this.z = z;
	}

	@Override
	public String toString() {
		return this.x + "," + this.y + "," + this.z;
	}

}
