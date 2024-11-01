package com.bookingApp.controller;

import com.bookingApp.model.City;
import com.bookingApp.model.Country;
import com.bookingApp.model.Hotel;
import com.bookingApp.repository.CityRepository;
import com.bookingApp.repository.CountryRepository;
import com.bookingApp.repository.HotelRepository;
import com.bookingApp.service.HotelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/hotel")
public class HotelController {

    private final HotelService hotelService;

    @Autowired
    public HotelController(HotelService hotelService) {
        this.hotelService = hotelService;
    }

    @GetMapping("/{id}")
    public String hotelDetails(@PathVariable Long id, Model model) {
        Hotel hotel = hotelService.getHotelById(id);
        model.addAttribute("hotel", hotel);

        //check role
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        boolean isAdmin = authentication.getAuthorities().stream()
                .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ADMIN"));
        boolean isEditor = authentication.getAuthorities().stream()
                .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("EDITOR"));
        boolean isUser = authentication.getAuthorities().stream()
                .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("USER"));

        model.addAttribute("isAdmin", isAdmin);
        model.addAttribute("isEditor", isEditor);
        model.addAttribute("isUser", isUser);

        return "hotel";
    }

    @GetMapping("/{id}/delete")
    public String deleteHotel(@PathVariable Long id) {
        hotelService.deleteHotelAndCheckOrphans(id); // delete orphans
        return "redirect:/home";
    }

    @PostMapping("/book")
    public String bookHotel(
            @RequestParam Long hotelId,
            @RequestParam int days,
            @RequestParam int persons,
            @RequestParam String startDate,
            @RequestParam(required = false) boolean allInclusive,
            Model model
    ) {
        Hotel hotel = hotelService.getHotelById(hotelId);
//                .orElseThrow(() -> new IllegalArgumentException("Invalid hotel ID"));

        //calculate total price
        double totalPrice = hotel.getPricePerNight() * days * persons;
        if (allInclusive) {
            totalPrice += 50 * persons * days;
        }

        model.addAttribute("totalPrice", totalPrice);
        model.addAttribute("hotel", hotel);
        model.addAttribute("days", days);
        model.addAttribute("persons", persons);
        model.addAttribute("startDate", startDate);
        model.addAttribute("allInclusive", allInclusive);

        return "booking_confirmation";
    }

    @GetMapping("/question")
    public String reportProblem(Model model) {
        model.addAttribute("message", "If you have any questions or concerns, don't hesitate to contact us.");
        return "chat";
    }

    @GetMapping("/report")
    public String askQuestion(Model model) {
        model.addAttribute("message", "Any problem you encounter with the platform, don't hesitate to contact us.");
        return "chat";
    }

    @GetMapping("/confirmation")
    public String showConfirmationPage(Model model) {
        model.addAttribute("confirmationMessage", "Your message has been recorded, someone from our team will respond as soon as possible.");
        return "confirmation";
    }
}
