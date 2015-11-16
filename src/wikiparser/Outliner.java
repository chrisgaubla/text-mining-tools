/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package wikiparser;

import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author Dina
 */
public class Outliner {
    private String icd_code;
    private String omim_code;
    private String specialty;
    private String icd_concept;
    private Set <String> synonyms;
    private String diseasesDB_code;
    private final String mesh_code;
    private final String modified_desc;
    private String red_flag;
    public String getIcd_concept() {
        return icd_concept;
    }

    public String getModified_desc() {
        return modified_desc;
    }

    
    public String getDiseasesDB_code() {
        return diseasesDB_code;
    }

    public String getMesh_code() {
        return mesh_code;
    }

  

    public String getIcd_code() {
        return icd_code;
    }

    public String getOmim_code() {
        return omim_code;
    }

    public Set<String> getSynonyms() {
        return synonyms;
    }



    public String getSpecialty() {
        return specialty;
    }

    public String getRed_flag() {
        return red_flag;
    }

    public Outliner(String icd_code, String omim_code, String specialty, String icd_concept, Set<String> synonyms, String diseasesDB_code, String mesh_code, String modified_desc, String red_flag) {
        this.icd_code = icd_code;
        this.omim_code = omim_code;
        this.specialty = specialty;
        this.icd_concept = icd_concept;
        this.synonyms = synonyms;
        this.diseasesDB_code = diseasesDB_code;
        this.mesh_code = mesh_code;
        this.modified_desc = modified_desc;
        this.red_flag = red_flag;
    }






    
}
