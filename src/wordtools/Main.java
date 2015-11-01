package wordtools;

import java.util.TreeMap;

public class Main {
	/**
	 * The first argument must be the location of the data to process, the
	 * second argument must be the location of the dictionary
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
            
            BracketCounter brac = new BracketCounter();
            brac.countBracketSyn("data/DictionaryFINAL2.csv");
            
		
//		if(args.length!=2){
//			System.out.println("bad number of argument");
//		}
//		else{
//			
//		
//		String inputPath = args[0];
//		String wordsPath = args[1];
//		String separator = " ";
//		String[] cleaningList = { ",", ".", "'", "(", ")", ";", ":" };
//		String lineExclusion = "#";
//		String tagName = "word";
//
//		Dictionnary comparator = new Dictionnary(wordsPath, tagName);
//
//		Cleaner cleaner = new Cleaner(separator, cleaningList);
//
//		FileToStringBuilder builder = new FileToStringBuilder();
//
//		String text = builder.getStringFromFile(inputPath);
//
//		String[] cleanText = cleaner.cleanText(text, lineExclusion);
//
//		TreeMap<String, Integer> words = comparator.getMatches(cleanText);
//
//		System.out.println(words.toString());
//
//		LanguageDetector lang = new LanguageDetector();
//
//		try {
//			System.out.println(lang.sendGet("salut du con comment tu vas ?"));
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		}

	}

}