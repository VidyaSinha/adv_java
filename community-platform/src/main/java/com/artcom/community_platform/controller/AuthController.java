package com.artcom.community_platform.controller;

import com.artcom.community_platform.entity.User;
import com.artcom.community_platform.entity.UserProfile;
import com.artcom.community_platform.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class AuthController {

    private final UserService userService;

    @Autowired
    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/signup")
    public String showSignupForm(Model model) {
        model.addAttribute("roles", UserProfile.UserRole.values());
        return "signup";
    }

    @PostMapping("/signup")
    public String processSignup(@RequestParam String username,
                              @RequestParam String email,
                              @RequestParam String password,
                              @RequestParam String confirmPassword,
                              @RequestParam String role,
                              RedirectAttributes redirectAttributes) {
        try {
            // Validate input
            if (username == null || username.trim().isEmpty()) {
                throw new IllegalArgumentException("Username cannot be empty");
            }
            if (email == null || email.trim().isEmpty()) {
                throw new IllegalArgumentException("Email cannot be empty");
            }
            if (password == null || password.trim().isEmpty()) {
                throw new IllegalArgumentException("Password cannot be empty");
            }
            if (!password.equals(confirmPassword)) {
                throw new IllegalArgumentException("Passwords do not match");
            }
            if (role == null || role.trim().isEmpty()) {
                throw new IllegalArgumentException("Role must be selected");
            }

            // Password validation
            String passwordPattern = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[!@#$%^&*()_\\-+=<>?{}\\[\\]~])[A-Za-z\\d!@#$%^&*()_\\-+=<>?{}\\[\\]~]{8,}$";
            if (!password.matches(passwordPattern)) {
                throw new IllegalArgumentException("Password must be at least 8 characters long and include:\n- 1 uppercase\n- 1 lowercase\n- 1 number\n- 1 special character");
            }

            // Create user and profile
            User user = userService.createUser(email, password, username);
            UserProfile profile = userService.createUserProfile(user, username, UserProfile.UserRole.valueOf(role));
            
            redirectAttributes.addFlashAttribute("success", "Account created successfully! Please sign in.");
            return "redirect:/login";
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/signup";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error creating account: " + e.getMessage());
            return "redirect:/signup";
        }
    }
} 