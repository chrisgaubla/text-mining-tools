/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package icdcounter;

import com.sun.xml.internal.ws.util.StringUtils;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author christophe
 */
public class LineMatcher {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        PrintWriter out = null;
        try {
            out = new PrintWriter("data/icdFoundSynList.txt");
            String path1 = "data/DictionaryFINAL2.csv";
            String path2 = "data/UniqueICDHUG.txt";
            int precision = 3;
            try {
                List<String> dictionary = Files.readAllLines(Paths.get(path1),
                        StandardCharsets.UTF_8);
                List<String> toTest = Files.readAllLines(Paths.get(path2),
                        StandardCharsets.UTF_8);
                ArrayList<String[]> dicList = new ArrayList<>();

                for (String line : dictionary) {
                    String[] dicLine = line.split("\t");
                    dicLine[0] = dicLine[0].trim();
                    dicList.add(dicLine);
                }

                int i = 0;
                int j = 0;
                for (String icdToTest : toTest) {

                    if (precision != 0 && icdToTest.length() > precision) {
                        icdToTest = icdToTest.substring(0, precision);
                    }
                    Pattern icdPattern = Pattern.compile("^" + icdToTest + "$");

                    for (String[] dicICD : dicList) {
                        Matcher match = icdPattern.matcher(dicICD[0]);
                        if (match.find()) {
                            i++;
                            if (!dicICD[6].equals("null")) {
                                j++;
                                out.println(dicICD[0]);
                                break;
                            }
                        }
                    }

                }
                int numHeadingTerms = 0;
                int headTermsSyn = 0;
                int headTermWiki = 0;
                int overallSyn = 0;
                for (String[] dicICD : dicList) {
                    if (dicICD[0].trim().length() == 3) {
                        if (!dicICD[8].equals("null")) {
                            headTermWiki++;
                        }
                        headTermsSyn++;
                        if (!dicICD[6].equals("null")) {
                            overallSyn++;
                            if (dicICD[1].contains("[")) {
                                if (dicICD[6].length() - dicICD[6].replace(";", "").length() > 1) {
                                    numHeadingTerms++;
                                }

                            } else {
                                numHeadingTerms++;
                            }
                        }
                    }
                }
                System.out.println("With " + precision + " characters precision. \n Over " + toTest.size() + " ICD codes to test, "
                        + i + " of them were in the dictionary \n and " + j + " got synonyms.");
                System.out.println("Over " + headTermsSyn + " three letter codes, " + numHeadingTerms + " got synonyms without counting brackets. and "
                        + headTermWiki + " got wikicontent and overall syn is " + overallSyn);

            } catch (FileNotFoundException ex) {
                Logger.getLogger(LineMatcher.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(LineMatcher.class.getName()).log(Level.SEVERE, null, ex);
            }

        } catch (FileNotFoundException ex) {
            Logger.getLogger(LineMatcher.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            out.close();
        }
    }

}
