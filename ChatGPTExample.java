package com.example.hacks;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class ChatGPTExample {

    public static void main(String[] args) {
        String apiKey = "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";
        String prompt = "What is the meaning of life?";

        try {
            String response = promptChatGPT(apiKey, prompt);
            System.out.println("ChatGPT Response: " + response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static String promptChatGPT(String apiKey, String prompt) throws Exception {
        String endpoint = "https://api.openai.com/v1/chat/completions";
        URL url = new URL(endpoint);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        // Set the necessary HTTP headers
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setRequestProperty("Authorization", "Bearer " + apiKey);

        // Enable input/output streams
        connection.setDoOutput(true);

        // Create the request payload
        String payload = "{\"model\": \"gpt-3.5-turbo\", \"messages\": [{\"role\": \"system\", \"content\": \"You are a helpful assistant.\"}, {\"role\": \"user\", \"content\": \"" + prompt + "\"}]}";

        // Send the request payload
        try (DataOutputStream wr = new DataOutputStream(connection.getOutputStream())) {
            wr.write(payload.getBytes(StandardCharsets.UTF_8));
        }

        // Get the response
        try (BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                response.append(line);
            }
            return response.toString();
        } finally {
            connection.disconnect();
        }
    }
}
