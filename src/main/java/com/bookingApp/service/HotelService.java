package com.bookingApp.service;

import com.bookingApp.model.City;
import com.bookingApp.model.Country;
import com.bookingApp.model.Hotel;
import com.bookingApp.repository.CityRepository;
import com.bookingApp.repository.CountryRepository;
import com.bookingApp.repository.HotelRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HotelService {
    private final HotelRepository hotelRepository;
    private final CityRepository cityRepository;
    private final CountryRepository countryRepository;

    @Autowired
    public HotelService(HotelRepository hotelRepository, CityRepository cityRepository, CountryRepository countryRepository) {
        this.hotelRepository = hotelRepository;
        this.cityRepository = cityRepository;
        this.countryRepository = countryRepository;
    }

    public List<Hotel> getAllHotels() {
        return hotelRepository.findAll();
    }

    public Hotel addHotel(Hotel hotel) {
        return hotelRepository.save(hotel);
    }

    public void save(Hotel hotel) {
        hotelRepository.save(hotel);
    }

    public Hotel getHotelById(Long id) {
        return hotelRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid hotel Id:" + id));
    }

    public void updateHotelName(String oldName, String newName) {
        List<Hotel> hotels = hotelRepository.findByName(oldName);
        for (Hotel hotel : hotels) {
            hotel.setName(newName);
            hotelRepository.save(hotel);  // save updated hotel
        }
    }

    @Transactional
    public void deleteHotelAndCheckOrphans(Long hotelId) {
        // find hotel by id, get city & country
        Hotel hotel = hotelRepository.findById(hotelId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid hotel ID: " + hotelId));
        System.out.println("Found hotel: " + hotel.getName());

        City city = hotel.getCity();
        Country country = city.getCountry();

        // delete hotel
        System.out.println("Deleting hotel: " + hotel.getName());
        hotelRepository.delete(hotel);
        hotelRepository.flush();  // delete from db

        // check if city has hotels
        List<Hotel> hotelsInCity = hotelRepository.findByCityId(city.getId());
        System.out.println("Hotels remaining in city " + city.getName() + ": " + hotelsInCity.size());

        // if !hotels, delete city
        if (hotelsInCity.isEmpty()) {
            System.out.println("Deleting city: " + city.getName());
            cityRepository.delete(city);
            cityRepository.flush();
        } else {
            System.out.println("City " + city.getName() + " still has hotels.");
        }

        // check if country has cities
        List<City> citiesInCountry = cityRepository.findByCountry(country);
        System.out.println("Cities remaining in country " + country.getName() + ": " + citiesInCountry.size());

        if (citiesInCountry.isEmpty()) {
            System.out.println("Deleting country: " + country.getName());
            countryRepository.delete(country);
            countryRepository.flush();
        } else {
            System.out.println("Country " + country.getName() + " still has cities.");
        }
    }

    public void deleteHotel(Long id) {
        hotelRepository.deleteById(id);
    }
}
