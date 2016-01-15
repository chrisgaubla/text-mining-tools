/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package icdtextminer;

import java.util.ArrayList;
import java.util.HashSet;

/**
 *
 * @author chrisgaubla
 */
public abstract class ICDMiner {
    private DictionaryBuilder dicoBuilder;
    private HashSet<DicoEntry> dico;
    
    abstract ArrayList<MatchedCode> getMatching(String history);
}
