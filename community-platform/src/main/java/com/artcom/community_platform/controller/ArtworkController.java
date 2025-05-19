package com.artcom.community_platform.controller;

import com.artcom.community_platform.entity.Artwork;
import com.artcom.community_platform.entity.User;
import com.artcom.community_platform.repository.ArtworkRepository;
import com.artcom.community_platform.repository.UserRepository;
import com.artcom.community_platform.service.ArtworkService;
import com.artcom.community_platform.service.UserService;
import com.artcom.community_platform.dto.ArtworkDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:8080")
@Controller
@RequestMapping("/artwork")
public class ArtworkController {

    @Autowired
    private ArtworkService artworkService;

    @Autowired
    private UserService userService;

    @Autowired
    private ArtworkRepository artworkRepository;

    @Autowired
    private UserRepository userRepository;

    // Upload artwork with file and form data
    @PostMapping("/upload")
    @ResponseBody
    public ResponseEntity<?> uploadArtwork(
            @RequestParam("title") String title,
            @RequestParam("category") String category,
            @RequestParam("imageFile") MultipartFile imageFile,
            @RequestParam("artist.id") Long artistId,
            @AuthenticationPrincipal UserDetails userDetails) {
        
        if (userDetails == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(Map.of("message", "User must be authenticated"));
        }

        try {
            User artist = userService.getUserById(artistId);
            if (artist == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("message", "Artist not found"));
            }

            // Verify that the authenticated user matches the artist
            if (!artist.getEmail().equals(userDetails.getUsername())) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(Map.of("message", "You can only upload artwork as yourself"));
            }

            Artwork artwork = new Artwork();
            artwork.setTitle(title);
            artwork.setCategory(Artwork.ArtworkGenre.valueOf(category.toUpperCase()));
            artwork.setArtist(artist);

            // Handle image upload
            if (imageFile != null && !imageFile.isEmpty()) {
                String imageUrl = artworkService.saveImage(imageFile);
                artwork.setImageUrl(imageUrl);
            }

            artworkRepository.save(artwork);
            return ResponseEntity.ok(Map.of("message", "Artwork saved successfully"));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(Map.of("message", "Invalid category: " + category));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(Map.of("message", "Error saving artwork: " + e.getMessage()));
        }
    }

    // Display all artworks
    @GetMapping("/artwork_list")
    public String listArtworks(Model model) {
        List<Artwork> artworks = artworkService.getAllArtworks();
        model.addAttribute("artworks", artworks);
        return "artwork_list"; // Template: artwork_list.html
    }

    // Show form to create new artwork
    @GetMapping("/new")
    public String showCreateForm(Model model) {
        User defaultUser = userService.getUserById(1L); // Dummy fallback user
        Artwork artwork = new Artwork("New Artwork", "default-image.jpg", defaultUser, Artwork.ArtworkGenre.UNCATEGORIZED);
        model.addAttribute("artwork", artwork);
        return "create_artwork"; // Template: create_artwork.html
    }

    // Create new artwork (form submission with Thymeleaf)
    @PostMapping
    public String createArtwork(@ModelAttribute Artwork artwork) {
        try {
            Artwork.ArtworkGenre categoryEnum = Artwork.ArtworkGenre.valueOf(artwork.getCategory().toString().toUpperCase());
            artwork.setCategory(categoryEnum);
            artworkService.createArtwork(artwork);
            return "redirect:/profile";
        } catch (IllegalArgumentException e) {
            return "redirect:/artwork/new?error=invalid_genre";
        }
    }

    // View single artwork by ID
    @GetMapping("/{id}")
    public String viewArtwork(@PathVariable Long id, Model model) {
        Artwork artwork = artworkService.getArtworkById(id);
        model.addAttribute("artwork", artwork);
        return "view_artwork"; // Template: view_artwork.html
    }

    // Delete artwork
    @PostMapping("/{id}/delete")
    public String deleteArtwork(@PathVariable Long id) {
        artworkService.deleteArtwork(id);
        return "redirect:/artwork"; // Matches GET /artwork for refreshed list
    }
}
