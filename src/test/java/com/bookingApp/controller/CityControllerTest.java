package com.bookingApp.controller;

import com.bookingApp.controller.CityController;
import com.bookingApp.model.City;
import com.bookingApp.model.Country;
import com.bookingApp.repository.CityRepository;
import com.bookingApp.service.APIsService;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import java.util.Optional;

class CityControllerTest {

    @Mock
    private CityRepository cityRepository;

    @Mock
    private APIsService apisService;

    @Mock
    private Model model;

    @InjectMocks
    private CityController cityController;

    private City city;
    private Country country;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        country = new Country();
        country.setName("TestCountry");

        city = new City();
        city.setName("TestCity");
        city.setCountry(country);
        city.setCityImageUrl("testImageUrl");
        city.setCityDescription("Beautiful test city");
    }

    @Test
    void testCityDetails() {
        when(cityRepository.findById(1L)).thenReturn(Optional.of(city));
        when(apisService.getWeatherData("TestCity")).thenReturn("{\"current\":{\"temp_c\":20.0, \"condition\":{\"text\":\"Sunny\"}, \"humidity\":60}}");

        String viewName = cityController.cityDetails(1L, model);

        assertEquals("city", viewName);
        verify(model).addAttribute("city", city);
        verify(model).addAttribute("temperature", 20.0);
        verify(model).addAttribute("conditionText", "Sunny");
        verify(model).addAttribute("humidity", 60);
        verify(model).addAttribute("cityImageUrl", "testImageUrl");
        verify(model).addAttribute("description", "Beautiful test city");
    }
}

