/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package icdtextminer;

import java.util.HashMap;
import java.util.HashSet;

/**
 *
 * @author chrisgaubla
 */
public class Dictionary {
    private HashMap<String,DicoEntry> codeMap;

    public Dictionary(HashSet<DicoEntry> dicoSet) {
        for(DicoEntry entry : dicoSet){
            codeMap.put(entry.getCode(), entry);
        }
    }
    
    public DicoEntry getEntry(String code){
        return codeMap.get(code.trim().toLowerCase());
    }
}
    
    