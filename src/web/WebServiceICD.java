package web;


import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.util.concurrent.Executors;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import icdtextminer.StringICDMiner;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

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

    static StringICDMiner miner = new StringICDMiner();

    public void handle(HttpExchange exchange) throws IOException {
        String requestMethod = exchange.getRequestMethod();
        System.out.println("coucou");

        if (requestMethod.equalsIgnoreCase("POST")) {

            Headers responseHeaders = exchange.getResponseHeaders();
            responseHeaders.set("Content-Type", "text/plain");
            exchange.sendResponseHeaders(200, 0);
            OutputStream responseBody = exchange.getResponseBody();
            Headers requestHeaders = exchange.getRequestHeaders();
            System.out.println("Got post request");
            System.out.println(exchange.getRequestURI());
            switch (exchange.getRequestURI().toString()) {
                case "/findICD":
                    InputStream stream = exchange.getRequestBody();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
                    String history = "";
                    String line = "";
                    while ((line = reader.readLine()) != null) {
                        history = history + line;
                    }
                    System.out.println(history);

                    ArrayList<String> list = miner.getMatching(history);
                    System.out.println("Response : " + list.toString());

                    responseBody.write(list.toString().getBytes());
                    responseBody.close();
                    break;

            }

        }

    }
}
