package com.artcom.community_platform.repository;

import com.artcom.community_platform.entity.Critique;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CritiqueRepository extends JpaRepository<Critique, Long> {
    List<Critique> findByArtworkId(Long artworkId);
    List<Critique> findByCriticId(Long criticId);
} 