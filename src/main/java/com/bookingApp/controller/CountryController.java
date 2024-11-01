package com.bookingApp.controller;

import com.bookingApp.model.Country;
import com.bookingApp.model.City;
import com.bookingApp.repository.CountryRepository;
import com.bookingApp.repository.CityRepository;
import com.bookingApp.service.DescriptionService;
import com.bookingApp.service.NewsService;
import com.bookingApp.service.APIsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class CountryController {

    private final CountryRepository countryRepository;
    private final CityRepository cityRepository;
    private final APIsService APIsService;
    private final NewsService newsService;

    @Autowired
    public CountryController(CountryRepository countryRepository, CityRepository cityRepository, APIsService APIsService, NewsService newsService) {
        this.countryRepository = countryRepository;
        this.cityRepository = cityRepository;
        this.APIsService = APIsService;
        this.newsService = newsService;
    }

    @Autowired
    private DescriptionService descriptionService;

    // disp cities 4selected country
    @GetMapping("/country/{id}")
    public String countryDetails(@PathVariable Long id, Model model) {
        Country country = countryRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid country ID"));
        List<City> cities = cityRepository.findByCountry(country);

        model.addAttribute("country", country);
        model.addAttribute("cities", cities);

        // get imgUrl 4country
        String imageUrl = APIsService.getImageUrl(country.getName());
        model.addAttribute("imageUrl", imageUrl);
        // get news 4country
        List<Object> headlines = newsService.searchNews(country.getName(), 1, 3);
        model.addAttribute("headlines", headlines);
//        System.out.println("Headlines for " + country.getName() + ": " + headlines);


        for (City city : cities) {
            // if cityImgUrl = null, get cityImgUrl
            if (city.getCityImageUrl() == null || city.getCityImageUrl().isEmpty()) {
                String cityImageUrl = APIsService.getImageUrl(city.getName());
                city.setCityImageUrl(cityImageUrl);
            }

            // if description = null, set description
            if (city.getCityDescription() == null) {
                String description = descriptionService.getDescription(city.getName());
                city.setCityDescription(description);
            }

            cityRepository.save(city);
        }

        return "country";
    }


    // list of countries
    @GetMapping("/countries")
    public String showCountries(Model model) {
        List<Country> countries = countryRepository.findAll();
        model.addAttribute("countries", countries);
        return "home";
    }
}
