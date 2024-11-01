package com.bookingApp.controller;

import com.bookingApp.model.Country;
import com.bookingApp.repository.CountryRepository;
import com.bookingApp.service.CityService;
import com.bookingApp.service.DescriptionService;
import com.bookingApp.service.APIsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;
import java.util.List;

@Controller
public class HomeController {

    private final CountryRepository countryRepository;
    private final CityService cityRepository;

    @Autowired
    public HomeController(CountryRepository countryRepository, CityService cityRepository) {
        this.countryRepository = countryRepository;
        this.cityRepository = cityRepository;
    }

    @Autowired
    private APIsService APIsService;
    @Autowired
    private DescriptionService descriptionService;



    @GetMapping("/home")
    public String home(Model model, Principal principal) {

        String username = principal != null ? principal.getName().toUpperCase() : "Guest";
        // get current auth
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // check rolls
        boolean isAdmin = authentication.getAuthorities().stream()
                .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ADMIN"));

        boolean isEditor = authentication.getAuthorities().stream()
                .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("EDITOR"));

        boolean isUser = authentication.getAuthorities().stream()
                .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("USER"));


        List<Country> countries = countryRepository.findAll();

        for (Country country : countries) {
            // check imageUrl = null & update if so
            if (country.getImageUrl() == null) {
                String imageUrl = APIsService.getImageUrl(country.getName());
                country.setImageUrl(imageUrl);
            }

            // check description = null & update if so
            if (country.getDescription() == null) {
                String description = descriptionService.getDescription(country.getName());
                country.setDescription(description);
                countryRepository.save(country);
            }

            countryRepository.save(country); //save in db
        }

        // add into to model
        model.addAttribute("countries", countryRepository.findAll());
        model.addAttribute("isAdmin", isAdmin);
        model.addAttribute("isEditor", isEditor);
        model.addAttribute("isUser", isUser);
        model.addAttribute("username", username);
//        countries.forEach(country -> System.out.println("Country: " + country.getName() + ", Image URL: " + country.getImageUrl()));

        model.addAttribute("countries", countries);

        return "home";
    }
}
