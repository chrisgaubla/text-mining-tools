/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package icdtextminer;

import com.aliasi.tokenizer.IndoEuropeanTokenizerFactory;
import com.aliasi.tokenizer.TokenizerFactory;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.regex.Pattern;

/**
 *
 * @author chrisgaubla
 */
public class DictionaryBuilder {

    private final TokenizerFactory TOKENIZER_FACTORY = IndoEuropeanTokenizerFactory.INSTANCE;

    private final String path;
    private final String specialChar = ",|;|\\.|:|(|)|\\[|\\]";
    private final CommonWordFilter FILTER = new CommonWordFilter();

    public DictionaryBuilder(String path) {
        this.path = path;
    }

    public HashMap<String, ArrayList<Pattern>> getDictionaryPattern() {
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
                ICDSet.put(code, parseICDLinePattern(line));
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

    public HashSet<DicoEntry> getDictionaryString() {

        HashSet<DicoEntry> dicoEntrySet = new HashSet<>();
        InputStream stream;
        try {

            stream = new FileInputStream(new File(path));
            BufferedReader reader = new BufferedReader(new InputStreamReader(stream));

            String line;
            int i = 0;
            System.out.println("Loading Dictionary...");
            while ((line = reader.readLine()) != null) {

                dicoEntrySet.add(parseICDLineToDicoEntry(line));
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

        return dicoEntrySet;
    }

    private DicoEntry parseICDLineToDicoEntry(String line) {
        DicoEntry entry;
        ArrayList<Pattern> tokenizedDescription;
        Pattern descriptionPattern;

        String code = line.substring(0, 9).trim();
        String[] lineArray = line.split("\t");

        String description = lineArray[1].replaceAll(specialChar, "");
        String descriptionModified = lineArray[2].replaceAll(specialChar, "");
        tokenizedDescription = buildTokenizedDescription(descriptionModified);
        descriptionPattern = Pattern.compile(toRegex(descriptionModified));
        
        ArrayList<Pattern> synonyms = new ArrayList<>();
        if (!lineArray[6].equals("null")) {
            String[] synArray = lineArray[6].split(";");

            for (String synonym : synArray) {
                synonyms.add(Pattern.compile(toRegex(synonym.replaceAll(specialChar, ""))));
            }
        }
        entry = new DicoEntry(code, description, descriptionModified, tokenizedDescription, synonyms, descriptionPattern);
        System.out.println(entry.getCode());
        return entry;

    }

    private ArrayList<Pattern> parseICDLinePattern(String line) {
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

    private String toRegex(String s) {
        s = s.replaceAll(specialChar, "");
        s = "\\b" + s.replaceAll("\\W", "\\\\W") + "\\b";
        return s;
    }

    private ArrayList<Pattern> buildTokenizedDescription(String descriptionModified) {
        ArrayList<Pattern> listPattern = new ArrayList<>();
        ArrayList<String> listWord = FILTER.getFilteredStringList(descriptionModified);
        for(String s :  listWord){
            listPattern.add(Pattern.compile(toRegex(s)));
        }
        return listPattern;
                
    }

}
