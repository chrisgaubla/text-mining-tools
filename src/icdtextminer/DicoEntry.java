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

    private final String code;
    private final String description;
    private final String descriptionModified;
    private final ArrayList<Pattern> tokenizedDescription;
    private final ArrayList<Pattern> synonyms;
    private final Pattern descriptionModifiedPattern;

    public DicoEntry(String code, String description, String descriptionModified, ArrayList<Pattern> tokenizedDescription, ArrayList<Pattern> synonyms, Pattern descriptionPattern) {
        this.code = code;
        this.description = description;
        this.descriptionModified = descriptionModified;
        this.tokenizedDescription = tokenizedDescription;
        this.synonyms = synonyms;
        this.descriptionModifiedPattern = descriptionPattern;

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
        return descriptionModifiedPattern;
    }

}
