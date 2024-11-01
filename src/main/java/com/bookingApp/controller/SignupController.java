package com.bookingApp.controller;

import com.bookingApp.model.User;
import com.bookingApp.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping; // Import necesar
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class SignupController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public SignupController(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/signup")
    public String showSignupForm(Model model) {
        return "signup";
    }

    @PostMapping("/signup")
    public String registerUser(@RequestParam String username,
                               @RequestParam String password,
                               @RequestParam String role,
                               Model model) {
        // check if user
        if (userRepository.findByUsername(username).isPresent()) {
            model.addAttribute("error", "Username already exists!");
            return "signup";
        }

        //create user
        User user = new User();
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(password));
        user.setRole(role.toUpperCase());

        userRepository.save(user);

        model.addAttribute("success", "User registered successfully! Please login.");
        return "redirect:/login";
    }
}
