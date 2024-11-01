package com.bookingApp.service;

import com.bookingApp.model.Country;
import com.bookingApp.repository.CountryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CountryService {
    private final CountryRepository countryRepository;

    @Autowired
    public CountryService(CountryRepository countryRepository) {
        this.countryRepository = countryRepository;
    }

    //find in db
    public Country findByName(String name) {
        System.out.println("Searching for country: " + name);
        return countryRepository.findByName(name);
    }

    //save/update in db
    public void save(Country country) {
        System.out.println("Saving country: " + country.getName());
        countryRepository.save(country);
    }
}

