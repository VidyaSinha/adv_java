package com.artcom.community_platform.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "critiques")
public class Critique {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "artwork_id", nullable = false)
    private Artwork artwork;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "critic_id", nullable = false)
    private User critic;

    @Column(nullable = false, length = 1000)
    private String technicalAnalysis;

    @Column(nullable = false, length = 1000)
    private String compositionAndDesign;

    @Column(nullable = false, length = 1000)
    private String conceptAndMessage;

    @Column(nullable = false, length = 1000)
    private String suggestionsForImprovement;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }

    // Getters and Setters for all fields
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Artwork getArtwork() {
        return artwork;
    }

    public void setArtwork(Artwork artwork) {
        this.artwork = artwork;
    }

    public User getCritic() {
        return critic;
    }

    public void setCritic(User critic) {
        this.critic = critic;
    }

    public String getTechnicalAnalysis() {
        return technicalAnalysis;
    }

    public void setTechnicalAnalysis(String technicalAnalysis) {
        this.technicalAnalysis = technicalAnalysis;
    }

    public String getCompositionAndDesign() {
        return compositionAndDesign;
    }

    public void setCompositionAndDesign(String compositionAndDesign) {
        this.compositionAndDesign = compositionAndDesign;
    }

    public String getConceptAndMessage() {
        return conceptAndMessage;
    }

    public void setConceptAndMessage(String conceptAndMessage) {
        this.conceptAndMessage = conceptAndMessage;
    }

    public String getSuggestionsForImprovement() {
        return suggestionsForImprovement;
    }

    public void setSuggestionsForImprovement(String suggestionsForImprovement) {
        this.suggestionsForImprovement = suggestionsForImprovement;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
