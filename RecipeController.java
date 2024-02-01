package com.example.hacks;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Map;
import java.util.TreeMap;
import java.util.TreeSet;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class RecipeController {
	
	@GetMapping("/recipe")
	public String start(Model model) {
		ArrayList<String> names = new ArrayList<>();
		ArrayList<String> images = new ArrayList<>();
		ArrayList<String> ing[] = new ArrayList[20];
		for(int i=0;i<20;i++) ing[i] = new ArrayList<>();
		ArrayList<String> tree[] = new ArrayList[20];
		for(int i=0;i<20;i++) tree[i] = new ArrayList<>();
		try {
            String apiKey = "3ae31a6a300e4120ad6aacc55ecf5d24";
            String apiUrl = "https://api.spoonacular.com/recipes/random?number=100&include-tags=vegetarian&apiKey=3ae31a6a300e4120ad6aacc55ecf5d24";
            URL url = new URL(apiUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setDoOutput(true);
            connection.setDoInput(true);
            int responseCode = connection.getResponseCode();
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
                String line;
                StringBuffer response = new StringBuffer();

                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
                String resp = response.toString();
                int prev = 0;
                int ind = 0;
                TreeSet<Integer> set = new TreeSet<>();
                set.add(-1);
                while(true) {
                	int find = resp.indexOf("readyInMinutes", set.last() + 1);
                	set.add(find);
                	find -= 100;
                	if(find < 0) break;
                	int index = resp.indexOf("title", find);
                	if(index == -1) break;
                	int end = resp.indexOf(",", index);
                	String title = resp.substring(index + 8, end - 1);
                	names.add(title);
                	System.out.println(title);
                	prev = end;
                	index = resp.indexOf("image", find);
                	end = resp.indexOf(",", index);
                	String image = resp.substring(index + 8, end - 1);
                	System.out.println(image);
                	images.add(image);
                	index = resp.indexOf("sourceUrl", find);
                	end = resp.indexOf(",", index);
                	String ur = resp.substring(index + 12, end - 1);
                	System.out.println(ur);
                	ArrayList<String> inge = new ArrayList<>();
                	TreeMap<Integer, String> map = new TreeMap<>();
                	try {
                        // Connect to the website and get the HTML document
                        Document doc = Jsoup.connect(ur).get();

                        // Select all elements with the class "field-item even"
                        Elements elements = doc.select(".field-item.even");

                        // Iterate over the selected elements and print their text content
                        for (Element element : elements) {
                        	if(element.text().length() > 100) {
                        		try {
	                        		int num = Integer.parseInt(element.text().substring(0, element.text().indexOf(" ")));
	                        		String t = element.text().substring(element.text().indexOf(" ") + 1, element.text().length());
	                        		map.put(num, t);
	                        		System.out.println(t);
                        		}
                        		catch(Exception e) {
                        			inge.add(element.text());
                                    System.out.println(element.text());
                        		}
                        		continue;
                        	}
                        	inge.add(element.text());
                            System.out.println(element.text());
                        }
                        elements = doc.select(".field-item.odd");
                        for (Element element : elements) {
                        	if(element.text().length() > 100) {
                        		try {
	                        		int num = Integer.parseInt(element.text().substring(0, element.text().indexOf(" ")));
	                        		String t = element.text().substring(element.text().indexOf(" ") + 1, element.text().length());
	                        		map.put(num, t);
	                        		System.out.println(t);
                        		}
                        		catch(Exception e) {
                        			inge.add(element.text());
                                    System.out.println(element.text());
                        		}
                        		continue;
                        	}
                        	inge.add(element.text());
                            System.out.println(element.text());
                        }
                        for(String ele : inge) {
                    		ing[ind].add(ele);
                    	}
                    	for(Map.Entry<Integer, String> entry : map.entrySet()) {
                    		tree[ind].add(entry.getValue());
                    	}
                    } catch (Exception e) {
                        if(names.size() > 0) names.remove(names.size() - 1);
                        if(images.size() > 0) images.remove(images.size() - 1);
                        ind--;
                    }
                	ind++;
                	if(ind == 10) break;
                }
                //System.out.println(resp);
//                ArrayList<String> arr = new ArrayList<>();
//                String s = "";
//                for(int i=0;i<list.length();i++) {
//                	if(list.charAt(i) >= 48 && list.charAt(i) <= 57 || list.charAt(i) == ',') {
//                		if(s.length() > 3) arr.add(s);
//                		s = "";
//                	}
//                	s += list.charAt(i);
//                }
//                if(s.indexOf(".") != -1) arr.add(s);
//                for(String p : arr) System.out.println(p.substring(0, p.length()));
            }

            // Close the connection
            connection.disconnect();

        } catch (Exception e) {
            e.printStackTrace();
        }
		model.addAttribute("names", names);
		model.addAttribute("images", images);
		model.addAttribute("ingredients", ing);
		model.addAttribute("steps", tree);
		return "recipe.html";
	}
	
	public void fetch() {
		
	}
	
}
