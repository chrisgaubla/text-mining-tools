/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package icdmatcher;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 *
 * @author chrisgaubla
 */
public class MedDocSet {

    private final String path;
    private final String specialChar = ",|;|\\.|:|(|)|\\[|\\]";

    public MedDocSet(String path) {
        this.path = path;
    }

    public HashMap<Integer, String> getHistorySet() {
        File japMedRec = new File(path);
        String dataNode = "data";
        HashMap<Integer,String> historySet = new HashMap<>();

        DocumentBuilderFactory docfactory = DocumentBuilderFactory.newInstance();
        docfactory.setNamespaceAware(true);
        DocumentBuilder docbuilder;
        Document document;

        try {

            docbuilder = docfactory.newDocumentBuilder();

            document = docbuilder.parse(japMedRec);
            NodeList recList = document.getElementsByTagName(dataNode);
            System.out.println("Loading medical document set...");
            for (int i = 0; i < recList.getLength(); i++) {
                NodeList childNodes = recList.item(i).getChildNodes();
                int id = Integer.parseInt(recList.item(i).getAttributes().getNamedItem("id").getNodeValue());
                String result = new String();

                for (int j = 0; j < childNodes.getLength(); j++) {
                    Node node = childNodes.item(j);

                    if (!node.getNodeName().equals("icd")) {

                        result += node.getTextContent();
                    }
                    

                }
                historySet.put(id,result.replaceAll(specialChar, ""));
            }

        } catch (ParserConfigurationException | SAXException | IOException e1) {
            System.out.println("exception! ");
            e1.printStackTrace();
        }
        System.out.println("Medical document set loaded !");
        return historySet;
    }
     public HashMap<Integer, ArrayList<String>> getICDSet() {
        File japMedRec = new File(path);
        String dataNode = "data";
        HashMap<Integer,ArrayList<String>> icdSet = new HashMap<>();

        DocumentBuilderFactory docfactory = DocumentBuilderFactory.newInstance();
        docfactory.setNamespaceAware(true);
        DocumentBuilder docbuilder;
        Document document;

        try {

            docbuilder = docfactory.newDocumentBuilder();

            document = docbuilder.parse(japMedRec);
            NodeList recList = document.getElementsByTagName(dataNode);
            System.out.println("Loading medical document set...");
            for (int i = 0; i < recList.getLength(); i++) {
                NodeList childNodes = recList.item(i).getChildNodes();
                int id = Integer.parseInt(recList.item(i).getAttributes().getNamedItem("id").getNodeValue());
                ArrayList<String> result = new ArrayList<>();
                
                for (int j = 0; j < childNodes.getLength(); j++) {
                    Node node = childNodes.item(j);

                    if (node.getNodeName().equals("icd")) {
                        String code = node.getAttributes().getNamedItem("code").getNodeValue();
                        result.add(code);
                    }
                    

                }
                icdSet.put(id,result);
            }

        } catch (ParserConfigurationException | SAXException | IOException e1) {
            System.out.println("exception! ");
            e1.printStackTrace();
        }
        System.out.println("Medical document set loaded ! ICD");
        return icdSet;
    }

}
