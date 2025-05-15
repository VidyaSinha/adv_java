package com.artcom.community_platform.service;

import com.artcom.community_platform.dto.CritiqueDTO;
import com.artcom.community_platform.entity.Artwork;
import com.artcom.community_platform.entity.Critique;
import com.artcom.community_platform.entity.User;
import com.artcom.community_platform.repository.CritiqueRepository;
import com.artcom.community_platform.repository.ArtworkRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CritiqueService {

    @Autowired
    private CritiqueRepository critiqueRepository;

    @Autowired
    private ArtworkRepository artworkRepository;

    @Autowired
    private ArtworkService artworkService;

    @Autowired
    private UserService userService;

    public Critique createCritique(CritiqueDTO critiqueDTO, Long criticId) {
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

    public List<Critique> getCritiquesByArtwork(Long artworkId) {
        return critiqueRepository.findByArtworkId(artworkId);
    }

    public List<Critique> getCritiquesByCritic(Long criticId) {
        return critiqueRepository.findByCriticId(criticId);
    }

    public List<Critique> getAllCritiques() {
        return critiqueRepository.findAll();
    }

    public Critique getCritiqueById(Long id) {
        return critiqueRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Critique not found with id: " + id));
    }

    @Transactional
    public Critique addCritiqueToArtwork(Long artworkId, Critique critique) {
        Artwork artwork = artworkRepository.findById(artworkId)
                .orElseThrow(() -> new RuntimeException("Artwork not found with id: " + artworkId));
        
        critique.setArtwork(artwork);
        artwork.addCritique(critique);
        
        return critiqueRepository.save(critique);
    }

    public void deleteCritique(Long id) {
        critiqueRepository.deleteById(id);
    }
}