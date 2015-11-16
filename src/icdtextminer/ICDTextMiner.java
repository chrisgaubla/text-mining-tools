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
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;

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
            result = new PrintWriter("data/outputMatching.txt");
            
            BasicICDMiner basic = new BasicICDMiner();

            MedDocSet historyBuilder = new MedDocSet("data/japanese_med_rec.xml");
            HashMap<Integer, String> history = historyBuilder.getHistorySet();
            HashMap<Integer, ArrayList<String>> correctMatching = historyBuilder.getICDSet();
            
            Dictionary dicoBuilder = new Dictionary("data/DictionaryFINAL2.csv");
            HashMap<String, ArrayList<Pattern>> dico = dicoBuilder.getDictionary();

            HashMap<Integer, ArrayList<String>> testMatching = basic.getMatching(dico, history);
            
            
            ICDMatchingEvaluator eval = new ICDMatchingEvaluator();
            
            eval.evaluate(testMatching, correctMatching);
           
            for (Integer id : testMatching.keySet()) {
                result.print(id + " : \n");
                for (String code : testMatching.get(id)){
                    result.print(code);
                    for(String codeTrue : correctMatching.get(id)){
                        if(code.equals(codeTrue)){
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
