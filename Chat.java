package com.example.hacks;

import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class Chat {
    public static void main(String[] args) {
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
                    "      \"content\": \" i am appending a list of items, you tell me whether it is suitable to eat or not according to : jain;"
                    + "ingredients - chicken, palm oil , pork, onion, potato; (dont explain just list yes or no)\"\n" +
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
                int index = resp.indexOf("content") + 10;
                int end = resp.indexOf("}", index);
                String list = resp.substring(index, end);
                System.out.println(list);
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
    }
}
