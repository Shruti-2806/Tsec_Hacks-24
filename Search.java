package com.example.hacks;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.stream.Collectors;

public class Search {
    public static void main(String[] args) {
        try {
            // Specify the base URL for the Nominatim API
            String baseUrl = "https://nominatim.openstreetmap.org/search";

            // Specify query parameters
            String query = "chips";
            String format = "json";
            int limit = 5;

            // Specify the latitude and longitude (example coordinates for a location)
            double latitude = 37.7749;  // Example latitude
            double longitude = -122.4194;  // Example longitude

            // Build the complete URL with query parameters and location
            String urlString = String.format("%s?q=%s&format=%s&limit=%d&lat=%f&lon=%f",
                    baseUrl, URLEncoder.encode(query, "UTF-8"), format, limit, latitude, longitude);
            System.out.println(urlString);
            // Create a URL object from the string
            URL url = new URL(urlString);

            // Open a connection to the URL
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            // Set the request method to GET
            connection.setRequestMethod("GET");

            // Get the response code
            int responseCode = connection.getResponseCode();

            // Check if the request was successful (HTTP 200 OK)
            if (responseCode == HttpURLConnection.HTTP_OK) {
                // Read the response
                try (BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
                    String response = reader.lines().collect(Collectors.joining("\n"));
                    System.out.println(response);
                    // You can parse and process the JSON response here
                }
            } else {
                System.out.println("Error: " + responseCode);
            }

            // Close the connection
            connection.disconnect();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
