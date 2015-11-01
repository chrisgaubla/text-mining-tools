/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wordtools;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author chrisgaubla
 */
public class IcdCimMapper {

    public void mapICDCIM(String cimPath, String icdPath) {
        try {
            PrintWriter out = new PrintWriter("data/outPutMapICDCIM.txt");

            List<String> cimList = Files.readAllLines(Paths.get(cimPath), StandardCharsets.UTF_8);
            List<String> icdList = Files.readAllLines(Paths.get(icdPath), StandardCharsets.UTF_8);

            for (String cimLine : cimList) {
                String[] splittedCimLine = cimLine.split("\t");
                String freq = splittedCimLine[1];
                String cim = splittedCimLine[0];
                if (cim.length() >= 3) {

                    cim = cim.substring(0, 3);
                }
                if (icdList.contains(cim)) {
                    out.println(freq + "\t" + cimLine);
                } else {
                    out.println("0" + "\t" + cimLine);
                }

            }

        } catch (FileNotFoundException ex) {
            Logger.getLogger(IcdCimMapper.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(IcdCimMapper.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}
