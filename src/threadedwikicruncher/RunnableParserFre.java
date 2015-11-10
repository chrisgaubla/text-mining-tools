/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package threadedwikicruncher;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author chrisgaubla
 */
public class RunnableParserFre implements Runnable {

    private final HTTPGetter getter;
    private final BufferedReader reader;
    private final PrintWriter out;

    private final String SLASH = "(.+ )?(\\S+)/([^-\\s]+)([-\\s].+)?";
    private final String BRACKET = "(.+ )\\[(.+?)\\](.+)?$";
    private final Pattern BRACKET_PATTERN;
    private final Pattern SLASH_PATTERN;
    private Matcher slashMatcher;
    private Matcher bracketMatcher;

    public RunnableParserFre(HTTPGetter getter, BufferedReader reader, PrintWriter out) {
        this.getter = getter;
        this.reader = reader;
        this.out = out;

        SLASH_PATTERN = Pattern.compile(SLASH, Pattern.CASE_INSENSITIVE);
        BRACKET_PATTERN = Pattern.compile(BRACKET, Pattern.CASE_INSENSITIVE);
    }

    @Override
    public void run() {
        String code;
        String description;
        String descriptionModified;
        String wikiContent;
        String line;

        try {

            while ((line = reader.readLine()) != null) {
                wikiContent = "";
                code = line.substring(0, 6);

                description = line.substring(9, line.length()).split("\\|")[1];

                descriptionModified = description.replaceAll(" non spécifique|Autre |Autres |,| d'autres localisation| d'autres sièges|, sans précision", "");
                descriptionModified = descriptionModified.replaceAll("infections", "infection");
                slashMatcher = SLASH_PATTERN.matcher(descriptionModified);
                bracketMatcher = BRACKET_PATTERN.matcher(descriptionModified);

                if (bracketMatcher.find()) {
                    System.out.println("BRACKETS ! ");
                    String bracketContent = bracketMatcher.group(2);
                    if (bracketMatcher.group(3) != null) {
                        descriptionModified = bracketMatcher.group(1) + bracketMatcher.group(3);
                    } else {
                        descriptionModified = bracketMatcher.group(1);
                    }
                    System.out.println("without bracket : " + descriptionModified + "\n bracketContent : " + bracketContent);
                    wikiContent = getter.sendGet(descriptionModified, "fr");
                    out.print(code + "\t" + description + "\t" + descriptionModified + "\t" + wikiContent + "\t" + bracketContent + "\n");

                } else {
                    System.out.println("NO SLASHES AND NO BRACKETS!");
                    wikiContent = getter.sendGet(descriptionModified, "fr");
                    out.print(code + "\t" + description + "\t" + descriptionModified + "\t" + wikiContent + "\t" + "null" + "\n");
                }

                System.out.println("done line :" + line.substring(0, 6));
            }

        } catch (IOException ex) {
            Logger.getLogger(RunnableParserFre.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(RunnableParserFre.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
