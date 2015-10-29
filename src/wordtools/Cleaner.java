package wordtools;

import java.util.Arrays;

public class Cleaner {
	private String separator;
	private String[] cleaningList;

	public Cleaner(String separator, String[] cleaningList) {
		this.separator = separator;
		this.cleaningList = cleaningList.clone();
	}

	public String[] cleanText(String lines, String lineExclusion) {
		String[] linesArray = lines.split("\\n");
		String[] buildingArray = new String[0];

		String[] tempArray;
		String line;

		for (int i = 0; i < linesArray.length; i++) {
			line = linesArray[i];
			line = cleanWithList(line);
			if (!line.startsWith(lineExclusion)) {
				tempArray = line.split(separator);
				String[] result = Arrays.copyOf(buildingArray,
						buildingArray.length + tempArray.length);
				System.arraycopy(tempArray, 0, result, buildingArray.length,
						tempArray.length);
				buildingArray = result.clone();
			}
		}

		return buildingArray;
	}

	/**
	 * Method that remove from a string all occurrences of the Strings of
	 * cleaningList.
	 * 
	 * @param toClean
	 *            The String to clean
	 * @return the cleaned String
	 */
	private String cleanWithList(String toClean) {
		for (int i = 0; i < cleaningList.length; i++) {
			toClean = toClean.replace(cleaningList[i], "");
		}
		return toClean;

	}
}
