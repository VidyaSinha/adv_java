package com.artcom.community_platform.repository;

import com.artcom.community_platform.entity.Artwork;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ArtworkRepository extends JpaRepository<Artwork, Long> {
    List<Artwork> findByGenre(Artwork.ArtworkGenre genre);
} 