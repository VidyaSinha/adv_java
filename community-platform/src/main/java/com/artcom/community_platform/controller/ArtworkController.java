package com.artcom.community_platform.controller;

import com.artcom.community_platform.entity.Artwork;
import com.artcom.community_platform.entity.User;
import com.artcom.community_platform.service.ArtworkService;
import com.artcom.community_platform.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/artworks")
public class ArtworkController {

    @Autowired
    private ArtworkService artworkService;

    @Autowired
    private UserService userService;

    @GetMapping
    public String listArtworks(Model model) {
        List<Artwork> artworks = artworkService.getAllArtworks();
        model.addAttribute("artworks", artworks);
        return "artwork_list"; // artwork_list.html
    }

    @GetMapping("/new")
    public String showCreateForm(Model model) {
        // Create a default user for new artworks
        User defaultUser = userService.getUserById(1L); // Assuming user with ID 1 exists
        Artwork artwork = new Artwork("New Artwork", "default-image.jpg", defaultUser, "Uncategorized");
        model.addAttribute("artwork", artwork);
        return "create_artwork"; // create_artwork.html
    }

    @PostMapping
    public String createArtwork(@ModelAttribute Artwork artwork) {
        artworkService.createArtwork(artwork);
        return "redirect:/artworks";
    }

    @GetMapping("/{id}")
    public String viewArtwork(@PathVariable Long id, Model model) {
        Artwork artwork = artworkService.getArtworkById(id);
        model.addAttribute("artwork", artwork);
        return "view_artwork"; // view_artwork.html
    }

    @PostMapping("/{id}/delete")
    public String deleteArtwork(@PathVariable Long id) {
        artworkService.deleteArtwork(id);
        return "redirect:/artworks";
    }
}
