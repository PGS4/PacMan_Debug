package cz.PacMan.debug.main;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.List;


public class Model {

	private static Hashtable<List<Integer>, String> map = new Hashtable<List<Integer>, String>();

	public Model() {
		
	}

	public static void newMap() {
		String line;
		ArrayList<String> lines = new ArrayList<String>();
		line = "#############";
		lines.add(line);
		line = "#############";
		lines.add(line);
		line = "##         ##";
		lines.add(line);
		line = "## ## # ## ##";
		lines.add(line);
		line = "## #     # ##";
		lines.add(line);
		line = "## # #S# # ##";
		lines.add(line);
		line = "## #     # ##";
		lines.add(line);
		line = "##         ##";
		lines.add(line);
		line = "#############";
		lines.add(line);
		line = "#############";
		lines.add(line);
		for (int y = 0; y < lines.size(); y++) {
			for (int x = 0; x < lines.get(y).length(); x++) {
				/*
				 * if (y == 0 || y == 8 - 1 || x == 0 || x == 11 - 1) {
				 * List<Integer> souradnice = Arraye.asList(x, y);
				 * map.put(souradnice, "#"); } else { List<Integer> souradnice =
				 * Arraye.asList(x, y); map.put(souradnice, " "); }
				 */
				map.put(Arrays.asList(x, y),
						String.valueOf(lines.get(y).charAt(x)));
			}
		}
	}

	public static Hashtable<List<Integer>, String> getMap() {
		return map;
	}

	public static void putToMap(List<Integer> key, String value) {
		map.put(key, value);
	}
}
