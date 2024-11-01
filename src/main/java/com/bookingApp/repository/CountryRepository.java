package com.bookingApp.repository;

import com.bookingApp.model.City;
import com.bookingApp.model.Country;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CountryRepository extends JpaRepository<Country, Long> {
    Country findByName(String name);

//    List<City> findByCountry(Country country);
}

