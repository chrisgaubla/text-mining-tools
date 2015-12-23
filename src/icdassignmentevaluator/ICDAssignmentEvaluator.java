/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package icdassignmentevaluator;

import com.aliasi.sentences.MedlineSentenceModel;
import com.aliasi.sentences.SentenceModel;
import com.aliasi.tokenizer.IndoEuropeanTokenizerFactory;
import com.aliasi.tokenizer.TokenizerFactory;
import icdtextminer.DicoEntry;
import icdtextminer.DicoEntrySentence;
import icdtextminer.Dictionary;
import icdtextminer.DictionaryBuilder;
import icdtextminer.SentenceSplitter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author chrisgaubla
 */
public class ICDAssignmentEvaluator {

    private DictionaryBuilder dicoBuilder;
    private Dictionary dico;
    private final TokenizerFactory TOKENIZER_FACTORY = IndoEuropeanTokenizerFactory.INSTANCE;
    private final SentenceModel SENTENCE_MODEL = new MedlineSentenceModel();
    private final SentenceSplitter splitter = new SentenceSplitter();
    private final int WINDOW = 3;

    public ICDAssignmentEvaluator() {
        this.dicoBuilder = new DictionaryBuilder("Dictionary");
        this.dico = dicoBuilder.getDictionaryObject();

    }

    public String evaluateList(String history, String icd) {
        ArrayList<Double> scores = new ArrayList<>();
        String[] icdArray = icd.split(";");
        for (int i = 0; i < icdArray.length; i++) {
            DicoEntry entry = dico.getEntry(icdArray[i].trim().toLowerCase());
            double score = evaluate(history, entry);
            icdArray[i] = icdArray[i] + " : " + score;
        }
        return Arrays.toString(icdArray);

    }

    public double evaluate(String history, DicoEntry entry) {
        double score =0.0;
        ArrayList<String> listSentence = splitter.getChunk(history);
        
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
                            score = Math.max(score, 1);
                            break;
                        }
                    }
                }
                break;
            case 2:
                for (int i = 0; i <= Math.max(entrySenList.size() - WINDOW,0); i++) {
                    String foundSentenceTemp = "";
                    int foundNum = 0;
                    for (int k = 0; k < entrySenList.get(0).getMatchList().size(); k++) {
                        for (int j = i; j < Math.min(WINDOW,entrySenList.size()); j++) { 
                            if (entrySenList.get(j).getMatchList().get(k).find()) {
                                foundNum++;
                                foundSentenceTemp = foundSentenceTemp + " ; " + entrySenList.get(j).getSentence();
                                score = Math.max(score, foundNum/2);
                                
                            }
                        }
                    }
                }
                break;
            case 3:
                for (int i = 0; i <= Math.max(entrySenList.size() - WINDOW, 0); i++) {
                    String foundSentenceTemp = "";
                    int foundNum = 0;
                    for (int k = 0; k < entrySenList.get(0).getMatchList().size(); k++) {
                        for (int j = i; j < Math.min(WINDOW, entrySenList.size()); j++) {
                            if (entrySenList.get(j).getMatchList().get(k).find()) {
                                foundNum++;
                                foundSentenceTemp = foundSentenceTemp + entrySenList.get(j).getSentence() + " ; ";
                                score = Math.max(score, foundNum/3);
                                break;
                            }
                        }
                    }
                }
                break;
            default:
                for (DicoEntrySentence enSen : entrySenList) {
                    Matcher matchDescription = entry.getDescriptionPattern().matcher(enSen.getSentence());
                    if (matchDescription.find()) {
                        score = 1;
                    }
                }

        }

        
        return score;
    }
}
