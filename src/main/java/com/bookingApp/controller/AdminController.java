package com.bookingApp.controller;

import com.bookingApp.model.City;
import com.bookingApp.model.Country;
import com.bookingApp.model.Hotel;
import com.bookingApp.model.HotelDTO;
import com.bookingApp.service.CityService;
import com.bookingApp.service.CountryService;
import com.bookingApp.service.HotelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private CountryService countryService;

    @Autowired
    private CityService cityService;

    @Autowired
    private HotelService hotelService;

    @GetMapping("/add-hotel")
    @PreAuthorize("hasRole('ADMIN')")
    public String showAddHotelForm(Model model) {
        model.addAttribute("hotel", new HotelDTO());
        return "add_hotel";
    }

    @PostMapping("/add-hotel")
    public String addHotel(@ModelAttribute HotelDTO hotelDTO) {
//        System.out.println("Country Name: " + hotelDTO.getCountryName());
//        System.out.println("City Name: " + hotelDTO.getCityName());
//        System.out.println("Hotel Name: " + hotelDTO.getHotelName());
//        System.out.println("Price per Night: " + hotelDTO.getPricePerNight());
//        System.out.println("Has All Inclusive: " + hotelDTO.isHasAllInclusive());

        //check if country
        Country country = countryService.findByName(hotelDTO.getCountryName());
        if (country == null) {
            // if !country
            country = new Country();
            country.setName(hotelDTO.getCountryName());
            countryService.save(country);
        }

        // check if city
        City city = cityService.findByNameAndCountry(hotelDTO.getCityName(), country);
        if (city == null) {
            city = new City();
            city.setName(hotelDTO.getCityName());
            city.setCountry(country);
            cityService.save(city);
        }

        // add hotel
        Hotel hotel = new Hotel();
        hotel.setName(hotelDTO.getHotelName());
        hotel.setPricePerNight((double) hotelDTO.getPricePerNight());
        hotel.setHasAllInclusive(hotelDTO.isHasAllInclusive());
        hotel.setCity(city);
        hotelService.save(hotel);

        return "redirect:/home";
    }

    @GetMapping("/edit-hotel/{id}")
    public String showEditHotelForm(@PathVariable Long id, Model model) {
        Hotel hotel = hotelService.getHotelById(id);
        model.addAttribute("hotel", hotel); // add hotel details to view
        return "edit_hotel";
    }

    @PostMapping("/edit-hotel/{id}")
    public String updateHotel(@PathVariable Long id, @ModelAttribute HotelDTO hotelDTO) {
//        System.out.println("Updating Hotel Name: " + hotelDTO.getHotelName());

        // find hotel
        Hotel hotel = hotelService.getHotelById(id);

        // update hotel details
        if (hotelDTO.getHotelName() != null && !hotelDTO.getHotelName().isEmpty()) {
            hotel.setName(hotelDTO.getHotelName());
        } else {
            System.out.println("Hotel name is null or empty");
        }

        hotel.setPricePerNight((double) hotelDTO.getPricePerNight());
        hotel.setHasAllInclusive(hotelDTO.isHasAllInclusive());

        // save updated hotel
        hotelService.save(hotel);

        return "redirect:/home";
    }



}

