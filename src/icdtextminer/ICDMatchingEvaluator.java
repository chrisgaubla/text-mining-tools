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
        double precision = precision(input, correct);
        double precisionMulti = precisionMulti(input, correct);
        double recall = recall(input, correct);
        double recallMulti = recallMulti(input, correct);
        double fScore = fScore(precision, recall);
    }
    
    private double fScore(double precision, double recall){
        double fScore = (2*(precision*recall))/(precision+recall);
        System.out.println("The F-Score for precision and recall is : "+fScore);
        return fScore;
        
    }
    
    private double precisionMulti(HashMap<Integer, ArrayList<String>> input, HashMap<Integer, ArrayList<String>> correct) {

        double sumPrecision = 0.0;
        for (Integer id : input.keySet()) {
            double numICDAssigned = 0.0;
            double numICDCorrectlyAssigned = 0.0;

            for (String code : input.get(id)) {
                numICDAssigned++;
                for (String codeTrue : correct.get(id)) {

                    if (code.equals(codeTrue)) {
                        numICDCorrectlyAssigned++;
                        break;
                    }
                }

            }
            sumPrecision = sumPrecision + (numICDAssigned / numICDCorrectlyAssigned);
        }
        double precisionMulti = sumPrecision / input.keySet().size();

        System.out.println("The mean of the sum of precisions is : "+sumPrecision);

        return sumPrecision;
    }

    private double recallMulti(HashMap<Integer, ArrayList<String>> input, HashMap<Integer, ArrayList<String>> correct) {
        double sumRecall = 0.0;

        for (Integer id : correct.keySet()) {
            double numICDCorrectlyAssigned = 0.0;
            double numICDToAssigned = 0.0;

            for (String code : correct.get(id)) {
                numICDToAssigned++;
                for (String codeAssigned : input.get(id)) {
                    if (code.equals(codeAssigned)) {
                        numICDCorrectlyAssigned++;
                        break;
                    }
                }

            }
            sumRecall = sumRecall + numICDCorrectlyAssigned / numICDToAssigned;
        }
        double recallMulti = sumRecall / input.keySet().size();
        System.out.println("The mean of the sums of recall is : "+ sumRecall);

        return sumRecall;
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

    private double recall(HashMap<Integer, ArrayList<String>> input, HashMap<Integer, ArrayList<String>> correct) {
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
        recall = numICDCorrectlyAssigned / numICDToAssigned;
        System.out.println("# of ICD to assigned : " + numICDToAssigned + "\n" + "#of ICD correctly assigned : " + numICDCorrectlyAssigned + "\n" + "Recall : " + recall);

        return recall;
    }

}
