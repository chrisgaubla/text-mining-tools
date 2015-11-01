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
public class BracketCounter {

    public void countBracketSyn(String brackPath) {
        try {
            PrintWriter out = new PrintWriter("data/outBracket");

            List<String> brackList = Files.readAllLines(Paths.get(brackPath), StandardCharsets.UTF_8);

            int i = 0;
            for (String brackLine : brackList) {

                String[] splittedbrackLine = brackLine.split("\t");

                if (splittedbrackLine[1].contains("[")) {
                    i++;
                    if (!splittedbrackLine[8].equals("null")) {
                        int before = splittedbrackLine[6].length();

                        String afterString = splittedbrackLine[6].replaceAll(";", "");
                        int after = afterString.length();

                        int numSyn = before - after;
                        if (numSyn > 1) {

                            out.println(brackLine);
                        }
                    } else {
                        //System.out.println(i);
                    }

                }

            }
            System.out.println(i);
            out.close();

        } catch (FileNotFoundException ex) {
            Logger.getLogger(IcdCimMapper.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(IcdCimMapper.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
