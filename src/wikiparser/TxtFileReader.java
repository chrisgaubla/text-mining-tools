/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wikiparser;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Dina
 */
public class TxtFileReader {
  private      DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy-HH-mm-ss");
  private      Date date = new Date();
  private      FileWriter fstream = null;
      
    public void readfile(String path, String fout) throws IOException {
        Outliner out ;
                
        String outputfile_name = fout + dateFormat.format(date);
        String header= "ICD_code||ICD_concept||ICD_modified||MeSH_code||OMIM_code||DiseasesDB_code||Disease_Syn||Specialty\n";
        File file = new File(path);
        System.out.println("Filr: "+file.getName());
        writeFile(header, outputfile_name);
        Parser p = new Parser();
        BufferedReader br = new BufferedReader(new FileReader(file));
      
            for (String line = br.readLine(); line != null; line = br.readLine()) {
                         out = p.getOut_obj(line);
              
                String finalSyn = null;
                if(out.getSynonyms()!=null){
                    StringBuilder stringBuilder = new StringBuilder();
                    for (String synonym : out.getSynonyms()){
                        stringBuilder.append(synonym).append(";");
                    }
                    finalSyn = stringBuilder.toString();
                    
                }
                System.out.println(out.getIcd_code()+"||"+out.getIcd_concept()+"||"+out.getModified_desc()+"||"
                        +out.getMesh_code()+"||"+out.getOmim_code()+"||"+out.getDiseasesDB_code()+"||"+finalSyn+"||"+out.getSpecialty()+"||"+out.getRed_flag()+"\n");
                writeFile(out.getIcd_code()+"||"+out.getIcd_concept()+"||"+out.getModified_desc()+"||"
                        +out.getMesh_code()+"||"+out.getOmim_code()+"||"+out.getDiseasesDB_code()+"||"+finalSyn+"||"+out.getSpecialty()+"||"+out.getRed_flag()+"\n", outputfile_name);
            

            
                     
            }
            
             
               

            // line is not visible here.
        
    }
    
    private void writeFile(String outWrite, String outputfile_name){
        
        
        
        try {
            //An output file
            fstream = new FileWriter(outputfile_name + ".csv", true);
        } catch (IOException ex) {
            Logger.getLogger(TxtFileReader.class.getName()).log(Level.SEVERE, null, ex);
        }
         try {
            try (BufferedWriter bf = new BufferedWriter(fstream)) {
                bf.write(outWrite);
            }
        } catch (IOException ex) {
            Logger.getLogger(TxtFileReader.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
