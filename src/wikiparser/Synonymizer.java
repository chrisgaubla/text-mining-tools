/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package wikiparser;

import java.util.HashSet;
import java.util.Set;
import java.util.regex.Pattern;

/**
 *
 * @author Dina
 */
public class Synonymizer {
    
    private Set <Pattern> p;

    public Synonymizer() {
        Set <Pattern> patterns = new HashSet();
        
        patterns.add(Pattern.compile("also known as '''(.+?)'''[\\\\W\\\\w]?\""));
    }
    
    
    
}
