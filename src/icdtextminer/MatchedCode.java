/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package icdtextminer;

/**
 *
 * @author chrisgaubla
 */
public class MatchedCode {
    
    private DicoEntry entry;
    private String sentence;
    private String webString;

    public MatchedCode(DicoEntry entry, String sentence, String webString) {
        this.entry = entry;
        this.sentence = sentence;
        this.webString = webString;
    }

    public DicoEntry getEntry() {
        return entry;
    }

    public String getSentence() {
        return sentence;
    }

    public String getWebString() {
        return webString;
    }
    
    
    
}
