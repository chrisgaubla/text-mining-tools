/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wikicruncher;

/**
 *
 * @author chrisgaubla
 */
public class WikiCruncher {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        ICDListBuilder builder = new ICDListBuilder();        
        
        builder.getICDListFromPath("data/UnspecifiedLines.txt");
//        BodyPartsMapper body = new BodyPartsMapper("mesh/BodyRegions.txt");
//        body.bodyMap("data/icd10cm_order_2015.txt");
        
    }
    
    
}
