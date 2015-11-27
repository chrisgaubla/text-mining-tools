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
public class SoapICDTextMiner {



    public String getICDFromString(String input) {
        String result;
        StringICDMiner miner = new StringICDMiner();
        result = "The matching ICD codes for this string are : \n";
        result = result + miner.getMatching(input).toString();
        
        return result;

    }

}
