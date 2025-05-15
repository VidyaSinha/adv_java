package com.artcom.community_platform.service;

import com.artcom.community_platform.entity.Artwork;
import com.artcom.community_platform.repository.ArtworkRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ArtworkService {

    @Autowired
    private ArtworkRepository artworkRepository;

    public Artwork createArtwork(Artwork artwork) {
        return artworkRepository.save(artwork);
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

    public void deleteArtwork(Long id) {
        artworkRepository.deleteById(id);
    }

    public List<Artwork> getArtworksByArtist(Long artistId) {
        return artworkRepository.findByArtistId(artistId);
    }
}


