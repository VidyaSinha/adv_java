package com.artcom.community_platform.service;

import com.artcom.community_platform.entity.Artwork;
import com.artcom.community_platform.entity.User;
import com.artcom.community_platform.repository.ArtworkRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

@Service
public class ArtworkService {
    private final ArtworkRepository artworkRepository;
    
    @Value("${upload.path}")
    private String uploadPath;

    public ArtworkService(ArtworkRepository artworkRepository) {
        this.artworkRepository = artworkRepository;
    }

    public Artwork saveArtwork(Artwork artwork, MultipartFile imageFile, User artist) throws IOException {
        if (imageFile != null && !imageFile.isEmpty()) {
            String fileName = UUID.randomUUID().toString() + "_" + imageFile.getOriginalFilename();
            Path uploadDir = Paths.get(uploadPath);
            
            if (!Files.exists(uploadDir)) {
                Files.createDirectories(uploadDir);
            }
            
            Path filePath = uploadDir.resolve(fileName);
            Files.copy(imageFile.getInputStream(), filePath);
            
            artwork.setImageUrl("/uploads/" + fileName);
        }
        
        artwork.setArtist(artist);
        return artworkRepository.save(artwork);
    }

    public List<Artwork> getAllArtworks() {
        return artworkRepository.findAll();
    }

    public List<Artwork> getArtworksByGenre(Artwork.ArtworkGenre genre) {
        return artworkRepository.findByGenre(genre);
    }

    public Artwork getArtworkById(Long id) {
        return artworkRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Artwork not found"));
    }
} 