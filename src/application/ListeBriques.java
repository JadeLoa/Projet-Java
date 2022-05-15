package application;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class ListeBriques {
	public static final Map<String, int[]> briques;
	static {
		Map<String, int[]> aMap = new HashMap<String, int[]>();
		String[] nomsB = { "1x1x1", "1x2x1", "1x4x1", "2x2x1", "2x4x1", "2x2x4" };
		int[][] tailleBriques = { { 1, 1, 1 }, { 1, 2, 1 }, { 1, 4, 1 }, { 2, 2, 1 }, { 2, 4, 1 }, { 2, 2, 4 } };
		for (int i = 0; i < nomsB.length; i++) {
			aMap.put(nomsB[i], tailleBriques[i]);
		}
		briques = Collections.unmodifiableMap(aMap);
	}

	public String filtre(String largeur, String longueur, String hauteur) {
		return "";
	}
}
