package wordtools;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.TreeMap;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class Dictionnary {
	HashSet<String> stringSet;

	public Dictionnary(String path, String tagName) {

		stringSet = loadSet(path, tagName);
		
	}

	/**
	 * This method returns a map of the matching terms along with a value that
	 * represent the number of occurrences in the text
	 * 
	 * @param stringTest A string array of words you want to check
	 * @return a map of the matching words with # of occurrences
	 */
	public TreeMap<String, Integer> getMatches(String[] stringTest) {
		TreeMap<String, Integer> result = new TreeMap<String, Integer>();

		for (int i = 0; i < stringTest.length; i++) {
			String wordTest = stringTest[i];
			if (stringSet.contains(wordTest)) {
				if (!result.containsKey(wordTest)) {
					result.put(wordTest, 1);
				} else {
					result.put(wordTest, result.get(wordTest) + 1);
				}

			}
		}
		return result;

	}

	/**
	 * 
	 * @param path
	 *            : the path to your xml file
	 * @return returns the set of all string under the label word in your xml
	 *         document
	 */
	public HashSet<String> loadSet(String path, String tagName) {

		File words = new File(path);
		HashSet<String> wordSet = new HashSet<String>();

		DocumentBuilderFactory docbf = DocumentBuilderFactory.newInstance();
		docbf.setNamespaceAware(true);
		DocumentBuilder docbuilder;

		try {

			docbuilder = docbf.newDocumentBuilder();

			Document document;

			document = docbuilder.parse(words);
			NodeList wordsList = document.getElementsByTagName(tagName);

			for (int i = 0; i < wordsList.getLength(); i++) {
				wordSet.add(wordsList.item(i).getFirstChild().getTextContent());
			}

		} catch (ParserConfigurationException e1) {
			e1.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return wordSet;

	}

	public HashSet<String> getStringSet() {
		return stringSet;
	}

}
