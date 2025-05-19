//package com.artcom.community_platform.service;
//
//import com.artcom.community_platform.dto.CritiqueDTO;
//import com.artcom.community_platform.entity.Artwork;
//import com.artcom.community_platform.entity.Critique;
//import com.artcom.community_platform.entity.User;
//import com.artcom.community_platform.repository.CritiqueRepository;
////import com.artcom.community_platform.service.impl.CritiqueServiceImpl;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//
//import java.util.Arrays;
//import java.util.List;
//
//import static org.assertj.core.api.Assertions.assertThat;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.Mockito.*;
//
//@ExtendWith(MockitoExtension.class)
//class CritiqueServiceTest {
//
//    @Mock
//    private CritiqueRepository critiqueRepository;
//
//    @Mock
//    private ArtworkService artworkService;
//
//    @Mock
//    private UserService userService;
//
//    @InjectMocks
////    private CritiqueServiceImpl critiqueService;
//
//    private Critique critique;
//    private Artwork artwork;
//    private User critic;
//    private CritiqueDTO critiqueDTO;
//
//    @BeforeEach
//    void setUp() {
//        artwork = new Artwork();
//        artwork.setId(1L);
//        artwork.setTitle("Test Artwork");
//
//        critic = new User();
//        critic.setId(1L);
//        critic.setUsername("critic");
//        critic.setEmail("critic@example.com");
//
//        critique = new Critique();
//        critique.setId(1L);
//        critique.setArtwork(artwork);
//        critique.setCritic(critic);
//        critique.setTechnicalAnalysis("Good technique");
//        critique.setCompositionAndDesign("Well composed");
//        critique.setConceptAndMessage("Clear message");
//        critique.setSuggestionsForImprovement("Could use more contrast");
//
//        critiqueDTO = new CritiqueDTO();
//        critiqueDTO.setArtworkId(1L);
//        critiqueDTO.setTechnicalAnalysis("Good technique");
//        critiqueDTO.setCompositionAndDesign("Well composed");
//        critiqueDTO.setConceptAndMessage("Clear message");
//        critiqueDTO.setSuggestionsForImprovement("Could use more contrast");
//    }
//
//    @Test
//    void whenCreateCritique_thenReturnCreatedCritique() throws Exception {
//        when(artworkService.getArtworkById(1L)).thenReturn(artwork);
//        when(userService.getUserById(1L)).thenReturn(critic);
//        when(critiqueRepository.save(any(Critique.class))).thenReturn(critique);
//
////        Critique created = critiqueService.createCritique(critiqueDTO, 1L);
//
//        assertThat(created).isNotNull();
//        assertThat(created.getArtwork()).isEqualTo(artwork);
//        assertThat(created.getCritic()).isEqualTo(critic);
//        verify(critiqueRepository).save(any(Critique.class));
//    }
//
//    @Test
//    void whenGetCritiquesByArtwork_thenReturnCritiqueList() {
//        when(critiqueRepository.findByArtworkId(1L)).thenReturn(Arrays.asList(critique));
//
//        List<Critique> critiques = critiqueService.getCritiquesByArtwork(1L);
//
//        assertThat(critiques).hasSize(1);
//        assertThat(critiques.get(0).getArtwork()).isEqualTo(artwork);
//        verify(critiqueRepository).findByArtworkId(1L);
//    }
//
//    @Test
//    void whenGetCritiquesByCritic_thenReturnCritiqueList() {
//        when(critiqueRepository.findByCriticId(1L)).thenReturn(Arrays.asList(critique));
//
//        List<Critique> critiques = critiqueService.getCritiquesByCritic(1L);
//
//        assertThat(critiques).hasSize(1);
//        assertThat(critiques.get(0).getCritic()).isEqualTo(critic);
//        verify(critiqueRepository).findByCriticId(1L);
//    }
//
//    @Test
//    void whenGetAllCritiques_thenReturnCritiqueList() {
//        when(critiqueRepository.findAll()).thenReturn(Arrays.asList(critique));
//
//        List<Critique> critiques = critiqueService.getAllCritiques();
//
//        assertThat(critiques).hasSize(1);
//        assertThat(critiques.get(0).getId()).isEqualTo(1L);
//        verify(critiqueRepository).findAll();
//    }
//}