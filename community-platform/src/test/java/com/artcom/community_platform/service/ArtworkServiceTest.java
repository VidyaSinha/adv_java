package com.artcom.community_platform.service;

import com.artcom.community_platform.entity.Artwork;
import com.artcom.community_platform.entity.User;
import com.artcom.community_platform.repository.ArtworkRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ArtworkServiceTest {

    @Mock
    private ArtworkRepository artworkRepository;

    @InjectMocks
    private ArtworkServiceImpl artworkService;

    private Artwork artwork;
    private User artist;

    @BeforeEach
    void setUp() {
        artist = new User();
        artist.setId(1L);
        artist.setUsername("artist");
        artist.setEmail("artist@example.com");

        artwork = new Artwork();
        artwork.setId(1L);
        artwork.setTitle("Test Artwork");
        artwork.setArtist(artist);
        artwork.setCategory(Artwork.ArtworkGenre.PAINTING);
        artwork.setImageUrl("http://example.com/image.jpg");
    }

    @Test
    void whenCreateArtwork_thenReturnCreatedArtwork() throws Exception {
        when(artworkRepository.save(any(Artwork.class))).thenReturn(artwork);

        Artwork created = artworkService.createArtwork(artist, "Test Artwork", "PAINTING", mock(MultipartFile.class));

        assertThat(created).isNotNull();
        assertThat(created.getTitle()).isEqualTo("Test Artwork");
        verify(artworkRepository).save(any(Artwork.class));
    }

    @Test
    void whenGetAllArtworks_thenReturnArtworkList() {
        when(artworkRepository.findAll()).thenReturn(Arrays.asList(artwork));

        List<Artwork> artworks = artworkService.getAllArtworks();

        assertThat(artworks).hasSize(1);
        assertThat(artworks.get(0).getTitle()).isEqualTo("Test Artwork");
        verify(artworkRepository).findAll();
    }

    @Test
    void whenGetArtworkById_thenReturnArtwork() {
        when(artworkRepository.findById(1L)).thenReturn(Optional.of(artwork));

        Artwork found = artworkService.getArtworkById(1L);

        assertThat(found).isNotNull();
        assertThat(found.getId()).isEqualTo(1L);
        verify(artworkRepository).findById(1L);
    }

    @Test
    void whenGetArtworkById_withNonExistentId_thenThrowException() {
        when(artworkRepository.findById(999L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> artworkService.getArtworkById(999L))
            .isInstanceOf(RuntimeException.class)
            .hasMessageContaining("Artwork not found");
    }

    @Test
    void whenGetArtworkByTitle_thenReturnArtwork() {
        when(artworkRepository.findByTitle("Test Artwork")).thenReturn(Optional.of(artwork));

        Artwork found = artworkService.getArtworkByTitle("Test Artwork");

        assertThat(found).isNotNull();
        assertThat(found.getTitle()).isEqualTo("Test Artwork");
        verify(artworkRepository).findByTitle("Test Artwork");
    }

    @Test
    void whenGetArtworkByTitle_withNonExistentTitle_thenThrowException() {
        when(artworkRepository.findByTitle("Nonexistent")).thenReturn(Optional.empty());

        assertThatThrownBy(() -> artworkService.getArtworkByTitle("Nonexistent"))
            .isInstanceOf(RuntimeException.class)
            .hasMessageContaining("Artwork not found");
    }

    @Test
    void whenGetArtworksByArtist_thenReturnArtworkList() {
        when(artworkRepository.findByArtistId(1L)).thenReturn(Arrays.asList(artwork));

        List<Artwork> artworks = artworkService.getArtworksByArtist(artist);

        assertThat(artworks).hasSize(1);
        assertThat(artworks.get(0).getArtist()).isEqualTo(artist);
        verify(artworkRepository).findByArtistId(1L);
    }

    @Test
    void whenDeleteArtwork_thenArtworkIsDeleted() throws Exception {
        doNothing().when(artworkRepository).deleteById(1L);

        artworkService.deleteArtwork(1L, artist);

        verify(artworkRepository).deleteById(1L);
    }
} 