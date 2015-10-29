/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package icdmatcher;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Pattern;

/**
 *
 * @author chrisgaubla
 */
public class Dictionary {

    private final String path;
    private final String specialChar = ",|;|\\.|:|(|)|\\[|\\]";


    public Dictionary(String path) {
        this.path = path;
    }

    public HashMap<String, ArrayList<Pattern>> getDictionary() {
        HashMap<String, ArrayList<Pattern>> ICDSet = new HashMap<>();

        InputStream stream;
        try {

            stream = new FileInputStream(new File(path));
            BufferedReader reader = new BufferedReader(new InputStreamReader(stream));

            String line;
            int i = 0;
            System.out.println("Loading Dictionary...");
            while ((line = reader.readLine()) != null) {

                String code = line.substring(0, 9).trim();
                ICDSet.put(code, parseICDLine(line));
                if (i % 10000 == 0) {
                    System.out.print("--");
                }
                i++;
            }
            System.out.println("\n Dictionary loaded !");

        } catch (FileNotFoundException e) {

            System.out.println("Wrong path !");
            e.printStackTrace();

        } catch (IOException e) {
            System.out.println("IO exception");
            e.printStackTrace();
        }

        return ICDSet;
    }

    private ArrayList<Pattern> parseICDLine(String line) {
        ArrayList<Pattern> patternList = new ArrayList<>();

        String[] lineArray = line.split("\t");
        
        patternList.add(Pattern.compile(toRegex(lineArray[1])));
        patternList.add(Pattern.compile(toRegex(lineArray[2])));

        if (!lineArray[6].equals("null")) {

            String[] synonyms = lineArray[6].split(";");

            for (String synonym : synonyms) {
                patternList.add(Pattern.compile(toRegex(synonym), Pattern.CASE_INSENSITIVE));
            }
        }
        return patternList;

    }
    private String toRegex(String s){
        s = s.replaceAll(specialChar, "");
        s = "\\b"+s.replaceAll("\\W", "\\\\W")+"\\b";
        System.out.println(s);
        return s;
    }
}
