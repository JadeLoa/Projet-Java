package application;

import javafx.scene.paint.Color;

public class Piece {

	public int hauteur, largeur, longueur;
	public Color couleur;
	Connecteur[][] connecteursF;
	Connecteur[][] connecteursM;

	public Piece(int hauteur, int largeur, int longueur, Color couleur) {
		this.hauteur = hauteur;
		this.largeur = largeur;
		this.longueur = longueur;
		this.couleur = couleur;
		this.connecteursM=new Connecteur[largeur][longueur];
		this.connecteursF=new Connecteur[largeur][longueur];
		for(int i=0;i<largeur;i++)
			for(int j=0;j<longueur;j++) {
				this.connecteursF[i][j]=null; // pas de connexion
				this.connecteursM[i][j]=null;				
			}

	}
	
	public boolean connecterPieceF(Piece pieceF,AssoConnecteurs asc) {
		// verifier si les connecteurs des 2 pieces sont libres
		for (int i=0; i<=asc.largeur; i++) {
			for (int j=0;j<asc.longueur; j++) {
				if  (this.connecteursM[asc.male[i][j].x][asc.male[i][j].y]!=null) return false;
				if  (pieceF.connecteursF[asc.femelle[i][j].x][asc.femelle[i][j].y]!= null) return false; 
				}
			}
	
		// affecter les connecteurs
		for (int i=0; i<=asc.largeur; i++) {
			for (int j=0;j<asc.longueur; j++) {
				this.connecteursM[asc.male[i][j].x][asc.male[i][j].y]= asc.femelle[i][j]; 
				pieceF.connecteursF[asc.femelle[i][j].x][asc.femelle[i][j].y]= asc.male[i][j]; 
				}
			}		
		
		
		
		return true;
	}
	
	//public String toString() {
	//	return this.x + "," + this.y + "," + this.z;
	//}

}
