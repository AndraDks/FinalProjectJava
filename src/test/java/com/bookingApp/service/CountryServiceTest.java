package com.bookingApp.service;

import com.bookingApp.model.Country;
import com.bookingApp.repository.CountryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class CountryServiceTest {

    private CountryService countryService;
    private CountryRepository countryRepository;

    @BeforeEach
    public void setUp() {
        countryRepository = Mockito.mock(CountryRepository.class);
        countryService = new CountryService(countryRepository);
    }

    @Test
    public void testFindByName() {
        String countryName = "Romania";
        Country expectedCountry = new Country();
        expectedCountry.setName(countryName);

        // mock the resp
        when(countryRepository.findByName(countryName)).thenReturn(expectedCountry);

        Country actualCountry = countryService.findByName(countryName);

        assertEquals(expectedCountry, actualCountry, "The country returned should match the expected country.");
    }

    @Test
    public void testSave() {
        Country countryToSave = new Country();
        countryToSave.setName("Italy");

        countryService.save(countryToSave);

        verify(countryRepository, times(1)).save(countryToSave); // Verify that save was called once with the correct country
    }
}
