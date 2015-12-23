/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package icdassignmentevaluator;

/**
 *
 * @author chrisgaubla
 */
public class TestEvaluator {
    
    public static void main(String[] args){
        ICDAssignmentEvaluator evaluator = new ICDAssignmentEvaluator();
        
        System.out.println(evaluator.evaluateList("Coucou I have pneumonia and cholera. Also my lung disease is caused by typhoid.", "P23;p239;a00;a0103"));
    }
}
