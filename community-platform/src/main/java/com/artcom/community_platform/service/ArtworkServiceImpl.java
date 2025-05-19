package com.artcom.community_platform.service;

import com.artcom.community_platform.entity.Artwork;
import com.artcom.community_platform.entity.User;
import com.artcom.community_platform.repository.ArtworkRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public class ArtworkServiceImpl extends ArtworkService {

    @Autowired
    private ArtworkRepository artworkRepository;

    public Artwork createArtwork(User artist, String title, String category, MultipartFile imageFile) throws Exception {
        // Implementation of createArtwork method
        return null; // Placeholder return, actual implementation needed
    }

    public List<Artwork> getAllArtworks() {
        return artworkRepository.findAll();
    }

    public Artwork getArtworkById(Long id) {
        return artworkRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Artwork not found with id: " + id));
    }

    public Artwork getArtworkByTitle(String title) {
        return artworkRepository.findByTitle(title)
                .orElseThrow(() -> new RuntimeException("Artwork not found with title: " + title));
    }

    public void deleteArtwork(Long id, User user) throws Exception {
        // Implementation of deleteArtwork method
    }

    public List<Artwork> getArtworksByArtist(User artist) {
        return artworkRepository.findByArtistId(artist.getId());
    }
}


