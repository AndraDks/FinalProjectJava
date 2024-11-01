package com.bookingApp.controller;

import com.bookingApp.repository.CityRepository;
import com.bookingApp.service.APIsService;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/city")
public class CityController {

    private final CityRepository cityRepository;
    private final APIsService APIsService;

    @Autowired
    public CityController(CityRepository cityRepository, APIsService APIsService) {
        this.cityRepository = cityRepository;
        this.APIsService = APIsService;
    }

    @GetMapping("/{id}")
    public String cityDetails(@PathVariable Long id, Model model) {
        return cityRepository.findById(id)
                .map(city -> {
                    model.addAttribute("city", city);
                    model.addAttribute("hotels", city.getHotels());

                    // parse city`s weather info
                    String weatherData = APIsService.getWeatherData(city.getName());
                    if (weatherData != null) {
                        JSONObject json = new JSONObject(weatherData);
                        double temperature = json.getJSONObject("current").getDouble("temp_c");
                        String conditionText = json.getJSONObject("current").getJSONObject("condition").getString("text");
                        int humidity = json.getJSONObject("current").getInt("humidity");

                        model.addAttribute("temperature", temperature);
                        model.addAttribute("conditionText", conditionText);
                        model.addAttribute("humidity", humidity);
                    }

                    if (city.getCityImageUrl() == null || city.getCityImageUrl().isEmpty()) {
                        String cityImageUrl = APIsService.getImageUrl(city.getName() + " " + city.getCountry().getName());

                        city.setCityImageUrl(cityImageUrl);
                        cityRepository.save(city);
                    }


                    model.addAttribute("cityImageUrl", city.getCityImageUrl());
                    model.addAttribute("description", city.getCityDescription());

                    return "city";
                })
                .orElse("error");
    }
}
