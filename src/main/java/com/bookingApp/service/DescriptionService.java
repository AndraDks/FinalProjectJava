package com.bookingApp.service;

import org.json.JSONObject;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class DescriptionService {

    private static final String WIKIPEDIA_API_URL = "https://en.wikipedia.org/api/rest_v1/page/summary/";

    public String getDescription(String placeName) {
        String url = WIKIPEDIA_API_URL + placeName;

        RestTemplate restTemplate = new RestTemplate();
        try {
            ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
            JSONObject json = new JSONObject(response.getBody());

            if (json.has("extract")) {
                String fullDescription = json.getString("extract");

                // split into sentences, get first2
                String[] sentences = fullDescription.split("\\. ");
                String description = sentences.length > 1
                        ? sentences[0] + ". " + sentences[1] + "."
                        : sentences[0] + ".";

                if (description.length() > 250) {
                    description = description.substring(0, 100).trim() + "...";
                }

                return description;
            }
        } catch (Exception e) {
            System.out.println("Error fetching description for " + placeName + ": " + e.getMessage());
        }

        return "No description available.";
    }
}
