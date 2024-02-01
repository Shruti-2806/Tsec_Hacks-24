package com.example.hacks;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import javax.imageio.ImageIO;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class OCRController {
	
	class Pair {long f; long s; Pair(long f, long s) {this.f = f;this.s = s;}@Override public boolean equals(Object o) {if (this == o) return true; if (o == null || getClass() != o.getClass()) return false;Pair pair = (Pair) o;return f == pair.f && s == pair.s;}@Override public int hashCode() {return Objects.hash(f, s);}}
	
	@GetMapping("")
	public String start() {
		return "index.html";
	}
	
	@GetMapping("/start")
	public String st() {
		return "registration.html";
	}
	
	@PostMapping("/upload-multiple")
    public ResponseEntity<MyDataDTO> handleMultipleFileUpload(@RequestPart("fileInput") MultipartFile userFile) {
		Pair p = new Pair(-1, -1);
		 try {
			 int suff = 1;
			 while(true) {
				 if(suff == 5) break;
				 BufferedImage image1 = ImageIO.read(new File("C:\\Users\\Dhrumil\\OneDrive\\Desktop\\img" + suff + ".jpg"));
		         BufferedImage image2 = convertMultipartFileToBufferedImage(userFile);
		         float match = compareImages(image1, image2);
		         System.out.println(match);
		         long value = (long)(match*100);
		         if(p.f < value) {
		        	 p.f = value;
		        	 p.s = suff;
		         }
				 suff++;
			 }
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
		 String query = "";
		 switch((int)p.s) {
		 case 1: query = "kitkat";
			 break;
		 case 2: query = "dairymilk";
			 break;
		 case 3: query = "5star";
			 break;
		 case 4: query = "oats";
			 break;
		 case 5:
			 break;
		 case 6:
			 break;
		 case 7:
			 break;
		 case 8:
			 break;
		 case 9:
			 break;
		 }
		 //search(query);
		 boolean myBooleanValue = get(query);
		 
		 System.out.println(myBooleanValue);
		 ArrayList<String> myStringList = find(query);
		 System.out.println(myStringList);
		 MyDataDTO myDataDTO = new MyDataDTO(myBooleanValue, myStringList);
		 HttpHeaders headers = new HttpHeaders();
		 headers.setContentType(MediaType.APPLICATION_JSON);
		 return ResponseEntity.ok().headers(headers).body(myDataDTO);
		
    }
	
	public ArrayList<String> find(String query) {
		ArrayList<String> arr = new ArrayList<>();
		try {
            // Set your OpenAI API key
            String apiKey = "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";
            // Set the OpenAI API endpoint
            String apiUrl = "https://api.openai.com/v1/chat/completions";
            // Create the JSON request payload
            String jsonInputString = "{\n" +
                    "  \"model\": \"gpt-3.5-turbo\",\n" +
                    "  \"messages\": [\n" +
                    "    {\n" +
                    "      \"role\": \"user\",\n" +
                    "      \"content\": \" just list the alternatives for " + query + "keywords -> jain\"\n" +
                    "    }\n" +
                    "  ]\n" +
                    "}";

            // Create the URL object
            URL url = new URL(apiUrl);

            // Open a connection
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            // Set the request method to POST
            connection.setRequestMethod("POST");

            // Set the request headers
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("Authorization", "Bearer " + apiKey);

            // Enable input/output streams
            connection.setDoOutput(true);
            connection.setDoInput(true);

            // Write the JSON payload to the request
            try (DataOutputStream outputStream = new DataOutputStream(connection.getOutputStream())) {
                outputStream.writeBytes(jsonInputString);
                outputStream.flush();
            }

            // Get the response code
            int responseCode = connection.getResponseCode();
            //System.out.println("Response Code: " + responseCode);

            // Read the response
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
                String line;
                StringBuffer response = new StringBuffer();

                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }

                // Print the response
                //System.out.println("Response: " + response.toString());
                String resp = response.toString();
                int p = resp.indexOf("content") + 12;
                int e = resp.indexOf("}", p);
                String full = resp.substring(p, e - 2);
                String s = "";
                for(int i=0;i<full.length();i++) {
                	if(full.charAt(i) == '\\') {
                		if(s.indexOf(".") == -1) arr.add(s.trim());
                    	else arr.add(s.substring(s.indexOf(".") + 1, s.length()).trim());
                		s = "";
                	}
                	else s += full.charAt(i);
                }
                if(s.length() >= 2) {
                	if(s.indexOf(".") == -1) arr.add(s.trim());
                	else arr.add(s.substring(s.indexOf(".") + 1, s.length()).trim());
                }
            }

            // Close the connection
            connection.disconnect();

        } catch (Exception e) {
            e.printStackTrace();
        }
		return arr;
	}
	
	public boolean get(String query) {
		try {
            // Set your OpenAI API key
            String apiKey = "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";
            // Set the OpenAI API endpoint
            String apiUrl = "https://api.openai.com/v1/chat/completions";
            // Create the JSON request payload
            String jsonInputString = "{\n" +
                    "  \"model\": \"gpt-3.5-turbo\",\n" +
                    "  \"messages\": [\n" +
                    "    {\n" +
                    "      \"role\": \"user\",\n" +
                    "      \"content\": \" answer in yes or no, is " + query + " safe for => jain\"\n" +
                    "    }\n" +
                    "  ]\n" +
                    "}";

            // Create the URL object
            URL url = new URL(apiUrl);

            // Open a connection
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            // Set the request method to POST
            connection.setRequestMethod("POST");

            // Set the request headers
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("Authorization", "Bearer " + apiKey);

            // Enable input/output streams
            connection.setDoOutput(true);
            connection.setDoInput(true);

            // Write the JSON payload to the request
            try (DataOutputStream outputStream = new DataOutputStream(connection.getOutputStream())) {
                outputStream.writeBytes(jsonInputString);
                outputStream.flush();
            }

            // Get the response code
            int responseCode = connection.getResponseCode();
            //System.out.println("Response Code: " + responseCode);

            // Read the response
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
                String line;
                StringBuffer response = new StringBuffer();

                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }

                // Print the response
                //System.out.println("Response: " + response.toString());
                String resp = response.toString();
                if(resp.contains("YES") || resp.contains("Yes")) return true;
            }

            // Close the connection
            connection.disconnect();

        } catch (Exception e) {
            e.printStackTrace();
        }
		return false;
	}
	
	private static float compareImages(BufferedImage img1, BufferedImage img2) {
        byte[] pixels1 = ((DataBufferByte) img1.getRaster().getDataBuffer()).getData();
        byte[] pixels2 = ((DataBufferByte) img2.getRaster().getDataBuffer()).getData();
        HashMap<Byte, Long> map = new HashMap<>();
        for(byte a : pixels2) fill(map, a);
        int count = 0;
        for (int i = 0; i < pixels1.length; i++) {
            if(map.containsKey(pixels1[i])) {
            	remove(map, pixels1[i]);
            	count++;
            }              
        }
        return ((float)count / (pixels1.length));
    }
    
	public void search(String query) {
		try {
            String apiUrl = "https://world.openfoodfacts.org/cgi/search.pl?search_terms=" + query + "&json=1"; 
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
                System.out.println(resp);
//                ArrayList<String> arr = new ArrayList<>();
//                String s = "";
//                fr(int i=0;i<list.length();i++) {
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
	}
	
    public static void fill(HashMap<Byte, Long> map, Byte key) {
    	if(map.containsKey(key)) map.put(key, map.get(key) + 1);
    	else map.put(key, 1L);
    }
    
    public static void remove(HashMap<Byte, Long> map, Byte key) {
    	map.put(key, map.get(key) - 1);
    	if(map.get(key) == 0) map.remove(key);
    }
    
    private BufferedImage convertMultipartFileToBufferedImage(MultipartFile multipartFile) throws IOException {
        byte[] bytes = multipartFile.getBytes();
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes);
        return ImageIO.read(byteArrayInputStream);
    }
    
    class MyDataDTO {
        private boolean myBoolean;
        private List<String> myStringList;

        // Constructors

        public MyDataDTO() {
            // Default constructor
        }

        public MyDataDTO(boolean myBoolean, List<String> myStringList) {
            this.myBoolean = myBoolean;
            this.myStringList = myStringList;
        }

        // Getters and setters

        public boolean isMyBoolean() {
            return myBoolean;
        }

        public void setMyBoolean(boolean myBoolean) {
            this.myBoolean = myBoolean;
        }

        public List<String> getMyStringList() {
            return myStringList;
        }

        public void setMyStringList(List<String> myStringList) {
            this.myStringList = myStringList;
        }
    }

	
}
