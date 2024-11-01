package com.bookingApp.service;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class APIsService {

    private static final String WEATHER_API_KEY = "d68cbbe802174aeea7b110155230304";
    private static final String WEATHER_URL = "http://api.weatherapi.com/v1/current.json?key=" + WEATHER_API_KEY + "&q=";

    private static final String PEXELS_API_KEY = "X8kXKrS4KoUxsTq1IoKhPxB94YL8aOcQjuXnCdZCyGpXOisAYVnQkz5T"; // Set your Pexels API Key
    private static final String PEXELS_API_URL = "https://api.pexels.com/v1/search";

    private final RestTemplate restTemplate;

    public APIsService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate; // constr injection
    }

    public String getWeatherData(String city) {
//        RestTemplate restTemplate = new RestTemplate();
        String url = WEATHER_URL + city;
        return restTemplate.getForObject(url, String.class);
    }


    public String getImageUrl(String query) {
        // build the query to include "city"
        String url = PEXELS_API_URL + "?query=" + query + "+city&per_page=1";
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        //create http header to add key -> pexels config
        headers.set("Authorization", PEXELS_API_KEY);
        HttpEntity<String> entity = new HttpEntity<>(headers);

        try {
            //restTemplate exchange method
            ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
            //parse response
            JSONObject json = new JSONObject(response.getBody());
            JSONArray photos = json.getJSONArray("photos");

            if (photos.length() > 0) {
                return photos.getJSONObject(0).getJSONObject("src").getString("original");
            } else {
//                System.out.println("No specific image found for query: " + query);
                // if !image, return default image
                return "https://www.nationalforest.org/sites/default/files/styles/custom_270x174/public/2023-08/Beacon%20Hill%20at%20Dawn%20-%20Large.jpg.webp?itok=2Qf142YV";  // Replace with the URL of a default weather image
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "https://www.nationalforest.org/sites/default/files/styles/custom_270x174/public/2023-08/Beacon%20Hill%20at%20Dawn%20-%20Large.jpg.webp?itok=2Qf142YV";  // Return fallback image in case of error
    }
}

