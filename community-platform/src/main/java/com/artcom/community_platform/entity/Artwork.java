package com.artcom.community_platform.entity;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "artwork")

public class Artwork {

    public enum ArtworkGenre {
        LANDSCAPE,
        PORTRAIT,
        STILL_LIFE,
        ABSTRACT,
        POP_ART
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @Enumerated(EnumType.STRING)
    private ArtworkGenre category;

    @Column(name = "image_url")
    private String imageUrl;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "artist_id")
    private User artist;

    @OneToMany(mappedBy = "artwork", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Critique> critiques = new ArrayList<>();

    public Artwork() {
    }

    public Artwork(String title, String imageUrl, User artist, ArtworkGenre category) {
        this.title = title;
        this.imageUrl = imageUrl;
        this.artist = artist;
        this.category = category;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public ArtworkGenre getCategory() { return category; }
    public void setCategory(ArtworkGenre category) { this.category = category; }

    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }

    public User getArtist() { return artist; }
    public void setArtist(User artist) { this.artist = artist; }

    public List<Critique> getCritiques() { return critiques; }
    public void setCritiques(List<Critique> critiques) { this.critiques = critiques; }

    public void addCritique(Critique critique) {
        this.critiques.add(critique);
        critique.setArtwork(this);
    }
}
