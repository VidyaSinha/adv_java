package com.artcom.community_platform.controller;

import com.artcom.community_platform.entity.User;
import com.artcom.community_platform.entity.Artwork;
import com.artcom.community_platform.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        model.addAttribute("totalUsers", adminService.getAllUsers().size());
        model.addAttribute("totalArtworks", adminService.getAllArtworks().size());
        return "admin/dashboard";
    }

    @GetMapping("/users")
    public String listUsers(Model model) {
        model.addAttribute("users", adminService.getAllUsers());
        return "admin/users";
    }

    @GetMapping("/artworks")
    public String listArtworks(Model model) {
        model.addAttribute("artworks", adminService.getAllArtworks());
        return "admin/artworks";
    }

    @GetMapping("/users/{id}")
    public String viewUser(@PathVariable Long id, Model model) {
        model.addAttribute("user", adminService.getUserById(id));
        return "admin/user-details";
    }

    @GetMapping("/artworks/{id}")
    public String viewArtwork(@PathVariable Long id, Model model) {
        model.addAttribute("artwork", adminService.getArtworkById(id));
        return "admin/artwork-details";
    }

    @PostMapping("/users/{id}/delete")
    public String deleteUser(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            adminService.deleteUser(id);
            redirectAttributes.addFlashAttribute("success", "User deleted successfully");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error deleting user: " + e.getMessage());
        }
        return "redirect:/admin/users";
    }

    @PostMapping("/artworks/{id}/delete")
    public String deleteArtwork(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            adminService.deleteArtwork(id);
            redirectAttributes.addFlashAttribute("success", "Artwork deleted successfully");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error deleting artwork: " + e.getMessage());
        }
        return "redirect:/admin/artworks";
    }

    @PostMapping("/users/{id}/update")
    public String updateUser(@PathVariable Long id, @ModelAttribute User user, RedirectAttributes redirectAttributes) {
        try {
            adminService.updateUser(user);
            redirectAttributes.addFlashAttribute("success", "User updated successfully");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error updating user: " + e.getMessage());
        }
        return "redirect:/admin/users/" + id;
    }

    @PostMapping("/artworks/{id}/update")
    public String updateArtwork(@PathVariable Long id, @ModelAttribute Artwork artwork, RedirectAttributes redirectAttributes) {
        try {
            adminService.updateArtwork(artwork);
            redirectAttributes.addFlashAttribute("success", "Artwork updated successfully");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error updating artwork: " + e.getMessage());
        }
        return "redirect:/admin/artworks/" + id;
    }
} 