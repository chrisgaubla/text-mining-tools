/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package wikiparser;

import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author Dina
 */
public class Parser {
    private final Pattern p= Pattern.compile("[\\W\\w]?'''(.+?)'''[\\W\\w]?",Pattern.DOTALL);
    private final Pattern p1= Pattern.compile("OMIM\\s+?=\\s?([a-zA-Z0-9']+)\\W?",Pattern.DOTALL);
    private final Pattern p2= Pattern.compile("MeshID\\s+?=\\s?([a-zA-Z0-9']+)\\W?",Pattern.DOTALL);
    private final Pattern p3 = Pattern.compile("DiseasesDB\\s+?=\\s?([a-zA-Z0-9']+)\\W?",Pattern.DOTALL);
    private final Pattern p4 = Pattern.compile("field\\s+?=\\s?\\[{2}(.*?)\\]{2}",Pattern.DOTALL);
    private Matcher m;
    private Outliner out_obj;
    private void doParser(String line){
        System.out.println("WE PARSE");
         String[] data = line.split("\\t");
         System.out.println("length "+data.length);
         String omim_code= "null";
         String meshID_code= "null";
         String diseasesDB_code = "null";
         String specialty = "null";
         Outliner outline = null;
         Set <String> synonyms = new HashSet();
          String icd_code;
          String icd_concept_name;
           String icd_mod_descr;
                 icd_code = data [0];
         icd_concept_name = data [1];
         icd_mod_descr = data [2];
         
          String synonym ="null";
        
         if (data.length!=0&&data.length==6){
              if(!data[5].equalsIgnoreCase("null")){
                     //synonyms.add(data[5]);
                 }
             String red_flag = data[3];     
             String wiki_txt = data[4].trim();

                 
                 String sub_wiki = wiki_txt.replaceAll("\\{{2}Infobox [dD]isease.+MeshID\\s+?=.+?\\}{2}", "");
                 if(sub_wiki.length()>310){
                      sub_wiki=sub_wiki.substring(0, 300);
                      m = p.matcher(sub_wiki);
                       System.out.println("SUB: "+sub_wiki);
                 }
                 else m=p.matcher(wiki_txt);
                
          
                 while (m.find()){
                      
                      synonym = m.group(1);
                     synonyms.add(synonym);
                     System.out.println("FOUND SYNONYM "+synonym);
                 } 
                
                
                 m = p1.matcher(wiki_txt);
                  
                 if(m.find()){
                     omim_code = m.group(1);
                     System.out.println("FOUND OMIM "+m.group(1));
                 }               
                 m = p2.matcher(wiki_txt);
                  if(m.find()){
                     meshID_code = m.group(1);
                     System.out.println("FOUND MESH "+m.group(1));
                 }  
                 m = p3.matcher(wiki_txt);
                  if(m.find()){
                     diseasesDB_code = m.group(1);
                     System.out.println("FOUND DiseaseDB "+m.group(1));
                 }  
                 m = p4.matcher(wiki_txt);
                  if(m.find()){
                     specialty = m.group(1);
                     System.out.println("FOUND Specialty "+m.group(1));
                 }  
                        
             outline = new Outliner(icd_code, omim_code, specialty, icd_concept_name, synonyms, diseasesDB_code, meshID_code, icd_mod_descr, red_flag);
         }
         else{
                outline = new Outliner(icd_code, "null","null",icd_concept_name, null, "null","null",icd_mod_descr, "null");
         }
         
         
     this.out_obj = outline;
    }

    public Outliner getOut_obj(String line) {
        doParser(line);
        return out_obj;
    }

}
