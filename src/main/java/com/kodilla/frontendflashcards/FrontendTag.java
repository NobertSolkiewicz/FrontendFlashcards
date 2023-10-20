package com.kodilla.frontendflashcards;

import java.util.List;

public class FrontendTag {
    private Long tagId;
    private String name;
    private List<FrontendFlashcard> flashcardId;

    public FrontendTag(Long tagId, String name, List<FrontendFlashcard> flashcardId) {
        this.tagId = tagId;
        this.name = name;
        this.flashcardId = flashcardId;
    }

    public Long getTagId() {
        return tagId;
    }

    public String getName() {
        return name;
    }

    public List<FrontendFlashcard> getFlashcardId() {
        return flashcardId;
    }
}
