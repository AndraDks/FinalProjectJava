package com.bookingApp.service;

import com.bookingApp.model.City;
import com.bookingApp.model.Country;
import com.bookingApp.repository.CityRepository;
import com.bookingApp.service.CityService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class CityServiceTest {

    @Mock
    private CityRepository cityRepository;

    @InjectMocks
    private CityService cityService;

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
    }

    @Test
    void testFindByNameAndCountry() {
        when(cityRepository.findByNameAndCountry("TestCity", country)).thenReturn(city);

        City foundCity = cityService.findByNameAndCountry("TestCity", country);

        assertEquals(city, foundCity);
        verify(cityRepository).findByNameAndCountry("TestCity", country);
    }

    @Test
    void testSaveCity() {
        cityService.save(city);

        verify(cityRepository).save(city);
    }
}

