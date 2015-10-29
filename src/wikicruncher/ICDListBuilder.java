/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wikicruncher;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author chrisgaubla
 */
public class ICDListBuilder {

    private final String SLASH = "(.+ )?(\\S+)/([^-\\s]+)([-\\s].+)?";
    private final String BRACKET = "(.+ )\\[(.+?)\\](.+)?$";
    private final Pattern BRACKET_PATTERN;
    private final Pattern SLASH_PATTERN;
    private Matcher slashMatcher;
    private Matcher bracketMatcher;

    public ICDListBuilder() {
        SLASH_PATTERN = Pattern.compile(SLASH, Pattern.CASE_INSENSITIVE);
        BRACKET_PATTERN = Pattern.compile(BRACKET, Pattern.CASE_INSENSITIVE);
    }

    /**
     * This method take a path to the ICD list txt file and make a query on
     * wikipedia using HTTPGetter.java then output in a txt file
     *
     * @param path
     */
    public void getICDListFromPath(String path) {

        try {

            PrintWriter out;
            out = new PrintWriter("data/outputICDWikiUnspecified.txt");

            HTTPGetter getter = new HTTPGetter(1300);

            String code;
            String description;
            String descriptionModified;
            String wikiContent;

            InputStream stream = new FileInputStream(new File(path));
            BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
            String line;

            while ((line = reader.readLine()) != null) {
                wikiContent = "";
                code = line.substring(6, 14);

                description = line.substring(77, line.length());
                descriptionModified = description;
                descriptionModified = descriptionModified.replaceAll("Unspecified | unspecified|Other specified |Other |,|, other site|of other sites ", "");
                descriptionModified = descriptionModified.replaceAll("infections", "infection");
                slashMatcher = SLASH_PATTERN.matcher(descriptionModified);
                bracketMatcher = BRACKET_PATTERN.matcher(descriptionModified);

                if (slashMatcher.find()
                        && !(descriptionModified.contains("Subluxation of")
                        | descriptionModified.contains("Dislocation of ")
                        | descriptionModified.contains("Blood alcohol level of ")
                        | descriptionModified.contains("Mosaicism")
                        | descriptionModified.contains("Subluxation and dislocation of "))) {
                    System.out.println("SLASHES : ");

                    descriptionModified = slashMatcher.group(1) + slashMatcher.group(3);

                    if (slashMatcher.group(4) != null) {
                        System.out.println(slashMatcher.group(4));
                        descriptionModified = descriptionModified + slashMatcher.group(4);
                    }
                    System.out.println(descriptionModified);
                    wikiContent = getter.sendGet(descriptionModified);
                    out.print(code + "\t" + description + "\t" + descriptionModified + "\t" + wikiContent + "\t" + "null" + "\n");
                    descriptionModified = slashMatcher.group(1) + slashMatcher.group(2);
                    if (slashMatcher.group(4) != null) {
                        descriptionModified = descriptionModified + slashMatcher.group(4);
                    }
                    System.out.println(descriptionModified);
                    wikiContent = getter.sendGet(descriptionModified);
                    out.print(code + "\t" + description + "\t" + descriptionModified + "\t" + wikiContent + "\t" + "null" + "\n");

                } else if (bracketMatcher.find()) {
                    System.out.println("BRACKETS ! ");
                    String bracketContent = bracketMatcher.group(2);
                    if (bracketMatcher.group(3) != null) {
                        descriptionModified = bracketMatcher.group(1) + bracketMatcher.group(3);
                    }
                    else {
                        descriptionModified = bracketMatcher.group(1);
                    }
                    System.out.println("without bracket : " + descriptionModified + "\n bracketContent : " + bracketContent);
                    wikiContent = getter.sendGet(descriptionModified);
                    out.print(code + "\t" + description + "\t" + descriptionModified + "\t" + wikiContent + "\t" + bracketContent + "\n");

                } else {
                    System.out.println("NO SLASHES AND NO BRACKETS!");
                    wikiContent = getter.sendGet(descriptionModified);
                    out.print(code + "\t" + description + "\t" + descriptionModified + "\t" + wikiContent + "\t" + "null" + "\n");
                }

                System.out.println("done line :" + line.substring(0, 6));
            }

            out.close();

        } catch (FileNotFoundException ex) {
            Logger.getLogger(ICDListBuilder.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(ICDListBuilder.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}
