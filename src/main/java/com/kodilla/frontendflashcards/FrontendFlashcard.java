package com.kodilla.frontendflashcards;

import java.util.List;

public class FrontendFlashcard {
    private Long flashCardId;
    private Long deckId;
    private String question;
    private String answer;
    private List<Long> tagsId;

    public FrontendFlashcard(Long flashCardId, Long deckId, String question, String answer, List<Long> tagsId) {
        this.flashCardId = flashCardId;
        this.deckId = deckId;
        this.question = question;
        this.answer = answer;
        this.tagsId = tagsId;
    }

    public Long getFlashCardId() {
        return flashCardId;
    }

    public Long getDeckId() {
        return deckId;
    }

    public String getQuestion() {
        return question;
    }

    public String getAnswer() {
        return answer;
    }

    public List<Long> getTagsId() {
        return tagsId;
    }
}
