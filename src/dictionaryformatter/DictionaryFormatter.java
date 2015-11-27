/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dictionaryformatter;

import icdtextminer.CommonWordFilter;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author chrisgaubla
 */
public class DictionaryFormatter {

    public static void main(String[] args) {
        CommonWordFilter filter = new CommonWordFilter();

        try {
            PrintWriter out = new PrintWriter("data/Dictionary");
            InputStream stream = new FileInputStream(new File("data/Dictionary18_11_15.csv"));
            BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
            String line;

            while ((line = reader.readLine()) != null) {
                String[] splitted = line.split("\\t");
                System.out.println(splitted.length);
                String correctedLine = splitted[0] + "\t" + splitted[1] + "\t" + filter.getFilteredString(splitted[1]) + "\t" + splitted[3] + "\t" + splitted[4] + "\t" + splitted[5] + "\t" + splitted[6] + "\t" + splitted[7] ;
                if(splitted.length==9){
                    correctedLine = correctedLine+ "\t" + splitted[8];
                }
                out.println(correctedLine);
                System.out.println("Line "+splitted[0]);
                
            }
            out.close();
            

        } catch (FileNotFoundException ex) {
            Logger.getLogger(DictionaryFormatter.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(DictionaryFormatter.class.getName()).log(Level.SEVERE, null, ex);
        }
       
    }
}
