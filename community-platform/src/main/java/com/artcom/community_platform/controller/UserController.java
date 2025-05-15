package com.artcom.community_platform.controller;

import com.artcom.community_platform.entity.User;
import com.artcom.community_platform.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/profile")
    public String showProfile(Model model) {
        User user = userService.getAllUsers().get(0); // Replace with session user in future
        model.addAttribute("user", user);
        return "profile"; // profile.html
    }

    @GetMapping("/profile/edit")
    public String showEditProfileForm(Model model) {
        User user = userService.getAllUsers().get(0); // Simulating logged-in user
        model.addAttribute("user", user);
        return "edit_profile"; // edit_profile.html
    }

    @PostMapping("/profile/edit")
    public String updateProfile(@ModelAttribute("user") User updatedUser) {
        // Fetch existing user and update only editable fields
        User existingUser = userService.getUserById(updatedUser.getId());
        existingUser.setUsername(updatedUser.getUsername());
        existingUser.setEmail(updatedUser.getEmail());
        existingUser.setBio(updatedUser.getBio());
        existingUser.setWebsite(updatedUser.getWebsite());
        existingUser.setInstagram(updatedUser.getInstagram());

        userService.updateUser(existingUser);
        return "redirect:/profile";
    }

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new User());
        return "register"; // register.html
    }

    @PostMapping("/register")
    public String registerUser(@ModelAttribute("user") User user) {
        if (user.getRole() == null || user.getRole().isEmpty()) {
            user.setRole("ARTIST"); // Default role
        }
        userService.createUser(user);
        return "redirect:/profile";
    }
}
