package com.bookingApp.service;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

class APIsServiceTest {

    @InjectMocks
    private APIsService apisService;

    @Mock
    private RestTemplate restTemplate;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        restTemplate = Mockito.mock(RestTemplate.class);
        apisService = new APIsService(restTemplate);
    }

    @Test
    void testGetWeatherData() {
        String city = "Paris";
        String mockResponse = "{\"location\":{\"name\":\"Paris\",\"country\":\"France\"},\"current\":{\"temp_c\":15}}";
        when(restTemplate.getForObject(anyString(), eq(String.class))).thenReturn(mockResponse);

        String result = apisService.getWeatherData(city);

        // verify keys exist in JSON resp
        JSONObject jsonResponse = new JSONObject(result);
        assertTrue(jsonResponse.has("location"));
        assertTrue(jsonResponse.getJSONObject("location").has("name"));
        assertTrue(jsonResponse.getJSONObject("location").getString("name").equalsIgnoreCase("Paris"));

        // verify if called once
        verify(restTemplate, times(1)).getForObject(anyString(), eq(String.class));
    }

    @Test
    public void testGetImageUrl() {
        String query = "sunset";
        String expectedUrl = "https://images.pexels.com/photos/351434/pexels-photo-351434.jpeg"; // Dummy URL

        // dummy resp
        String jsonResponse = "{\"photos\":[{\"src\":{\"original\":\"" + expectedUrl + "\"}}]}";

        when(restTemplate.exchange(any(String.class), any(HttpMethod.class), any(HttpEntity.class), any(Class.class)))
                .thenReturn(ResponseEntity.ok(jsonResponse));

        String actualUrl = apisService.getImageUrl(query);

        assertEquals(expectedUrl, actualUrl, "The image URL should match the expected URL.");
    }

}
