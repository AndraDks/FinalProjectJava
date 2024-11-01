package com.bookingApp.controller;

import org.json.JSONException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;
import com.bookingApp.service.APIsService;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.mock;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class APIsControllerTest {

    @InjectMocks
    private APIsController apisController;

    @Mock
    private APIsService apisService;

    @Mock
    private Model model;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetWeather() throws JSONException {
        String city = "London";
        String weatherData = "{ \"location\": { \"name\": \"London\", \"country\": \"UK\" }, \"current\": { \"temp_c\": 15.0, \"condition\": { \"text\": \"Sunny\" }, \"humidity\": 40 } }";
        String imageUrl = "http://example.com/image.jpg";

        when(apisService.getWeatherData(city)).thenReturn(weatherData);
        when(apisService.getImageUrl("London UK")).thenReturn(imageUrl);

        String viewName = apisController.getWeather(city, model);

        assertEquals("weather", viewName);
    }

    @Test
    public void testShowWeatherCheckPage() {
        String viewName = apisController.showWeatherCheckPage();

        assertEquals("weather_check", viewName);
    }
}
