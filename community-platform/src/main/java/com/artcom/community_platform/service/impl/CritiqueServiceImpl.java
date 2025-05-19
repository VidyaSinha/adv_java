package com.artcom.community_platform.service.impl;

import com.artcom.community_platform.dto.CritiqueDTO;
import com.artcom.community_platform.entity.Artwork;
import com.artcom.community_platform.entity.Critique;
import com.artcom.community_platform.entity.User;
import com.artcom.community_platform.repository.CritiqueRepository;
import com.artcom.community_platform.service.ArtworkService;
import com.artcom.community_platform.service.CritiqueService;
import com.artcom.community_platform.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CritiqueServiceImpl implements CritiqueService {

    private final CritiqueRepository critiqueRepository;
    private final ArtworkService artworkService;
    private final UserService userService;

    @Autowired
    public CritiqueServiceImpl(CritiqueRepository critiqueRepository,
                             ArtworkService artworkService,
                             UserService userService) {
        this.critiqueRepository = critiqueRepository;
        this.artworkService = artworkService;
        this.userService = userService;
    }

    @Override
    public Critique createCritique(CritiqueDTO critiqueDTO, Long criticId) throws Exception {
        Artwork artwork = artworkService.getArtworkById(critiqueDTO.getArtworkId());
        User critic = userService.getUserById(criticId);

        Critique critique = new Critique();
        critique.setArtwork(artwork);
        critique.setCritic(critic);
        critique.setTechnicalAnalysis(critiqueDTO.getTechnicalAnalysis());
        critique.setCompositionAndDesign(critiqueDTO.getCompositionAndDesign());
        critique.setConceptAndMessage(critiqueDTO.getConceptAndMessage());
        critique.setSuggestionsForImprovement(critiqueDTO.getSuggestionsForImprovement());

        return critiqueRepository.save(critique);
    }

    @Override
    public Critique getCritiqueById(Long id) {
        return critiqueRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Critique not found with id: " + id));
    }

    @Override
    public List<Critique> getCritiquesByArtwork(Long artworkId) {
        return critiqueRepository.findByArtworkId(artworkId);
    }

    @Override
    public List<Critique> getCritiquesByCritic(Long criticId) {
        return critiqueRepository.findByCriticId(criticId);
    }

    @Override
    public List<Critique> getAllCritiques() {
        return critiqueRepository.findAll();
    }
} 