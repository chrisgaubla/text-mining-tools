/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package threadedwikicruncher;


/**
 *
 * @author chrisgaubla
 */
public class ThreadedWikiCruncher {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        ThreadedWikiParser builder = new ThreadedWikiParser();        
        
        builder.parseAndQueryWiki("data/LIBCIM10.TXT", "fr");
        
//        BodyPartsMapper body = new BodyPartsMapper("mesh/BodyRegions.txt");
//        body.bodyMap("data/icd10cm_order_2015.txt");
        
    }
    
    
}
