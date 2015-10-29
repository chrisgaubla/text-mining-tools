/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package icdmatcher;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author chrisgaubla
 */
public class ICDMatcher {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        PrintWriter result;
        try {
            result = new PrintWriter("data/outputMatching.txt");

            MedDocSet historyBuilder = new MedDocSet("data/japanese_med_rec.xml");
            HashMap<Integer, String> history = historyBuilder.getHistorySet();

            Dictionary dicoBuilder = new Dictionary("data/DictionaryFINAL2.csv");
            HashMap<String, ArrayList<Pattern>> dico = dicoBuilder.getDictionary();

            HashMap<Integer, ArrayList<String>> matching = getMatching(dico, history);

            for (Integer id : matching.keySet()) {
                result.print(id + " : \n");
                for (String code : matching.get(id)){
                    result.print(code + "\n");
                }
                System.out.println("\n");
            }
            result.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ICDMatcher.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private static HashMap<Integer, ArrayList<String>> getMatching(HashMap<String, ArrayList<Pattern>> dico, HashMap<Integer, String> history) {
        HashMap<Integer, ArrayList<String>> matching = new HashMap<>();
        for (Integer key : history.keySet()) {
            String input = history.get(key);
            ArrayList<String> icdList = new ArrayList<>();
            for (String icd : dico.keySet()) {
                ArrayList<Pattern> terms = dico.get(icd);

                
                    for (int i = 1; i < terms.size(); i++) {
                        Matcher matcher = terms.get(i).matcher(input);
                        if (matcher.find()) {
                            icdList.add(icd);
                            break;
                        }
                    }
                

            }
            System.out.println("done history # " + key);
            matching.put(key, icdList);
        }
        return matching;
    }

}
