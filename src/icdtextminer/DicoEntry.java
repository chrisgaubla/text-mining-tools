/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package icdtextminer;

import java.util.ArrayList;
import java.util.regex.Pattern;

/**
 *
 * @author chrisgaubla
 */
public class DicoEntry {

    private String code;
    private String description;
    private String descriptionModified;
    private ArrayList<Pattern> tokenizedDescription;
    private ArrayList<Pattern> synonyms;
    private Pattern descriptionPattern;

    public DicoEntry(String code, String description, String descriptionModified, ArrayList<Pattern> tokenizedDescription, ArrayList<Pattern> synonyms, Pattern descriptionPattern) {
        this.code = code;
        this.description = description;
        this.descriptionModified = descriptionModified;
        this.tokenizedDescription = tokenizedDescription;
        this.synonyms = synonyms;
        this.descriptionPattern = descriptionPattern;

    }

    public String getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }

    public String getDescriptionModified() {
        return descriptionModified;
    }

    public ArrayList<Pattern> getTokenizedDescription() {
        return tokenizedDescription;
    }

    public ArrayList<Pattern> getSynonyms() {
        return synonyms;
    }

    public Pattern getDescriptionPattern() {
        return descriptionPattern;
    }

}
