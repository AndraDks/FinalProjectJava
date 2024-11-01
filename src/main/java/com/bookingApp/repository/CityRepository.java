package com.bookingApp.repository;

import com.bookingApp.model.City;
import com.bookingApp.model.Country;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CityRepository extends JpaRepository<City, Long> {
    List<City> findByCountry(Country country);
//    List<Hotel> findByCity(City city);

    City findByNameAndCountry(String name, Country country);
}


