/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package icdtextminer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author chrisgaubla
 */
public class ICDMinerBasic extends ICDMiner {

    private DictionaryBuilder dicoBuilder = new DictionaryBuilder("Dictionary");
    private HashSet<DicoEntry> dico = dicoBuilder.getDictionaryString();

    @Override
    public ArrayList<String> getMatching(String history) {
        ArrayList<String> icdList = new ArrayList<>();
        for (DicoEntry entry : dico) {
            boolean found = false;

            ArrayList<Matcher> matcherList = new ArrayList<>();

            matcherList.add(entry.getDescriptionPattern().matcher(history));
            for (Pattern pat : entry.getSynonyms()) {
                matcherList.add(pat.matcher(history));
            }

            for (Matcher matcher : matcherList) {
                if (matcher.find()) {
                    found = true;
                }
            }
            if (found) {
                icdList.add(entry.getCode() + " : " + entry.getDescription()+"<br/>");
                
            }
            
        }
        Collections.sort(icdList, String.CASE_INSENSITIVE_ORDER);
        return icdList;
    }
}