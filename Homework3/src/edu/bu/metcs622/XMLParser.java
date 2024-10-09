package edu.bu.metcs622;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.*;
import java.util.ArrayList;
import java.util.List;

public class XMLParser {

    // Parse the XML file into a list of articles with a flexible limit on the number of articles
    public static List<Article> parseXML(String filePath) {
        List<Article> articles = new ArrayList<>();

        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(filePath);

            NodeList wwwNodes = doc.getElementsByTagName("www");
            NodeList inProceedingsNodes = doc.getElementsByTagName("inproceedings");
            NodeList incollectionNodes = doc.getElementsByTagName("incollection");

            // Add 'www' articles to the list, respecting the maxArticles limit
            for (int i = 0; i < wwwNodes.getLength(); i++) {
                Node wwwNode = wwwNodes.item(i);
                articles.add(parseArticle(wwwNode));
            }

            // Add 'inproceedings' articles to the list, respecting the maxArticles limit
            for (int i = 0; i < inProceedingsNodes.getLength(); i++) {
                Node inProcNode = inProceedingsNodes.item(i);
                articles.add(parseArticle(inProcNode));
            }
            
         // Add 'incollection' articles to the list, respecting the maxArticles limit
            for (int i = 0; i < incollectionNodes.getLength(); i++) {
                Node inColNode = incollectionNodes.item(i);
                articles.add(parseArticle(inColNode));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return articles;
    }

    // Helper method to parse an article node (for both 'www' and 'inproceedings')
    private static Article parseArticle(Node node) {
        Article article = new Article();
        if (node.getNodeType() == Node.ELEMENT_NODE) {
            Element element = (Element) node;

            article.title = getTextContent(element, "title");
            article.year = getTextContent(element, "year");
            article.url = getTextContent(element, "ee");
            article.authors = getMultipleTextContent(element, "author");
            article.editors = getMultipleTextContent(element, "editor");
        }
        return article;
    }

    // Helper function to get text content from an XML element
    private static String getTextContent(Element element, String tag) {
        NodeList list = element.getElementsByTagName(tag);
        if (list.getLength() > 0) {
            return list.item(0).getTextContent();
        }
        return null;
    }

    // Helper function to extract multiple text content for 'author' and 'editor' tags
    private static List<String> getMultipleTextContent(Element element, String tag) {
        NodeList list = element.getElementsByTagName(tag);
        List<String> contentList = new ArrayList<>();
        for (int i = 0; i < list.getLength(); i++) {
            contentList.add(list.item(i).getTextContent());
        }
        return contentList;
    }
}
