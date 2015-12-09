/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package icdtextminer;

import java.util.ArrayList;
import java.util.regex.Matcher;

/**
 *
 * @author chrisgaubla
 */
public class DicoEntrySentence {
    private String sentence;
    private DicoEntry entry;
    private ArrayList<Matcher> matchList;
    private ArrayList<Matcher> matchSynList;

    public DicoEntrySentence(String sentence, DicoEntry entry, ArrayList<Matcher> matchList, ArrayList<Matcher> matchSynList) {
        this.sentence = sentence;
        this.entry = entry;
        this.matchList = matchList;
        this.matchSynList = matchSynList;
    }

    public DicoEntry getEntry() {
        return entry;
    }

    public ArrayList<Matcher> getMatchList() {
        return matchList;
    }

    public ArrayList<Matcher> getMatchSynList() {
        return matchSynList;
    }

    public String getSentence() {
        return sentence;
    }
    
    
}
