package com.example.elekt.shvabrashvabr.Model.Parser;

import android.graphics.drawable.Drawable;
import android.os.AsyncTask;

import com.example.elekt.shvabrashvabr.Model.Article;

import org.w3c.dom.CharacterData;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

/**
 * This Class parses the data from the @urlResource and gets the collection of the articles objects.
 * All operations are performed in background.
 */

public class XMLParser extends AsyncTask<Void, Void, Collection<Article>>  {
    private static XMLParser parser;
    private final String urlResource = "https://habrahabr.ru/rss/hubs/all/";

    // the variable that stores how many articles have already been loaded in view
    private static int numOfLoadedArticles;

    static {
        parser = new XMLParser();
    }

    public XMLParser getInstance() {
        return parser;
    }


    public static void setNumOfLoadedArticles(int numOfLoadedArticles) {
        XMLParser.numOfLoadedArticles = numOfLoadedArticles;
    }

    public static int getNumOfLoadedArticles() {
        return numOfLoadedArticles;
    }

    /**
     * The method get the articles from the @urlResource:
     * parses the xml, creates objects of type Article, add objects in collection
     * @return collection of Article objects
     */
    public Collection<Article> getArticles() {
        ArrayList<Article> news = new ArrayList<>();
        URL url = null;
        try {
            url = new URL(urlResource);
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = null;
            db = dbf.newDocumentBuilder();
            Document doc = db.parse(new InputSource(url.openStream()));
            doc.getDocumentElement().normalize();

            NodeList nodeList = doc.getElementsByTagName("item");

            String[] title = new String[nodeList.getLength()];
            String[] link = new String[nodeList.getLength()];
            String[] description = new String[nodeList.getLength()];
            String[] pubDate = new String[nodeList.getLength()];
            Drawable[] image = new Drawable[nodeList.getLength()];
            String[] category = new String[nodeList.getLength()] ;
            String[] creator = new String[nodeList.getLength()];


            for (int i = 0; i < 8 && numOfLoadedArticles < nodeList.getLength(); numOfLoadedArticles++, i++) {

                Node node = nodeList.item(numOfLoadedArticles);
                title[numOfLoadedArticles] = get("title", node);
                link[numOfLoadedArticles] = get("link", node);
                description[numOfLoadedArticles] = get("description",  node);
                pubDate[numOfLoadedArticles] = get("pubDate", node);
                image[numOfLoadedArticles] = loadImageFromWeb(get("img", node));
                category[numOfLoadedArticles] = get("category", node);
                creator[numOfLoadedArticles] = get("creator", node);
                Article newsItem = new Article(title[numOfLoadedArticles], link[numOfLoadedArticles], description[numOfLoadedArticles], pubDate[numOfLoadedArticles], category[numOfLoadedArticles] , image[numOfLoadedArticles], creator[numOfLoadedArticles]);
                news.add(newsItem);
            }

        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return news;
    }

    private String get(String s, Node node) {
        switch (s) {
            case "title": {
                return getCharacterData(s, node);
            }
            case "link": {
                return getSimpleData(s, node);
            }
            case "description": {
                return getCharacterData(s, node);
            }
            case "pubDate": {
                return getSimpleData(s, node);
            }
            case "img": {
                String fullDescr = getCharacterData("description", node);
                return getImageLink(fullDescr);
            }
            case "category": {
                return getCharacterData(s, node);
            }
            case "creator": {
                return getSimpleData(s, node);
            }
        }
        return "";
    }

    /**
     * The method parses the string in order to find the link to the image
     * @param s tag that we search
     * @return the link to the image inside string in tag s
     */
    private String getImageLink(String s) {
        String searchString = "img src=";
        int searchStringSize = searchString.length();
        if (s.contains(searchString)) {
            StringBuilder stringBuilder = new StringBuilder(s);
            String ref = "";
            for (int i = 0 ; i < s.toCharArray().length; i++) {
                char current = s.charAt(i);
                boolean begin = current == '<';
                boolean have = (i + searchStringSize + 1) < s.toCharArray().length;
                if (!have) {continue;}
                String substring = s.substring(i + 1, i + searchStringSize + 1);
                boolean start = substring.equals(searchString);
                if (begin  && have && start) {
                    i = i + searchStringSize + 2;
                    for (int j = i ; j < s.toCharArray().length; j++) {
                        if (s.charAt(j) == '"') {
                            return ref;
                        }
                        ref += s.charAt(j);
                    }
                } else {
                    ref = "";
                }
            }
            return ref;
        }
        return "";

    }

    /**
     * The method gets string from the searched tag
     * @param s tag that we search
     * @param node node in witch we search
     * @return string with the information inside the searched tag
     */
    private String getSimpleData(String s, Node node) {
        Element elementNode = (Element) node;
        NodeList list = elementNode.getElementsByTagName(s);
        Element element = (Element) list.item(0);
        if (element == null) {
            return null;
        }
        NodeList childrenList = element.getChildNodes();
        return  ((Node) childrenList.item(0)).getNodeValue();
    }

    /**
     * The method gets information if it starts from "![CDATA["
     * @param s tag that we search
     * @param node node in witch we search
     * @return string with the information inside the searched tag
     */
    private String getCharacterData(String s, Node node) {
        Element elementNode = (Element) node;
        NodeList list = elementNode.getElementsByTagName(s);
        Element element = (Element) list.item(0);
        NodeList childrenList = element.getChildNodes();
        String data;
        for(int index = 0; index < childrenList.getLength(); index++){
            if(childrenList.item(index) instanceof CharacterData){
                CharacterData child = (CharacterData) childrenList.item(index);
                data = child.getData();

                if(data != null && data.trim().length() > 0)
                    return child.getData();
            }
        }
        return null;
    }

    /**
     * The method gets Drawable from the url
     * @param url
     * @return image in Drawable format
     */
    public Drawable loadImageFromWeb(String url) {
        try {
            InputStream is = (InputStream) new URL(url).getContent();
            Drawable d = Drawable.createFromStream(is, "src name");
            return d;
        } catch (Exception e) {
            return null;
        }
    }


    @Override
    protected Collection<Article> doInBackground(Void... params) {
        return getArticles();
    }
}
