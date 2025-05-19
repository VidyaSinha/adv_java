package com.artcom.community_platform.service;

import com.artcom.community_platform.dto.CritiqueDTO;
import com.artcom.community_platform.entity.Critique;

import java.util.List;

public interface CritiqueService {

    Critique createCritique(CritiqueDTO critiqueDTO, Long criticId) throws Exception;

    /**
     * Get a critique by its ID
     * @param id The ID of the critique
     * @return The critique if found
     * @throws RuntimeException if the critique is not found
     */
    Critique getCritiqueById(Long id);

    List<Critique> getCritiquesByArtwork(Long artworkId);

    List<Critique> getCritiquesByCritic(Long criticId);

    List<Critique> getAllCritiques();
} 