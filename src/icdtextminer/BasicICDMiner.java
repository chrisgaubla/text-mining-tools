/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package icdtextminer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author chrisgaubla
 */
public class BasicICDMiner {
    
    public HashMap<Integer, ArrayList<String>> getMatching(HashMap<String, ArrayList<Pattern>> dico, HashMap<Integer, String> history) {
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

