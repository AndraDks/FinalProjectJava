package com.bookingApp.controller;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.bookingApp.service.APIsService;

@Controller
public class APIsController {

    private final APIsService apisService;

    public APIsController(APIsService apisService) {
        this.apisService = apisService;
    }

    @GetMapping("/weather")
    public String getWeather(@RequestParam("wethercity") String city, Model model) throws JSONException {
        // fetch weather data from service
        String weatherData = apisService.getWeatherData(city);

        // parse json response
        JSONObject json = new JSONObject(weatherData);
        String locationName = json.getJSONObject("location").getString("name");
        String country = json.getJSONObject("location").getString("country");
        double temperature = json.getJSONObject("current").getDouble("temp_c");
        String conditionText = json.getJSONObject("current").getJSONObject("condition").getString("text");
        int humidity = json.getJSONObject("current").getInt("humidity");

        // ddd details to model
        model.addAttribute("locationName", locationName);
        model.addAttribute("temperature", temperature);
        model.addAttribute("conditionText", conditionText);
        model.addAttribute("humidity", humidity);

        // fetch image
        String imageUrl = apisService.getImageUrl(locationName + " " + country);
        model.addAttribute("imageUrl", imageUrl);

        return "weather";
    }

    @GetMapping("/weather-check")
    public String showWeatherCheckPage() {
        return "weather_check";
    }
}
