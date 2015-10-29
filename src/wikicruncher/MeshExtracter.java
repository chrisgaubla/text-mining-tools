/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wikicruncher;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author chrisgaubla
 */
public class MeshExtracter {

    private final String BODY_REGION = "(\\*NEWRECORD.+?)(MH = (.+)\\n)(.+?)(MN = A01.+?\\n)+(.+\\n{2})";
    private final Pattern BODY_REGION_PATTERN;
    private Matcher bodyRegionMatcher;

    public MeshExtracter() {
        this.BODY_REGION_PATTERN = Pattern.compile(BODY_REGION);
    }

    public void creatMeshList(String path) {
        try {
            PrintWriter out;
            out = new PrintWriter("data/MeshList.txt");
            Path pathReal = FileSystems.getDefault().getPath("mesh", "d2016.txt");
            String contents = new String(Files.readAllBytes(pathReal), StandardCharsets.UTF_8);
            
            bodyRegionMatcher = BODY_REGION_PATTERN.matcher(contents);
            System.out.println("matched");
            System.out.println(contents.substring(0, 10000));
            if(bodyRegionMatcher.find()){
                System.out.println("working");
            }
            
            
        } catch (FileNotFoundException e) {
            System.out.println("file not found");
        } catch (IOException ex) {
            Logger.getLogger(MeshExtracter.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
