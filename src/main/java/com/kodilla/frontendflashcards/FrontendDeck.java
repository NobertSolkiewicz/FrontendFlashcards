package com.kodilla.frontendflashcards;

import java.util.List;

public class FrontendDeck {
    private Long deckId;
    private Long userId;
    private String name;
    private String description;
    private List<FrontendTag> tags;

    public FrontendDeck(Long deckId, Long userId, String name, String description, List<FrontendTag> tags) {
        this.deckId = deckId;
        this.userId = userId;
        this.name = name;
        this.description = description;
        this.tags = tags;
    }

    public Long getDeckId() {
        return deckId;
    }

    public Long getUserId() {
        return userId;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public List<FrontendTag> getTags() {
        return tags;
    }
}
