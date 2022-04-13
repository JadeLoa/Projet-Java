package application;

import java.io.File;

public class Construction {
	
	public String nomConstruction;
	
	public Construction(String nomC) {
		this.nomConstruction=nomC;
		File listeBrique = new File("listeBrique.txt");
	}

}
