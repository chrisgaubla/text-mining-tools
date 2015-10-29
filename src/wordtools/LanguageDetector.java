package wordtools;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

public class LanguageDetector {

	public String urlDetector = "http://pingu.unige.ch:8080/LanguageDetector/rest/detector/";

	public LanguageDetector() {

	}

	/**
	 * Method that take a String as an argument and return a 2 character String
	 * containing the language of the text
	 * 
	 * @param text
	 *            The text you want to test
	 * @return The language (two letters)
	 * @throws Exception
	 */
	public String sendGet(String text) {

		String urlFormatted = urlDetector + text.replaceAll("\\s", "%20");
		URL lang;
		String wholePage = "";
		
		try {
			lang = new URL(urlFormatted);
			BufferedReader in = new BufferedReader(new InputStreamReader(
					lang.openStream()));

			String inputLine;
			while ((inputLine = in.readLine()) != null)
				wholePage = wholePage + inputLine;
			in.close();

		} 
		
		catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return wholePage.substring(17, 19);

	}
}
