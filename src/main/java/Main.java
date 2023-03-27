import com.fasterxml.jackson.databind.ObjectMapper;
import pojos.ItemInfo;
import pojos.Items;
import pojos.ResearchTree;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Collectors;


public class Main {
	public static Items importUrl(String urlName) {
		Items allitems = new Items();

		try {
			URL url = new URL(urlName);
			InputStream in = url.openStream();
			Scanner scan = new Scanner(in);

			boolean isType = true;
			int indexOffset = 0;
			int line = 1;
			while (scan.hasNext()) {
				String str = scan.nextLine();
				if (str.equals("") && isType) {
					isType = false;
					indexOffset = line;
				}

				if (isType) {
					allitems.addType(str);

				} else if (!str.equals("")) {
					String[] itemArray = str.split("\t");
					ItemInfo itemInfo = new ItemInfo(line - indexOffset, Integer.parseInt(itemArray[0]), Integer.parseInt(itemArray[1]), itemArray[2]);
					allitems.addItem(itemInfo);
				}
				line++;
			}
			scan.close();
		} catch (MalformedURLException e) {
			throw new RuntimeException(e);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		return allitems;
	}

	public static void main(String[] args) {
		Items allitems = importUrl("https://yal.cc/game-tools/terraria-research/data.txt");

		int players = Integer.parseInt(Utility.promptMeasure("How many players? \n"));
		ObjectMapper mapper = new ObjectMapper();
		Map<String, ResearchTree> playerItems = new HashMap<>();
		Map<Integer, Integer> outputMap = new LinkedHashMap<>();

		for (int i = 0; players > i; i++) {
			String fileLoc = Utility.promptMeasure("Enter file location: \n");

			try {
				ResearchTree researchTree = mapper.readValue(new File(fileLoc), ResearchTree.class);

				playerItems.put(fileLoc, researchTree);

				researchTree.getResearch()
						.forEach((key, value) -> outputMap.merge(key, value, (v1, v2) -> {
							try {
								return Math.min(v1 + v2, allitems.getItems().get(key).getAmountNeeded());
							} catch (NullPointerException e) {
								return Math.max(v1, v2);
							}
						}));

			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		ResearchTree outputTree = new ResearchTree();
		outputTree.setResourceType("TerrasavrResearch");
		outputTree.setResourceVersion("1.0");
		outputTree.setResearch(outputMap);

		try {
			mapper.writeValue(new File("foo.json"), outputTree);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		System.out.println("Done");

	}
}



