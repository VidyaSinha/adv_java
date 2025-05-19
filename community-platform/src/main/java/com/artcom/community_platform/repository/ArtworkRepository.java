package com.artcom.community_platform.repository;

import com.artcom.community_platform.entity.Artwork;
import com.artcom.community_platform.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ArtworkRepository extends JpaRepository<Artwork, Long> {
//    List<Artwork> findByArtistId(User artistId);
    Optional<Artwork> findByTitle(String title);

    List<Artwork> findByArtist(User user);
//
    List<Artwork> findByArtistId(Long artistId);
//
//    List<Artwork> findByArtist(User user);
}
