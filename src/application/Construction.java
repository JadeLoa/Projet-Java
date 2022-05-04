package application;

import java.util.LinkedList;

public class Construction {
	
	public String nomConstruction;
	public LinkedList<Brique> listeBrique;
	
	public Construction(String nomC) {
		this.nomConstruction=nomC;
		this.listeBrique = new LinkedList<>();
	}

}
