package com.bookingApp.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class DescriptionServiceTest {

    private DescriptionService descriptionService;
    private RestTemplate restTemplate;

    @BeforeEach
    public void setUp() {
        restTemplate = Mockito.mock(RestTemplate.class);
        descriptionService = new DescriptionService();
    }

    @Test
    public void testGetDescription() {
        String placeName = "Paris";
        String expectedDescription = "Paris is the capital and largest city of France. With an official estimated population of 2,102,650..."; // Dummy response

        String jsonResponse = "{\"extract\": \"" + expectedDescription + "\"}";

        when(restTemplate.getForEntity(any(String.class), any(Class.class)))
                .thenReturn(ResponseEntity.ok(jsonResponse));

        String actualDescription = descriptionService.getDescription(placeName);

        assertEquals("Paris is the capital and largest city of France. With an official estimated population of 2,102,650...", actualDescription,
                "The description returned should match the expected description.");
    }
}

