package com.artcom.community_platform.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.util.List;
import java.util.ArrayList;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "artworks")
public class Artwork {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(name = "image_url", nullable = false)
    private String imageUrl;

    @ManyToOne
    @JoinColumn(name = "artist_id", nullable = false)
    private User artist;

    @Column(nullable = false)
    private String category;

    @OneToMany(mappedBy = "artwork", cascade = CascadeType.ALL)
    private List<Critique> critiques = new ArrayList<>();

    // Constructor for creating artwork with basic details
    public Artwork(String title, String imageUrl, User artist, String category) {
        this.title = title;
        this.imageUrl = imageUrl;
        this.artist = artist;
        this.category = category;
    }

    public void addCritique(Critique critique) {
        critiques.add(critique);
        critique.setArtwork(this);
    }
}

