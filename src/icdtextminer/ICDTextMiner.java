/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package icdtextminer;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author chrisgaubla
 */
public class ICDTextMiner {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        PrintWriter result;
        try {
            result = new PrintWriter("data/outputMatchingSenSyn.txt");

            BasicICDMiner basic = new BasicICDMiner();
            SentenceICDMiner sentence = new SentenceICDMiner();

            MedDocSet historyBuilder = new MedDocSet("data/japanese_med_rec.xml");
            HashMap<Integer, String> history = historyBuilder.getHistorySet();
            HashMap<Integer, ArrayList<String>> correctMatching = historyBuilder.getICDSet();

            DictionaryBuilder dicoBuilder = new DictionaryBuilder("data/Dictionary18_11_15.csv");
            //HashMap<String, ArrayList<Pattern>> dicoPattern = dicoBuilder.getDictionaryPattern();
            HashSet<DicoEntry> dicoString = dicoBuilder.getDictionaryString();

            //HashMap<Integer, ArrayList<String>> testMatchingPat = basic.getMatching(dicoPattern, history);
            HashMap<Integer, ArrayList<String>> testMatchingSen = sentence.getMatching(dicoString, history);

            ICDMatchingEvaluator eval = new ICDMatchingEvaluator();

            eval.evaluate(testMatchingSen, correctMatching);

            for (Integer id : testMatchingSen.keySet()) {
                result.print(id + " : \n");
                for (String code : testMatchingSen.get(id)) {
                    result.print(code);
                    for (String codeTrue : correctMatching.get(id)) {
                        if (code.equals(codeTrue)) {
                            result.print(" correct");
                            break;
                        }
                    }
                    result.print("\n");
                }
                System.out.println("\n");
            }
            result.close();

        } catch (FileNotFoundException ex) {
            Logger.getLogger(ICDTextMiner.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
