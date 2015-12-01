/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package icdtextminer;

import com.aliasi.sentences.MedlineSentenceModel;
import com.aliasi.sentences.SentenceModel;
import com.aliasi.tokenizer.IndoEuropeanTokenizerFactory;
import com.aliasi.tokenizer.TokenizerFactory;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author chrisgaubla
 */
public class StringICDMiner {

    private DictionaryBuilder dicoBuilder = new DictionaryBuilder("Dictionary");

    private HashSet<DicoEntry> dico = dicoBuilder.getDictionaryString();
    private final TokenizerFactory TOKENIZER_FACTORY = IndoEuropeanTokenizerFactory.INSTANCE;
    private final SentenceModel SENTENCE_MODEL = new MedlineSentenceModel();
    SentenceSplitter splitter = new SentenceSplitter();
    private final int WINDOW = 3;

    public ArrayList<String> getMatching(String history) {
        HashMap<Integer, ArrayList<String>> matching = new HashMap<>();

        ArrayList<String> icdList = new ArrayList<>();
        ArrayList<String> listSentence = splitter.getChunk(history);

        for (DicoEntry entry : dico) {
            boolean found = false;
            ArrayList<Pattern> listToken = entry.getTokenizedDescription();
            ArrayList<Pattern> listSynToken = entry.getSynonyms();
            ArrayList<ArrayList<Matcher>> matchListList = new ArrayList<>();
            ArrayList<ArrayList<Matcher>> matchSynListList = new ArrayList<>();

            for (String sentence : listSentence) {
                ArrayList<Matcher> matchList = new ArrayList<>();
                ArrayList<Matcher> matchSynList = new ArrayList<>();

                for (Pattern pat : listToken) {
                    matchList.add(pat.matcher(sentence));
                }
                for (Pattern pat : listSynToken) {
                    matchSynList.add(pat.matcher(sentence));
                }
                matchSynListList.add(matchSynList);
                matchListList.add(matchList);

            }

            switch (listToken.size()) {
                case 1:
                    for (ArrayList<Matcher> mList : matchListList) {
                        for (Matcher m : mList) {
                            if (m.find()) {
                                found = true;

                                break;
                            }
                        }
                    }
                case 2:
                    for (int i = 0; i <= listSentence.size() - WINDOW; i++) {
                        int foundNum = 0;
                        for (int k = 0; k < matchListList.get(0).size(); k++) {
                            for (int j = i; j <= WINDOW; j++) {
                                if (matchListList.get(j).get(k).find()) {
                                    foundNum++;

                                    break;
                                }
                            }
                        }
                        if (foundNum == matchListList.get(0).size()) {
                            found = true;

                            break;
                        }
                    }
                case 3:
                    for (int i = 0; i <= listSentence.size() - WINDOW; i++) {
                        int foundNum = 0;
                        for (int k = 0; k < matchListList.get(0).size(); k++) {
                            for (int j = i; j <= WINDOW; j++) {
                                if (matchListList.get(j).get(k).find()) {
                                    foundNum++;
                                    break;
                                }
                            }
                        }
                        if (foundNum == matchListList.get(0).size()) {
                            found = true;
                            break;
                        }
                    }
                default:
                    Matcher matchDescription = entry.getDescriptionPattern().matcher(history);
                    if (matchDescription.find()) {
                        found = true;
                    }

            }
            for (ArrayList<Matcher> l : matchSynListList) {
                for (Matcher m : l) {
                    if (m.find()) {
                        found = true;
                    }
                }
            }
            if (found) {
                icdList.add(entry.getCode());
                System.out.println("Found icd : " + entry.getCode() + " in history");
            }
        }

        return icdList;
    }

}
