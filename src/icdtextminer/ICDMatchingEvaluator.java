/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package icdtextminer;

import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author chrisgaubla
 */
public class ICDMatchingEvaluator {

    public ICDMatchingEvaluator() {

    }

    public void evaluate(HashMap<Integer, ArrayList<String>> input, HashMap<Integer, ArrayList<String>> correct) {
        precision(input, correct);
        recall(input, correct);
    }

    private double precision(HashMap<Integer, ArrayList<String>> input, HashMap<Integer, ArrayList<String>> correct) {
        double numICDAssigned = 0.0;
        double numICDCorrectlyAssigned = 0.0;

        for (Integer id : input.keySet()) {
            for (String code : input.get(id)) {
                numICDAssigned++;
                for (String codeTrue : correct.get(id)) {
                    if (code.equals(codeTrue)) {
                        numICDCorrectlyAssigned++;
                        break;
                    }
                }

            }
        }
        double precision = numICDCorrectlyAssigned / numICDAssigned;

        System.out.println("# of ICD assigned : " + numICDAssigned + "\n" + "#of ICD correctly assigned : " + numICDCorrectlyAssigned + "\n" + "Precision : " + precision);

        return precision;
    }
    private double recall(HashMap<Integer, ArrayList<String>> input, HashMap<Integer, ArrayList<String>> correct){
        double numICDCorrectlyAssigned = 0.0;
        double numICDToAssigned = 0.0;
        double recall;

        for (Integer id : correct.keySet()) {
            for (String code : correct.get(id)) {
                numICDToAssigned++;
                for (String codeAssigned : input.get(id)) {
                    if (code.equals(codeAssigned)) {
                        numICDCorrectlyAssigned++;
                        break;
                    }
                }

            }
        }
        recall = numICDCorrectlyAssigned/numICDToAssigned;
        System.out.println("# of ICD to assigned : " + numICDToAssigned + "\n" + "#of ICD correctly assigned : " + numICDCorrectlyAssigned + "\n" + "Recall : " + recall);

        return recall;
    }

}
