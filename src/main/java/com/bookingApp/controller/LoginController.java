package com.bookingApp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoginController {

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @PostMapping("/login")
    public String login(@RequestParam String username, @RequestParam String password, Model model) {
        // check credentials
        boolean isAuthenticated = checkCredentials(username, password);

        if (!isAuthenticated) {
            model.addAttribute("error", "Invalid credentials. Please try again.");
            return "login";
        }

        return "redirect:/home";
    }


    private boolean checkCredentials(String username, String password) {
        return "user".equals(username) && "password".equals(password);
    }
}
