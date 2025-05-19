package com.artcom.community_platform.controller;

import com.artcom.community_platform.entity.User;
import com.artcom.community_platform.entity.UserProfile;
import com.artcom.community_platform.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.transaction.annotation.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Controller
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    // === Show Profile Page ===
    @GetMapping("/profile")
    public String showProfile(@AuthenticationPrincipal UserDetails userDetails,
                              Model model,
                              RedirectAttributes redirectAttributes) {
        try {
            if (userDetails == null) {
                logger.error("UserDetails is null - user not authenticated");
                redirectAttributes.addFlashAttribute("error", "Please log in to view your profile");
                return "redirect:/login";
            }

            User user = userService.getUserByEmail(userDetails.getUsername());
            if (user == null) {
                redirectAttributes.addFlashAttribute("error", "User not found");
                return "redirect:/login";
            }

            UserProfile profile = userService.getUserProfileByUser(user);
            if (profile == null) {
                redirectAttributes.addFlashAttribute("error", "User profile not found");
                return "redirect:/login";
            }

            model.addAttribute("user", user);
            model.addAttribute("profile", profile);
            model.addAttribute("artworks", userService.getUserArtworks(user));
            model.addAttribute("followers", userService.getUserFollowers(user));
            model.addAttribute("following", userService.getUserFollowing(user));

            return "profile";
        } catch (Exception e) {
            logger.error("Error loading profile: ", e);
            redirectAttributes.addFlashAttribute("error", "Error loading profile: " + e.getMessage());
            return "redirect:/login";
        }
    }

    // === Show Edit Profile Form ===
    @GetMapping("/profile/edit")
    public String showEditProfileForm(@AuthenticationPrincipal UserDetails userDetails,
                                      Model model,
                                      RedirectAttributes redirectAttributes) {
        try {
            User user = userService.getUserByEmail(userDetails.getUsername());
            if (user == null) {
                redirectAttributes.addFlashAttribute("error", "User not found");
                return "redirect:/login";
            }

            UserProfile profile = userService.getUserProfileByUser(user);
            if (profile == null) {
                redirectAttributes.addFlashAttribute("error", "User profile not found");
                return "redirect:/login";
            }

            model.addAttribute("user", user);
            model.addAttribute("profile", profile);
            return "edit_profile";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error loading profile: " + e.getMessage());
            return "redirect:/login";
        }
    }

    // === Handle Profile Update ===
    @PostMapping("/profile/edit")
    @Transactional
    public String updateProfile(@AuthenticationPrincipal UserDetails userDetails,
                                @ModelAttribute User updatedUser,
                                @ModelAttribute UserProfile updatedProfile,
                                RedirectAttributes redirectAttributes) {
        try {
            User currentUser = userService.getUserByEmail(userDetails.getUsername());
            if (currentUser == null) {
                redirectAttributes.addFlashAttribute("error", "User not found");
                return "redirect:/login";
            }

            UserProfile currentProfile = userService.getUserProfileByUser(currentUser);
            if (currentProfile == null) {
                redirectAttributes.addFlashAttribute("error", "User profile not found");
                return "redirect:/login";
            }

            // Update user fields
            currentUser.setEmail(updatedUser.getEmail());

            // Update profile fields
            currentProfile.setUsername(updatedProfile.getUsername());
            currentProfile.setBio(updatedProfile.getBio());
            currentProfile.setWebsite(updatedProfile.getWebsite());
            currentProfile.setInstagram(updatedProfile.getInstagram());
            currentProfile.setPortfolio(updatedProfile.getPortfolio());

            // Save both user and profile
            userService.updateUser(currentUser);
            userService.updateUserProfile(currentProfile);

            redirectAttributes.addFlashAttribute("success", "Profile updated successfully");
            return "redirect:/profile";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error updating profile: " + e.getMessage());
            return "redirect:/profile/edit";
        }
    }
}
