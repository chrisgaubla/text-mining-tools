/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wikicruncher;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author chrisgaubla
 */
public class BodyPartsMapper {

    List<String> bodyRegions;
    Pattern[] bodyRegionsPattern;
    
    Matcher matcher;
    

    public BodyPartsMapper(String pathMesh) {
        try {
            bodyRegions
                    = Files.readAllLines(Paths.get(pathMesh),
                            StandardCharsets.UTF_8);
            bodyRegionsPattern = new Pattern[bodyRegions.size()];
            
            for(int i =0; i<bodyRegions.size(); i++){
                String region = bodyRegions.get(i);
                String pattern = "(^|\\s)("+region.toLowerCase()+")[s\\s$]";
                System.out.println(pattern);
                bodyRegionsPattern[i] = Pattern.compile(pattern, Pattern.CASE_INSENSITIVE);
                System.out.println(bodyRegionsPattern[i].pattern());
            }
        } catch (IOException ex) {
            System.out.println("ioexception");
        }

    }

    public void bodyMap(String path) {

        try {
            PrintWriter out;
            out = new PrintWriter("data/outputBodyMap.txt");
            InputStream stream = new FileInputStream(new File(path));
            BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
            String line;
            String code;
            String description;

            while ((line = reader.readLine()) != null) {
                String outputLine;
                code = line.substring(6, 14);
                description = line.substring(77, line.length()).toLowerCase();
                outputLine =  code + "\t" + description + "\t";
                for (Pattern pat : bodyRegionsPattern) {
                    matcher = pat.matcher(description);
                    if (matcher.find()) {
                        outputLine = outputLine + matcher.group(2) + "\t";
                    }
                }
                System.out.println("Done line : "+code);
                out.print(outputLine + "\n");
            }
            out.close();
            

        } catch (FileNotFoundException e) {
            System.out.println("File not found");
        } catch (IOException ex) {
            System.out.println("IO exception");
        }
    }

}
