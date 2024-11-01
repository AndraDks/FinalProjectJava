package com.bookingApp.service;

import com.bookingApp.model.City;
import com.bookingApp.model.Country;
import com.bookingApp.repository.CityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class CityService {
    private final CityRepository cityRepository;

    @Autowired
    public CityService(CityRepository cityRepository) {
        this.cityRepository = cityRepository;
    }

    //usage admin contr
    public City findByNameAndCountry(String name, Country country) {
//        System.out.println("Searching for city: " + name + " in country: " + country.getName());
        return cityRepository.findByNameAndCountry(name, country);
    }

    //save/update in db
    public void save(City city) {
        System.out.println("Saving city: " + city.getName() + " in country: " + city.getCountry().getName());
        cityRepository.save(city);
    }

    // all cities
    public List<City> findAll() {
        return cityRepository.findAll();
    }

    public void deleteAll(List<City> orphanCities) {
        cityRepository.deleteAll();
    }
}

