package application;

import java.util.ArrayList;

import javafx.geometry.Point3D;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;

public class Piece {
	public static final double PAS=50, PAS_H=(2*PAS)/3;

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
        if (connecteurDepart.i+connecteursF.length>=connecteursM.length) {
        	return false;
        }
        if (connecteurDepart.j+connecteursF[0].length>=connecteursM[0].length) {
        	return false;
        }
        
        // verifier si les connecteurs des 2 pieces sont libres
        for (int i = 0; i <= connecteursF.length; i++) {
            for (int j = 0; j < connecteursF[i].length; j++) {
                if (this.connecteursM[connecteurDepart.i + i][connecteurDepart.j + j]!=null) {
                	return false;
                }
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

	
	
	public ArrayList<Box> generate3DBoxes(double translateX, double translateY, double translateZ){
		ArrayList<Box> boxes=new ArrayList();
		
		Box boxM=new Box();
		PhongMaterial material = new PhongMaterial();
        material.setDiffuseColor(this.couleur);
        material.setSpecularColor(this.couleur);
        boxM.setMaterial(material);

        boxM.setWidth(PAS * this.largeur);
        boxM.setHeight(PAS_H * this.hauteur);
        boxM.setDepth(PAS * this.longueur);
        boxM.translateXProperty().set(translateX);
        boxM.translateYProperty().set(translateY);
        boxM.translateZProperty().set(translateZ);

        boxes.add(boxM);

        for (int i = 0; i < this.connecteursM.length; i++) {
            for (int j = 0; j < this.connecteursM[i].length; j++) {
                if (this.connecteursM[i][j] != null) {
                    Piece pieceF = this.connecteursM[i][j].pieceFemelle;
                    Box boxF = new Box();
                    boxF.setWidth(PAS * pieceF.largeur);
                    boxF.setHeight(PAS_H * pieceF.hauteur);
                    boxF.setDepth(PAS * pieceF.longueur);
                    int i1 = this.connecteursM[i][j].connecteurF.i;
                    int j1 = this.connecteursM[i][j].connecteurF.j;
                    double depx, depy, depz;
                    depy = boxM.translateYProperty().get() - PAS_H;
                    boxF.translateYProperty().set(depy);
                    depx = boxM.translateXProperty().get() + ((j - j1) + (pieceF.largeur - this.largeur) / 2.0) * PAS;
                    boxF.translateXProperty().set(depx);
                    depz = boxM.translateZProperty().get() + ((i1 - i) + (this.longueur - pieceF.longueur) / 2.0) * PAS;
                    boxF.translateZProperty().set(depz);
                    material = new PhongMaterial();
                    material.setDiffuseColor(pieceF.couleur);
                    material.setSpecularColor(pieceF.couleur);
                    boxF.setMaterial(material);
                    boxes.add(boxF);
                    
                    pieceF.generate3DBoxes(boxF.translateXProperty().get(),boxF.translateYProperty().get(),boxF.translateZProperty().get());
                }
            }
        }

        return boxes;
	}
	
	 public void generate3DBoxesRecursivly(double translateX, double translateY, double translateZ,ArrayList<Box> boxes) {
	        Box boxM = new Box();
	        PhongMaterial material = new PhongMaterial();
	        material.setDiffuseColor(this.couleur);
	        material.setSpecularColor(this.couleur);
	        boxM.setMaterial(material);

	        boxM.setWidth(PAS * this.largeur);
	        boxM.setHeight(PAS_H * this.hauteur);
	        boxM.setDepth(PAS * this.longueur);
	        boxM.translateXProperty().set(translateX);
	        boxM.translateYProperty().set(translateY);
	        boxM.translateZProperty().set(translateZ);

	        boxes.add(boxM);

	        for (int i = 0; i < this.connecteursM.length; i++) {
	            for (int j = 0; j < this.connecteursM[i].length; j++) {
	                if (this.connecteursM[i][j] != null) {
	                    Piece pieceF = this.connecteursM[i][j].pieceFemelle;
	                    int i1 = this.connecteursM[i][j].connecteurF.i;
	                    int j1 = this.connecteursM[i][j].connecteurF.j;
	                    double depx, depy, depz;
	                    depy = boxM.translateYProperty().get() - PAS_H;
	                    depx = boxM.translateXProperty().get() + ((j - j1) + (pieceF.largeur - this.largeur) / 2.0) * PAS;
	                    depz = boxM.translateZProperty().get() + ((i1 - i) + (this.longueur - pieceF.longueur) / 2.0) * PAS;
	                   
	                    pieceF.generate3DBoxesRecursivly(depx,depy,depz,boxes);
	                }
	            }
	        }
	    }
	
	
    //public String toString() {
    //	return this.x + "," + this.y + "," + this.z;
    //}
}
