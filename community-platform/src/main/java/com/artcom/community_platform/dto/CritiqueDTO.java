package com.artcom.community_platform.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class CritiqueDTO {
    private Long artworkId;
    private String technicalAnalysis;
    private String compositionAndDesign;
    private String conceptAndMessage;
    private String suggestionsForImprovement;


    // Explicit getters and setters to ensure they're available
    public Long getArtworkId() {
        return artworkId;
    }

    public void setArtworkId(Long artworkId) {
        this.artworkId = artworkId;
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
} 