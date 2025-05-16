package com.artcom.community_platform.controller;

import com.artcom.community_platform.entity.Artwork;
import com.artcom.community_platform.entity.User;
import com.artcom.community_platform.service.ArtworkService;
import com.artcom.community_platform.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Controller
@RequestMapping("/artwork")
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
        if (artwork.getArtist() == null || artwork.getArtist().getId() == null) {
            throw new IllegalArgumentException("Artist must be provided");
        }
        artworkService.createArtwork(artwork);
        return "redirect:/users/profile"; // or wherever your user profile page is
    }

//    @GetMapping("/profile")
//    public String userProfile(Model model, Principal principal) {
//        User user = userService.findByUsername(principal.getName());
//        model.addAttribute("user", user);
//        return "profile";
//    }

    @GetMapping("/{id}")
    public String viewArtwork(@PathVariable Long id, Model model) {
        Artwork artwork = artworkService.getArtworkById(id);
        model.addAttribute("artwork", artwork);
        return "view_artwork"; // view_artwork.html
    }
    @PostMapping("/artwork")
    public String uploadArtwork(
            @RequestParam("title") String title,
            @RequestParam("category") String category,
            @RequestParam("artist.id") Long artistId,
            @RequestParam("imageFile") MultipartFile imageFile
    ) {
        try {
            // Handle file saving logic (e.g., to local or cloud storage)
            // For now, assume we just get the original filename
            String imageUrl = "/uploads/" + imageFile.getOriginalFilename(); // you should save the file and generate actual URL

            // Create the artwork object
            User artist = userService.getUserById(artistId);
            Artwork artwork = new Artwork(title, imageUrl, artist, category);

            // Save the artwork
            artworkService.createArtwork(artwork);

            return "redirect:/users/profile";
        } catch (Exception e) {
            e.printStackTrace();
            return "error"; // or a proper error page/view
        }
    }


    @PostMapping("/{id}/delete")
    public String deleteArtwork(@PathVariable Long id) {
        artworkService.deleteArtwork(id);
        return "redirect:/artworks";
    }
}
