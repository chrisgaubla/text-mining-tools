/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package threadedwikicruncher;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author chrisgaubla
 */
public class ThreadedWikiParser extends Thread {

    public ThreadedWikiParser() {
    }

    /**
     * This method take a path to the ICD list txt file and make a query on
     * wikipedia using HTTPGetter.java then output in a txt file
     *
     * @param path
     */
    public void parseAndQueryWiki(String path, String lang) {
        
        try {

            PrintWriter out = null;
            
            switch (lang) {
                case "fr":
                    out = new PrintWriter("data/outputICDWikiThreadedFR.txt");
                    break;
                case "en":
                    out = new PrintWriter("data/outputICDWikiThreadedEN2.txt");
                    break;
            }

            InputStream stream = new FileInputStream(new File(path));
            BufferedReader reader = new BufferedReader(new InputStreamReader(stream));

            ArrayList<Thread> threadList = new ArrayList<>();

            ArrayList<ArrayList<String>> outputList = new ArrayList<>();

            for (int i = 0; i < 8; i++) {
                if (lang.equals("en")) {
                    threadList.add(new Thread(new RunnableParserEng(new HTTPGetter(1300), reader, out)));
                } else if (lang.equals("fr")) {
                    threadList.add(new Thread(new RunnableParserFre(new HTTPGetter(1300), reader, out)));
                }
            }

            for (Thread th : threadList) {
                th.start();
            }
            for (Thread thread : threadList) {
                thread.join();
            }

            out.close();

        } catch (FileNotFoundException ex) {
            Logger.getLogger(ThreadedWikiParser.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(ThreadedWikiParser.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}
