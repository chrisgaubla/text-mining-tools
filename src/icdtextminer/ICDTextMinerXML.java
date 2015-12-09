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
public class ICDTextMinerXML {

    /**
     * @param args the command line arguments
     */
    public void main(String[] args) {
        PrintWriter result;
        try {
            result = new PrintWriter("data/outputMatchingSenSyn.txt");

            ICDMinerBasic basMiner = new ICDMinerBasic();
            ICDMinerSentence senMiner = new ICDMinerSentence();

            MedDocSet historyBuilder = new MedDocSet("data/japanese_med_rec.xml");
            HashMap<Integer, String> history = historyBuilder.getHistorySet();
            HashMap<Integer, ArrayList<String>> correctMatching = historyBuilder.getICDSet();

            HashMap<Integer, ArrayList<String>> senMatching = getXMLMatching(senMiner, history);
            HashMap<Integer, ArrayList<String>> basMatching = getXMLMatching(basMiner, history);

            ICDTextMinerXmlEvaluator eval = new ICDTextMinerXmlEvaluator();

            eval.evaluate(senMatching, correctMatching);

            for (Integer id : senMatching.keySet()) {
                result.print(id + " : \n");
                for (String code : senMatching.get(id)) {
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
            Logger.getLogger(ICDTextMinerXML.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public HashMap<Integer, ArrayList<String>> getXMLMatching(ICDMiner miner, HashMap<Integer, String> history) {
        HashMap<Integer, ArrayList<String>> matching = new HashMap<>();

        for (Integer key : history.keySet()) {
            matching.put(key, miner.getMatching(history.get(key)));
        }
        return matching;

    }

}
