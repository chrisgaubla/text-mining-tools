/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wikicruncher;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author chrisgaubla
 */
public class HTTPGetter {

    private final int NUM_CHARS;
    private final String USER_AGENT = "Mozilla/5.0";
    private final String REVISION = "(<revisions><rev.+?>(.+?)</rev></revisions>)";
    private final String REDIRECTION = "#redirect.+?\\[{2}(.+?)\\]{2}";
    private final Pattern revisionPattern;
    private final Pattern redirectionPattern;
    private Matcher matcherRevision;
    private Matcher matcherRedirection;
    private String redirectionName;

    public HTTPGetter(int numChars) {
        this.NUM_CHARS = numChars;
        revisionPattern = Pattern.compile(REVISION);
        redirectionPattern = Pattern.compile(REDIRECTION, Pattern.CASE_INSENSITIVE);
    }

    // HTTP GET request
    public String sendGet(String title) throws Exception {
        String result = "";
        String content = getRawContent(title);

        matcherRevision = revisionPattern.matcher(content);
        matcherRedirection = redirectionPattern.matcher(content);

        if (matcherRevision.find()) {            
            if (matcherRedirection.find()) {
                System.out.println("Redirection time!");
                redirectionName= matcherRedirection.group(1);
                content = getRawContent(matcherRedirection.group(1));
                matcherRevision = revisionPattern.matcher(content);
                if(matcherRevision.find()){
                    result = "Redirection to : "+redirectionName+"\t"+matcherRevision.group(2);
                }                    
            }
            else{
                result = "No redirection !\t"+matcherRevision.group(2);
            }
        }
        else{
            result = "null"+ "\t"+"null";
        }
        
        
        
        
        if (NUM_CHARS>= result.length()) {
            return result;
        } else {
            return result.substring(0, NUM_CHARS);
        }

    }
    private String getRawContent(String name) throws MalformedURLException, ProtocolException, IOException{
        String url = "https://en.wikipedia.org/w/api.php?%20format=xml&action=query&titles=" + name.replaceAll("\\s", "%20") + "&prop=revisions&rvprop=content";
        System.out.println(url);

        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();

        con.setRequestMethod("GET");
        con.setRequestProperty("User-Agent", USER_AGENT);

        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuilder rawContent = new StringBuilder();

        while ((inputLine = in.readLine()) != null) {
            rawContent.append(inputLine);
        }
        in.close();
        return rawContent.toString();
    }

}
