package application;

import java.util.ArrayList;

import javafx.geometry.Point3D;
import javafx.scene.paint.Color;
import javafx.scene.shape.Box;

public class Piece {
	public static final double PAS=50;

    public int hauteur, largeur, longueur;
    public Color couleur;
    Connexion[][] connecteursM;

    public Piece(int hauteur, int largeur, int longueur, Color couleur) {
        this.hauteur = hauteur;
        this.largeur = largeur;
        this.longueur = longueur;
        this.couleur = couleur;
        this.connecteursM = new Connexion[longueur][largeur];
        for (int i = 0; i < longueur; i++) {
            for (int j = 0; j < largeur; j++) {
                this.connecteursM[i][j] = null;
            }
        }

    }

    public boolean connecterPieceF(Position connecteurDepart, Piece pieceF, Position connecteursF[][]) {       
        
        // verifier s'il a pas un bug de reference de connecteur
        if (connecteurDepart.i+connecteursF.length>=connecteursM.length) return false;
        if (connecteurDepart.j+connecteursF[0].length>=connecteursM[0].length) return false;
        
        // verifier si les connecteurs des 2 pieces sont libres
        for (int i = 0; i <= connecteursF.length; i++) {
            for (int j = 0; j < connecteursF[i].length; j++) {
                if (this.connecteursM[connecteurDepart.i + i][connecteurDepart.j + j]!=null) return false;
            }
        } 
        
        // affecter les connecteurs
        for (int i = 0; i <= connecteursF.length; i++) {
            for (int j = 0; j < connecteursF[i].length; j++) {
                this.connecteursM[connecteurDepart.i + i][connecteurDepart.j + j]=new Connexion(pieceF,connecteursF[i][j]);
            }
        }

        return true;
    }
    
    
	Point3D calculerPosition(Piece pieceF) {
		Point3D pF=null;
		
		
		
		return pF;
	}
	
	
	public ArrayList<Box> generate3DBoxes(){
		ArrayList<Box> boxes=new ArrayList();
		Box boxM=new Box();
		boxM.setWidth(PAS*this.largeur);
		boxM.setHeight(PAS*this.hauteur);
		boxM.setDepth(PAS*this.longueur);
		boxes.add(boxM);
		
		for(int i=0;i<this.connecteursM.length;i++)
			for(int j=0;j<this.connecteursM[i].length;j++)
			{
				if (this.connecteursM[i][j]!=null) {
					Piece pieceF=this.connecteursM[i][j].pieceFemelle;
					Box boxF=new Box();
					boxF.setWidth(PAS*pieceF.largeur);
					boxF.setHeight(PAS*pieceF.hauteur);
					boxF.setDepth(PAS*pieceF.longueur);	
					int i1=this.connecteursM[i][j].connecteurF.i;
					int j1=this.connecteursM[i][j].connecteurF.j;
					double depx,depy,depz;
					depy = boxM.translateYProperty().get()-PAS;
					boxF.translateYProperty().set(depy);
					depx = boxM.translateXProperty().get()+(i1 + pieceF.largeur/2.0 -i-this.largeur/2.0)*PAS;
					boxF.translateXProperty().set(depx);
					depz = boxM.translateZProperty().get()+(j1 - pieceF.longueur/2.0 +j-this.longueur/2.0)*PAS;
					boxF.translateZProperty().set(depz);					
					boxes.add(boxF);
				}
			}
		
		return boxes;
	}
	
	
	
	
    //public String toString() {
    //	return this.x + "," + this.y + "," + this.z;
    //}
}
