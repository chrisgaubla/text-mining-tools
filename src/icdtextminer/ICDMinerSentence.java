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
import java.util.Collections;
import java.util.HashSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author chrisgaubla
 */
public class ICDMinerSentence extends ICDMiner {

    private DictionaryBuilder dicoBuilder = new DictionaryBuilder("Dictionary");

    private HashSet<DicoEntry> dico = dicoBuilder.getDictionaryString();
    private final TokenizerFactory TOKENIZER_FACTORY = IndoEuropeanTokenizerFactory.INSTANCE;
    private final SentenceModel SENTENCE_MODEL = new MedlineSentenceModel();
    SentenceSplitter splitter = new SentenceSplitter();
    private final int WINDOW = 3;
    

    @Override
    public ArrayList<MatchedCode> getMatching(String history) {

        ArrayList<MatchedCode> icdList = new ArrayList<>();
        ArrayList<String> listSentence = splitter.getChunk(history);

        for (DicoEntry entry : dico) {
            String foundSentenceList = "";
            boolean found = false;
            ArrayList<Pattern> listToken = entry.getTokenizedDescription();
            ArrayList<Pattern> listSynToken = entry.getSynonyms();
            ArrayList<DicoEntrySentence> entrySenList = new ArrayList<>();

            for (String sentence : listSentence) {
                ArrayList<Matcher> matchList = new ArrayList<>();
                ArrayList<Matcher> matchSynList = new ArrayList<>();

                for (Pattern pat : listToken) {
                    matchList.add(pat.matcher(sentence));
                }
                for (Pattern pat : listSynToken) {
                    matchSynList.add(pat.matcher(sentence));
                }
                entrySenList.add(new DicoEntrySentence(sentence, entry, matchList, matchSynList));

            }

            switch (listToken.size()) {
                case 1:
                    for (DicoEntrySentence entrySentence : entrySenList) {
                        for (Matcher m : entrySentence.getMatchList()) {
                            if (m.find()) {
                                found = true;
                                foundSentenceList = entrySentence.getSentence();
                                break;
                            }
                        }
                    }
                case 2:
                    for (int i = 0; i <= entrySenList.size() - WINDOW; i++) {
                        String foundSentenceTemp = "";
                        int foundNum = 0;
                        for (int k = 0; k < entrySenList.get(0).getMatchList().size(); k++) {
                            for (int j = i; j <= WINDOW; j++) {
                                if (entrySenList.get(j).getMatchList().get(k).find()) {
                                    foundNum++;
                                    foundSentenceTemp = foundSentenceTemp + " ; " + entrySenList.get(j).getSentence();
                                    if (foundNum == entrySenList.get(0).getMatchList().size()) {
                                        found = true;
                                        foundSentenceList = foundSentenceTemp;
                                    }
                                    break;
                                }
                            }
                        }
                    }
                case 3:
                    for (int i = 0; i <= entrySenList.size() - WINDOW; i++) {
                        String foundSentenceTemp = "";
                        int foundNum = 0;
                        for (int k = 0; k < entrySenList.get(0).getMatchList().size(); k++) {
                            for (int j = i; j <= WINDOW; j++) {
                                if (entrySenList.get(j).getMatchList().get(k).find()) {
                                    foundNum++;
                                    foundSentenceTemp = foundSentenceTemp + entrySenList.get(j).getSentence()+" ; ";
                                    if (foundNum == entrySenList.get(0).getMatchList().size()) {
                                        found = true;
                                        foundSentenceList = foundSentenceTemp;
                                    }
                                    break;
                                }
                            }
                        }
                    }
                default:
                    for (DicoEntrySentence enSen : entrySenList) {
                        Matcher matchDescription = entry.getDescriptionPattern().matcher(enSen.getSentence());
                        if(matchDescription.find()){
                            found = true;
                            foundSentenceList = enSen.getSentence();
                        }
                    }

            }
            
            if(!found){
                
                for (DicoEntrySentence l : entrySenList) {
                    for (Matcher m : l.getMatchSynList()) {
                        if (m.find()) {
                            found = true;
                            foundSentenceList = l.getSentence();
                        }
                    }
                }
            }
            if (found) {
                icdList.add(new MatchedCode(entry, history, "<em>"+entry.getCode() + " : </em> <strong>" + entry.getDescription() + " : </strong> "+ foundSentenceList+ "<br/>"));
                System.out.println("Found icd : " + entry.getCode() + " in history");
            }
        }

        return icdList;
    }

}
