/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package wikiparser;

import java.io.IOException;

/**
 *
 * @author Dina
 */
public class Wikiparser {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        // TODO code applicaPation logic here
      TxtFileReader readwriter = new TxtFileReader();
      System.out.println("Start parsing....");
      readwriter.readfile("/Users/Dina/Downloads/OutputICDWiki2.txt", "/Users/Dina/Downloads/");
        
    }
    
}
