package com.example.hacks;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.TreeMap;

public class Web {

    public static void main(String[] args) {
        // Replace this URL with the actual URL you want to scrape
    	ArrayList<String> ing = new ArrayList<>();
    	TreeMap<Integer, String> map = new TreeMap<>();
    	try {
            // Connect to the website and get the HTML document
            Document doc = Jsoup.connect("https://www.foodista.com/recipe/SVZDH8S4/spinach-tomato-onion-couscous").get();

            // Select all elements with the class "field-item even"
            Elements elements = doc.select(".field-item.even");

            // Iterate over the selected elements and print their text content
            for (Element element : elements) {
            	if(element.text().length() > 100) {
            		int num = Integer.parseInt(element.text().substring(0, element.text().indexOf(" ")));
            		String t = element.text().substring(element.text().indexOf(" ") + 1, element.text().length());
            		map.put(num, t);
            		continue;
            	}
            	ing.add(element.text());
                System.out.println(element.text());
            }
            elements = doc.select(".field-item.odd");
            for (Element element : elements) {
            	if(element.text().length() > 100) {
            		int num = Integer.parseInt(element.text().substring(0, element.text().indexOf(" ")));
            		String t = element.text().substring(element.text().indexOf(" ") + 1, element.text().length());
            		map.put(num, t);
            		continue;
            	}
            	ing.add(element.text());
                System.out.println(element.text());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
