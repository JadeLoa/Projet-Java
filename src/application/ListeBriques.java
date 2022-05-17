package application;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

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

	public static Set<String> filtre(String largeur, String longueur, String hauteur) {
		boolean valid = true;
		Set<String> listeFiltree = new HashSet<>();
		for (String s : ListeBriques.briques.keySet()) {
			valid = true;
			if (largeur.length() != 0 && longueur.length() != 0) {
				try {
					int dim = Integer.parseInt(largeur);
					int dim2 = Integer.parseInt(longueur);
					if (!(dim == ListeBriques.briques.get(s)[0] && dim2 == ListeBriques.briques.get(s)[1]
							|| dim == ListeBriques.briques.get(s)[1] && dim == ListeBriques.briques.get(s)[0])) {
						valid = false;
					}
				} catch (NumberFormatException e) {
					return ListeBriques.briques.keySet();
				} catch (NullPointerException e) {
					return ListeBriques.briques.keySet();
				}
			} else if (largeur.length() != 0) {
				try {
					int dim = Integer.parseInt(largeur);
					if (!(dim == ListeBriques.briques.get(s)[0] || dim == ListeBriques.briques.get(s)[1])) {
						valid = false;
					}
				} catch (NumberFormatException e) {
					return ListeBriques.briques.keySet();
				} catch (NullPointerException e) {
					return ListeBriques.briques.keySet();
				}
			} else if (longueur.length() != 0) {
				try {
					int dim = Integer.parseInt(longueur);
					if (!(dim == ListeBriques.briques.get(s)[0] || dim == ListeBriques.briques.get(s)[1])) {
						valid = false;
					}
				} catch (NumberFormatException e) {
					return ListeBriques.briques.keySet();
				} catch (NullPointerException e) {
					return ListeBriques.briques.keySet();
				}
			}
			if (hauteur.length() != 0) {
				try {
					int dim = Integer.parseInt(hauteur);
					if (dim != ListeBriques.briques.get(s)[2]) {
						valid = false;
					}
				} catch (NumberFormatException e) {
					return ListeBriques.briques.keySet();
				} catch (NullPointerException e) {
					return ListeBriques.briques.keySet();
				}
			}
			if (valid) {
				listeFiltree.add(s);
			}
		}
		return listeFiltree;
	}
}
