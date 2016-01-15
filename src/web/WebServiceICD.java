package web;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.util.concurrent.Executors;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import icdassignmentevaluator.ICDAssignmentEvaluator;
import icdtextminer.ICDMinerBasic;
import icdtextminer.ICDMinerSentence;
import icdtextminer.MatchedCode;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import org.json.JSONObject;

public class WebServiceICD {

    public static void main(String[] args) throws IOException {
        InetSocketAddress addr = new InetSocketAddress(8686);
        HttpServer server = HttpServer.create(addr, 0);
        server.createContext("/", new MyHandler());
        server.setExecutor(Executors.newCachedThreadPool());
        server.start();
        System.out.println("Server is listening on port 8686");
    }
}

class MyHandler implements HttpHandler {

    static ICDMinerSentence senMiner = new ICDMinerSentence();
    static ICDMinerBasic basMiner = new ICDMinerBasic();
    static ICDAssignmentEvaluator evaluator = new ICDAssignmentEvaluator();

    public void handle(HttpExchange exchange) throws IOException {
        String requestMethod = exchange.getRequestMethod();

        if (requestMethod.equalsIgnoreCase("POST")) {

            Headers responseHeaders = exchange.getResponseHeaders();
            responseHeaders.set("Content-Type", "text/plain");
            exchange.sendResponseHeaders(200, 0);
            OutputStream responseBody = exchange.getResponseBody();
            Headers requestHeaders = exchange.getRequestHeaders();
            System.out.println("Got post request");
            System.out.println(exchange.getRequestURI());

            InputStream stream = exchange.getRequestBody();
            BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
            String content = "";
            String line = "";
            String response = "";
            
            while ((line = reader.readLine()) != null) {
                content = content + line;
            }
            JSONObject jsonContent = new JSONObject(content);
            String history = jsonContent.getString("history");
            String icdList = (jsonContent.isNull("icdList")? "":jsonContent.getString("icdList"));
            switch (exchange.getRequestURI().toString()) {
                case "/findICDSentence":
                    System.out.println(history);

                    ArrayList<MatchedCode> senList = senMiner.getMatching(history);
                    System.out.println("Response : " + senList.toString());
                    for (MatchedCode m : senList){
                        response = response + m.getWebString();
                    }
                    break;
                case "/findICDBasic":

                    System.out.println(history);

                    ArrayList<MatchedCode> basList = basMiner.getMatching(history);
                    System.out.println("Response : " + basList.toString());
                    for (MatchedCode m : basList){
                        response = response + m.getWebString();
                    }
                    break;
                case "/evaluateICDList":
                    response = "Scoring of ICD codes for this text: </br>";
                    String[] scoreList = evaluator.evaluateList(history, icdList).replaceAll("\\[|\\]", "").split(",");
                    System.out.println(scoreList);
                    for (String score : scoreList){
                        response= response +"</br>"+score;
                    }
                    break;

            }
            responseBody.write(response.getBytes());
            responseBody.close();
            

        }

    }
}
