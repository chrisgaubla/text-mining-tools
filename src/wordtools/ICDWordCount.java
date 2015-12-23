/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wordtools;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author chrisgaubla
 */
public class ICDWordCount {

    public void count(String path, PrintWriter out) {
        
        HashMap<String, Integer> wordCountMap = new HashMap<>();

        try {
            FileInputStream stream;
            stream = new FileInputStream(new File(path));
            BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
            
            String line;
            
            while((line = reader.readLine())!=null){
                String[] lineSplitted = line.split("\\t");
                String[] descriptionSplitted = lineSplitted[6].replaceAll(",", "").split(";");
                for(String word : descriptionSplitted){
                    if(wordCountMap.get(word)!=null){
                        wordCountMap.put(word, wordCountMap.get(word)+1);
                    }
                    else{
                        wordCountMap.put(word, 1);
                    }
                }
            }
            
            for(String word : wordCountMap.keySet()){
                out.print(word + "\t"+ wordCountMap.get(word)+"\n");
            }
            
            
            
            
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ICDWordCount.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ICDWordCount.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
